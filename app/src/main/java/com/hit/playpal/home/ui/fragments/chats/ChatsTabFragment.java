package com.hit.playpal.home.ui.fragments.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.hit.playpal.R;
import com.hit.playpal.chatrooms.ui.activities.ChatRoomActivity;
import com.hit.playpal.entities.chats.ChatRoom;
import com.hit.playpal.entities.chats.GroupChatRoom;
import com.hit.playpal.entities.chats.OneToOneChatRoom;
import com.hit.playpal.entities.chats.enums.ChatRoomType;
import com.hit.playpal.home.adapters.recentchats.ChatsRecyclerViewAdapter;
import com.hit.playpal.home.ui.viewmodels.ChatsViewModel;

import javax.annotation.Nullable;

public class ChatsTabFragment extends Fragment {
    private static final String TAG = "AllChatRoomsTabFragment";
    private ChatsViewModel mChatsViewModel;

    private RecyclerView mRecyclerView;
    private ChatsRecyclerViewAdapter mChatsRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable private ChatRoomType mChatRoomType;


        public ChatsTabFragment() {

        }



    public ChatsTabFragment(@Nullable ChatRoomType iChatRoomType) {
        mChatRoomType = iChatRoomType;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater iInflater, ViewGroup iContainer, Bundle iSavedInstanceState) {
        return iInflater.inflate(R.layout.fragment_recent_chats_tab_recyclerview, iContainer, false);
    }

    @Override
    public void onViewCreated(@NonNull View iView, Bundle iSavedInstanceState) {
        super.onViewCreated(iView, iSavedInstanceState);

        initViewModel();
        initLinearLayoutManagerForRecyclerView();
        initAdapterForRecyclerView();
        initRecyclerView(iView);
    }

    private void initViewModel() {
        mChatsViewModel = new ChatsViewModel();
    }

    private void initLinearLayoutManagerForRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
    }

    private void initAdapterForRecyclerView() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        Query query = (mChatRoomType == null)
                ? mChatsViewModel.generateQueryForAllChatRooms()
                : mChatsViewModel.generateQueryForSpecificChatRooms(mChatRoomType);

        mChatsRecyclerViewAdapter = new ChatsRecyclerViewAdapter(this::handleChatRoomCardClicked, lifecycleOwner, query);
    }

    private void initRecyclerView(@NonNull View iView) {
        mRecyclerView = iView.findViewById(R.id.recyclerview_chats_all_chat_rooms_tab);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mChatsRecyclerViewAdapter);
    }

    private void handleChatRoomCardClicked(ChatRoom iChatRoom) {
        if (iChatRoom instanceof GroupChatRoom) {
            GroupChatRoom groupChatRoom = (GroupChatRoom) iChatRoom;

            Intent intent = new Intent(getContext(), ChatRoomActivity.class);
            intent.putExtra("chatRoom", groupChatRoom);
            startActivity(intent);
        } else if (iChatRoom instanceof OneToOneChatRoom) {
            OneToOneChatRoom oneToOneChatRoom = (OneToOneChatRoom) iChatRoom;

            Intent intent = new Intent(getContext(), ChatRoomActivity.class);
            intent.putExtra("chatRoom", oneToOneChatRoom);
            startActivity(intent);
        } else {
            throw new IllegalStateException("ChatRoom type not recognized");
        }
    }
}