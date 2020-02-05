package cn.akira.pojo;

import java.io.Serializable;
import java.util.Date;

public class RoomUse implements Serializable {
    /**
    * 室编号
    */
    private Integer roomId;

    /**
    * 申请预约用户编号
    */
    private Integer applyUserId;

    /**
    * 申请时间
    */
    private Date applyDate;

    /**
    * 申请原因
    */
    private String applyReason;

    /**
    * 开始使用时间
    */
    private Date startDate;

    /**
    * 结束使用时间
    */
    private Date endDate;

    /**
    * 审批状态*
    */
    private String approveState;

    /**
    * 审批时间
    */
    private Date approveDate;

    /**
    * 审批人(用户)ID
    */
    private Integer approveUserId;

    /**
    * 审核不通过原因
    */
    private String rejectReason;

    /**
     * 预约流水号
     */
    private long appSeq;

    private User applyUserInfo;

    private Room roomInfo;

    private static final long serialVersionUID = 1L;

    public Room getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(Room roomInfo) {
        this.roomInfo = roomInfo;
    }

    public User getApplyUserInfo() {
        return applyUserInfo;
    }

    public void setApplyUserInfo(User applyUserInfo) {
        this.applyUserInfo = applyUserInfo;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Integer getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Integer approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public long getAppSeq() {
        return appSeq;
    }

    public void setAppSeq(long appSeq) {
        this.appSeq = appSeq;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roomId=").append(roomId);
        sb.append(", applyUserId=").append(applyUserId);
        sb.append(", applyDate=").append(applyDate);
        sb.append(", applyReason=").append(applyReason);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", approveState=").append(approveState);
        sb.append(", approveDate=").append(approveDate);
        sb.append(", approveUserId=").append(approveUserId);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append("]");
        return sb.toString();
    }
}