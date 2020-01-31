package cn.akira.service;

import cn.akira.pojo.VerifyInfo;

public interface VerifyInfoService {
    int insertVerifyInfo(VerifyInfo verifyInfo) throws Exception;

    int deleteByConditions(VerifyInfo verifyInfo) throws Exception;

    VerifyInfo queryAllByConditions(VerifyInfo verifyInfo) throws Exception;
}
