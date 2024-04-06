package com.hit.playpal.entities.chats;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.PropertyName;
import com.hit.playpal.entities.chats.enums.ChatRoomType;

import org.jetbrains.annotations.Contract;

import java.util.List;

public class GroupChatRoom extends ChatRoom {
    @PropertyName("name") private String mName;
    @PropertyName("name") public String getName() { return mName; }
    @PropertyName("name") public void setName(String iName) { mName = iName; }

    @PropertyName("profile_picture") private String mProfilePicture;
    @PropertyName("profile_picture") public String getProfilePicture() { return mProfilePicture; }
    @PropertyName("profile_picture") public void setProfilePicture(String iProfilePicture) { mProfilePicture = iProfilePicture; }

    @PropertyName("game") private Game mGame;
    @PropertyName("game") public Game getGame() { return mGame; }
    @PropertyName("game") public void setGame(Game iGame) { mGame = iGame; }

    public GroupChatRoom() { }
    public GroupChatRoom(String iChatRoomId, List<String> iMembersUid, Message iLastMessage, String iName, String iProfilePicture, Game iGame) {
        super(iChatRoomId, ChatRoomType.GROUP, iMembersUid, iLastMessage);
        mName = iName;
        mProfilePicture = iProfilePicture;
        mGame = iGame;
    }

    protected GroupChatRoom(@NonNull Parcel iIn) {
        super(iIn);
        mName = iIn.readString();
        mProfilePicture = iIn.readString();
        mGame = iIn.readParcelable(Game.class.getClassLoader());
    }

    @Override
    public void writeToParcel(@NonNull Parcel iDest, int iFlags) {
        super.writeToParcel(iDest, iFlags);
        iDest.writeString(mName);
        iDest.writeString(mProfilePicture);
        iDest.writeParcelable(mGame, iFlags);
    }

    public static final Creator<GroupChatRoom> CREATOR = new Creator<GroupChatRoom>() {
        @NonNull
        @Contract("_ -> new")
        @Override
        public GroupChatRoom createFromParcel(Parcel iIn) {
            return new GroupChatRoom(iIn);
        }

        @NonNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public GroupChatRoom[] newArray(int iSize) {
            return new GroupChatRoom[iSize];
        }
    };

    public static class Game implements Parcelable {
        @PropertyName("name") private String mName;
        @PropertyName("name") public String getName() { return mName; }
        @PropertyName("name") public void setName(String iName) { mName = iName; }

        @PropertyName("background_image") private String mBackgroundImage;
        @PropertyName("background_image") public String getBackgroundImage() { return mBackgroundImage; }
        @PropertyName("background_image") public void setBackgroundImage(String iBackgroundImage) { mBackgroundImage = iBackgroundImage; }

        public Game() { }
        public Game(String iName, String iBackgroundImage) {
            mName = iName;
            mBackgroundImage = iBackgroundImage;
        }

        protected Game(@NonNull Parcel iIn) {
            mName = iIn.readString();
            mBackgroundImage = iIn.readString();
        }

        @Override
        public void writeToParcel(@NonNull Parcel iDest, int iFlags) {
            iDest.writeString(mName);
            iDest.writeString(mBackgroundImage);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Game> CREATOR = new Creator<Game>() {
            @NonNull
            @Contract("_ -> new")
            @Override
            public Game createFromParcel(Parcel iIn) {
                return new Game(iIn);
            }

            @NonNull
            @Contract(value = "_ -> new", pure = true)
            @Override
            public Game[] newArray(int iSize) {
                return new Game[iSize];
            }
        };
    }
}