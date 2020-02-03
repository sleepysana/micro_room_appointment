package cn.akira.pojo;


import java.io.Serializable;


public class Room implements Serializable {
    /**
     * 室编号
     */
    private Integer roomId;

    /**
     * 室名称
     */
    private String roomName;

    /**
     * 室类型
     */
    private String roomType;

    /**
     * 座位数
     */
    private Integer seats;

    /**
     * 所属楼ID
     */
    private Integer buildingId;

    /**
     * 能否预约*
     */
    private String roomState;

    /**
     * 不能预约原因
     */
    private String lockReason;

    /**
     * 所属教学楼
     */
    private Building building;

    private static final long serialVersionUID = 1L;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
