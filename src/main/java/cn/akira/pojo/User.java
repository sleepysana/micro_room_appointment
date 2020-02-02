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

    private String hdFileName;

    private Date regDate;

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

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getHdFileName() {
        return hdFileName;
    }

    public void setHdFileName(String hdFileName) {
        this.hdFileName = hdFileName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", loginPhoneNo='" + loginPhoneNo + '\'' +
                ", rsaPassword='" + rsaPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userState='" + userState + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", addr='" + addr + '\'' +
                ", realName='" + realName + '\'' +
                ", cid='" + cid + '\'' +
                ", cidType='" + cidType + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}