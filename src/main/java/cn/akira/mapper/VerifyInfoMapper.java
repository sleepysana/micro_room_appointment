package cn.akira.mapper;

import cn.akira.pojo.VerifyInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VerifyInfoMapper {
    int insertVerifyInfo(VerifyInfo verifyInfo);

    int deleteByConditions(VerifyInfo verifyInfo) throws Exception;

    VerifyInfo queryAllByConditions(VerifyInfo verifyInfo) throws Exception;
}