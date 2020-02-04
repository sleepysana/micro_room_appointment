package cn.akira.service.impl;

import cn.akira.mapper.BuildingMapper;
import cn.akira.pojo.Building;
import cn.akira.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public Building queryByPrimaryKey(int buildingId) {
        return buildingMapper.queryByPrimaryKey(buildingId);
    }

    @Override
    public List<Building> queryAllIdAndName() throws Exception{
        return buildingMapper.queryAllIdAndName();
    }
}
