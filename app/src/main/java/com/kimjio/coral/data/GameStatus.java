package com.kimjio.coral.data;

/**
 * 방 정보?
 */
public class GameStatus extends BaseData {
    private boolean isClosed; //방 닫힘?

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
