package cn.akira.service;

import cn.akira.pojo.Room;

import java.util.List;

public interface RoomService {
    List<Room> queryAll();

    List<Room> queryByConditions(Room room);
}
