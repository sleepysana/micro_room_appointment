package cn.akira.pojo;

import java.io.Serializable;

public class ColComm implements Serializable {
    /**
    * 描述
    */
    private String describe;

    /**
    * 列名
    */
    private String colName;

    /**
    * 码值
    */
    private String attributeKey;

    /**
    * 释义
    */
    private String attributeValue;

    private static final long serialVersionUID = 1L;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", describe=").append(describe);
        sb.append(", colName=").append(colName);
        sb.append(", attributeKey=").append(attributeKey);
        sb.append(", attributeValue=").append(attributeValue);
        sb.append("]");
        return sb.toString();
    }
}