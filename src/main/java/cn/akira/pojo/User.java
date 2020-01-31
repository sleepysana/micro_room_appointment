package cn.akira.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userId;

    private String loginName;

    private String loginEmail;

    private String loginPhoneNo;

    private String rsaPassword;

    private String userRole;

    private String userState;

    private String gender;

    private Date birthday;

    private String addr;

    private String realName;

    private String cid;

    private String cidType;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPhoneNo() {
        return loginPhoneNo;
    }

    public void setLoginPhoneNo(String loginPhoneNo) {
        this.loginPhoneNo = loginPhoneNo;
    }

    public String getRsaPassword() {
        return rsaPassword;
    }

    public void setRsaPassword(String rsaPassword) {
        this.rsaPassword = rsaPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getState() {
        return userState;
    }

    public void setState(String state) {
        this.userState = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCidType() {
        return cidType;
    }

    public void setCidType(String cidType) {
        this.cidType = cidType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", loginName=").append(loginName);
        sb.append(", loginEmail=").append(loginEmail);
        sb.append(", loginPhoneNo=").append(loginPhoneNo);
        sb.append(", rsaPassword=").append(rsaPassword);
        sb.append(", userRole=").append(userRole);
        sb.append(", state=").append(userState);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", addr=").append(addr);
        sb.append(", realName=").append(realName);
        sb.append(", cid=").append(cid);
        sb.append(", cidType=").append(cidType);
        sb.append("]");
        return sb.toString();
    }
}