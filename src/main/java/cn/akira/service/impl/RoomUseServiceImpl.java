package cn.akira.service.impl;

import cn.akira.mapper.RoomUseMapper;
import cn.akira.pojo.RoomUse;
import cn.akira.service.RoomUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomUseServiceImpl implements RoomUseService {

    @Autowired
    private RoomUseMapper roomUseMapper;

    @Override
    @Transactional
    public int insert(RoomUse record) {
        return roomUseMapper.insert(record);
    }
}
