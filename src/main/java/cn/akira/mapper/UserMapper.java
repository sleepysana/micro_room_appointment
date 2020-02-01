package cn.akira.mapper;

import cn.akira.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User querySessionCols(User user);

    Integer queryCountByConditions(User user);

    int insert(User user) throws Exception;
}