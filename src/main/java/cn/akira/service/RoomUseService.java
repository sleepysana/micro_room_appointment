package cn.akira.service;

import cn.akira.pojo.RoomUse;

import java.util.List;

public interface RoomUseService {
    int insert(RoomUse record);

    List<RoomUse> queryRoomUse(RoomUse record) throws Exception;

    int updateRoomUse(RoomUse record)throws Exception;
}
