package cn.akira.service.impl;

import cn.akira.mapper.VerifyInfoMapper;
import cn.akira.pojo.VerifyInfo;
import cn.akira.service.VerifyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyInfoServiceImpl implements VerifyInfoService {

    @Autowired
    private VerifyInfoMapper verifyInfoMapper;

    @Override
    public int insertVerifyInfo(VerifyInfo verifyInfo) throws Exception {
        return verifyInfoMapper.insertVerifyInfo(verifyInfo);
    }

    @Override
    public int deleteByConditions(VerifyInfo verifyInfo) throws Exception {
        return verifyInfoMapper.deleteByConditions(verifyInfo);
    }

    @Override
    public VerifyInfo queryAllByConditions(VerifyInfo verifyInfo) throws Exception {
        return verifyInfoMapper.queryAllByConditions(verifyInfo);
    }
}
