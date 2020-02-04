package cn.akira.service;

import cn.akira.pojo.Building;

import java.util.List;

public interface BuildingService {
    Building queryByPrimaryKey( int buildingId);

    List<Building> queryAllIdAndName() throws Exception;
}
