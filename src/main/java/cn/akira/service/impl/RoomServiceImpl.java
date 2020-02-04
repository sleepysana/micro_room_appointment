package cn.akira.service.impl;

import cn.akira.mapper.RoomMapper;
import cn.akira.pojo.Room;
import cn.akira.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> queryAll() {
       return roomMapper.queryAll();
    }

    @Override
    public List<Room> queryByConditions(Room room) {
        return roomMapper.queryByConditions(room);
    }
}
