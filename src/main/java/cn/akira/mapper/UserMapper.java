package cn.akira.mapper;

import cn.akira.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface UserMapper {

    User querySessionCols(User user);

    Integer queryCountByConditions(User user);

    int insert(User user) throws Exception;
}