package cn.akira.mapper;

import cn.akira.pojo.RoomUse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomUseMapper {
    int insert(RoomUse record);

    int insertSelective(RoomUse record);
}