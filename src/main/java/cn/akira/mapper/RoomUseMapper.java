package cn.akira.mapper;

import cn.akira.pojo.RoomUse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomUseMapper {
    int insert(RoomUse record);

    int insertSelective(RoomUse record);

    List<RoomUse> queryRoomUse(RoomUse record);

    int updateRoomUse(RoomUse record);
}