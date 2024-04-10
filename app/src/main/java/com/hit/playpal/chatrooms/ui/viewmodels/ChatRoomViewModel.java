package com.hit.playpal.chatrooms.ui.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.DocumentSnapshot;
import com.hit.playpal.chatrooms.data.repositories.ChatRoomRepository;
import com.hit.playpal.chatrooms.domain.listeners.INewMessageEventListener;
import com.hit.playpal.chatrooms.domain.listeners.INewMessageRegistrationListener;
import com.hit.playpal.chatrooms.domain.usecases.chatbody.ListenToLatestMessageUseCase;
import com.hit.playpal.chatrooms.domain.usecases.chatbody.SendMessageUseCase;
import com.hit.playpal.chatrooms.domain.usecases.chatbody.UpdateChatRoomLastMessageUseCase;
import com.hit.playpal.chatrooms.domain.usecases.chatbody.FetchMessagesUseCase;
import com.hit.playpal.entities.chats.ChatRoom;
import com.hit.playpal.entities.chats.Message;
import com.hit.playpal.entities.users.User;
import com.hit.playpal.utils.Out;
import com.hit.playpal.utils.UseCaseResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class ChatRoomViewModel extends ViewModel {
    private static final String TAG = "ChatRoomViewModel";

    private final User USER;
    public User getUser() {
        return USER;
    }

    private DocumentSnapshot mLatestMessageRef = null;
    private MutableLiveData<List<Message>> mNewMessages;
    private MutableLiveData<String> mFetchMessagesSuccess;
    private MutableLiveData<String> mFetchMessagesError;

    public LiveData<List<Message>> getNewMessages()
    {
        return mNewMessages;
    }
    public LiveData<String> getFetchMessagesSuccess()
    {
        return mFetchMessagesSuccess;
    }
    public LiveData<String> getFetchMessagesError()
    {
        return mFetchMessagesError;
    }

    private final MutableLiveData<ChatRoom> CHAT_ROOM_LIVE_DATA;
    public LiveData<ChatRoom> getChatRoomLiveData() {
        return CHAT_ROOM_LIVE_DATA;
    }


    private final MutableLiveData<Message> ON_MESSAGE_SENT = new MutableLiveData<>();
    public LiveData<Message> onMessageSent() {
        return ON_MESSAGE_SENT;
    }

    public ChatRoomViewModel() {
        USER = null;
        CHAT_ROOM_LIVE_DATA = new MutableLiveData<>();
        mNewMessages = new MutableLiveData<>();
        mFetchMessagesSuccess = new MutableLiveData<>();
        mFetchMessagesError = new MutableLiveData<>();
    }
    public ChatRoomViewModel(@NonNull User iUser, @NonNull ChatRoom iChatRoom) {
        USER = iUser;
        CHAT_ROOM_LIVE_DATA = new MutableLiveData<>(iChatRoom);
        mNewMessages = new MutableLiveData<>();
        mFetchMessagesSuccess = new MutableLiveData<>();
        mFetchMessagesError = new MutableLiveData<>();
    }


    public void sendMessage(Message iMessage) {
        SendMessageUseCase sendMessageUseCase = new SendMessageUseCase(new ChatRoomRepository());
        UpdateChatRoomLastMessageUseCase updateChatRoomLastMessageUseCase = new UpdateChatRoomLastMessageUseCase(new ChatRoomRepository());
        AtomicReference<String> chatRoomId = new AtomicReference<>(CHAT_ROOM_LIVE_DATA.getValue().getId());

        ON_MESSAGE_SENT.postValue(iMessage);

        sendMessageUseCase.execute(chatRoomId.get(), iMessage)
                .thenCompose(result -> {
                    if (result.isSuccessful()) {
                        return updateChatRoomLastMessageUseCase.execute(chatRoomId.get(), iMessage);
                    } else {
                        return CompletableFuture.completedFuture(UseCaseResult.forFailure(result.getFailure()));
                    }
                })
                .thenAccept(result -> {
                    if (!result.isSuccessful()) {
                        Log.e(TAG, "Couldn't update the last message in the chat room collection during `sendMessage` runtime");
                        Toast.makeText(null, "Couldn't send the message", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public INewMessageRegistrationListener listenToLatestMessages() {
        ListenToLatestMessageUseCase useCase = new ListenToLatestMessageUseCase(new ChatRoomRepository());
        String chatRoomId = CHAT_ROOM_LIVE_DATA.getValue().getId();

        return useCase.execute(chatRoomId, new INewMessageEventListener() {
            @Override
            public void onFetched(ChatRoom iChatRoom) {
                    CHAT_ROOM_LIVE_DATA.postValue(iChatRoom);
            }

            @Override
            public void onError(Exception iException) {
                // TODO: handle error

                // For now...
                Log.e(TAG, "onError: " + iException.getMessage());
                Toast.makeText(null, "An error occurred while fetching the latest message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchMessages(long iPageSize)
    {
        FetchMessagesUseCase fetchMessagesUseCase = new FetchMessagesUseCase(new ChatRoomRepository());
        Out<DocumentSnapshot> mNewlyLatestMessageRef = new Out<>(null);
        fetchMessagesUseCase.execute(CHAT_ROOM_LIVE_DATA.getValue().getId(), iPageSize, mLatestMessageRef, mNewlyLatestMessageRef)
                .whenComplete((messages, exception) -> {
                    if (exception == null)
                    {
                        mNewMessages.postValue(messages);
                        mFetchMessagesSuccess.postValue("Messages fetched successfully");
                        mLatestMessageRef = mNewlyLatestMessageRef.get();
                    }
                    else
                    {
                        mFetchMessagesError.postValue(exception.getMessage());
                    }
                });
        }

    public static class Factory implements ViewModelProvider.Factory {
        private final User USER;
        private final ChatRoom CHAT_ROOM;

        public Factory(@NonNull User iUser, @NonNull ChatRoom iChatRoom) {
            USER = iUser;
            CHAT_ROOM = iChatRoom;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> iModelClass) {
            if (iModelClass.isAssignableFrom(ChatRoomViewModel.class)) {
                return (T) new ChatRoomViewModel(USER, CHAT_ROOM);
            }

            throw new IllegalArgumentException(TAG+ ": Unknown ViewModel class");
        }
    }
}
