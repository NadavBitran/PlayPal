package com.hit.playpal.chatrooms.data.repositories;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hit.playpal.chatrooms.data.datasources.FirebaseFirestoreDataSource;
import com.hit.playpal.chatrooms.domain.listeners.INewMessageEventListener;
import com.hit.playpal.chatrooms.domain.listeners.INewMessageRegistrationListener;
import com.hit.playpal.chatrooms.domain.repositories.IChatRoomRepository;
import com.hit.playpal.entities.chats.group.GroupProfile;
import com.hit.playpal.entities.messages.Message;
import com.hit.playpal.entities.chats.group.enums.GroupChatRoomRole;
import com.hit.playpal.entities.users.User;

public class ChatRoomRepository implements IChatRoomRepository {
    private final FirebaseFirestoreDataSource DB = new FirebaseFirestoreDataSource();

    @Override
    public INewMessageRegistrationListener listenForTheLatestMessage(String iChatRoomId, INewMessageEventListener iEventListener) {
        return DB.listenForTheLatestMessage(iChatRoomId, iEventListener);
    }

    @Override
    public Task<DocumentReference> sendMessage(String iChatRoomId, Message iMessage) {
        return DB.writeMessage(iChatRoomId, iMessage);
    }

    @Override
    public Task<Void> updateLastMessage(String iChatRoomId, Message iMessage) {
        return DB.updateLastMessage(iChatRoomId, iMessage);
    }

    @Override
    public Task<DocumentSnapshot> getChatRoom(String iChatRoomId) {
        return DB.getGroupChatProfile(iChatRoomId);
    }

    @Override
    public Task<QuerySnapshot> getMessagesInPage(String iChatRoomId, long iPageSize, DocumentSnapshot iAfterThisMessageRef) {
        return DB.loadMessages(iChatRoomId, iPageSize, iAfterThisMessageRef);
    }

    @Override
    public Task<Void> addNewMemberToGroupChatRoom(String iChatRoomId, User iUser) {
        GroupProfile.Participant participant = GroupProfile.Participant.parseUser(iUser, GroupChatRoomRole.REGULAR);
        Task<Void> taskToUpdateGroupChatRoom = DB.updateGroupChatRoomWithNewMember(iChatRoomId, iUser.getUid());
        Task<Void> taskToUpdateGroupProfile = DB.updateGroupProfileWithNewParticipant(iChatRoomId, participant);

        return Tasks.whenAllComplete(taskToUpdateGroupChatRoom, taskToUpdateGroupProfile).continueWith(task -> null);
    }
}
