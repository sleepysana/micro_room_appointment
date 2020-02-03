package cn.akira.pojo;

import java.io.Serializable;

public class Building implements Serializable {
    /**
    * 楼编号
    */
    private Integer buildingId;

    /**
    * 楼名称
    */
    private String buildingName;

    /**
    * 楼地址
    */
    private String address;

    private static final long serialVersionUID = 1L;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", buildingId=").append(buildingId);
        sb.append(", buildingName=").append(buildingName);
        sb.append(", address=").append(address);
        sb.append("]");
        return sb.toString();
    }
}