package com.kimjio.coral.data;

import android.os.Parcel;

/**
 * 방 정보?
 */
public class GameStatus extends BaseData {
    public static final Creator<GameStatus> CREATOR = new Creator<GameStatus>() {
        @Override
        public GameStatus createFromParcel(Parcel in) {
            return new GameStatus(in);
        }

        @Override
        public GameStatus[] newArray(int size) {
            return new GameStatus[size];
        }
    };
    private boolean isClosed;

    public GameStatus() {
    }

    protected GameStatus(Parcel in) {
        super(in);
        isClosed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (isClosed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isClosed() {
        return isClosed;
    }
    /*public int playingMemberCount;
    public Status status;

    //TODO Status
    public enum Status {
        A(1),
        B(2),
        C(3);

        private int statusId;

        Status(int statusId) {
            this.statusId = statusId;
        }

        public int toInteger() {
            return statusId;
        }
    }*/
}
