package cn.akira.pojo;

import java.io.Serializable;
import java.util.Date;

public class VerifyInfo implements Serializable {
    private String verifyType;

    private String verifyCode;

    private String verifyEmail;

    private String verifyPhoneNo;

    private Date generateDate;

    private static final long serialVersionUID = 1L;

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(String verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public String getVerifyPhoneNo() {
        return verifyPhoneNo;
    }

    public void setVerifyPhoneNo(String verifyPhoneNo) {
        this.verifyPhoneNo = verifyPhoneNo;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", verifyType=").append(verifyType);
        sb.append(", verifyCode=").append(verifyCode);
        sb.append(", verifyEmail=").append(verifyEmail);
        sb.append(", verifyPhoneNo=").append(verifyPhoneNo);
        sb.append(", generateDate=").append(generateDate);
        sb.append("]");
        return sb.toString();
    }
}