package cn.akira.service;

import cn.akira.pojo.Building;

public interface BuildingService {
    Building queryByPrimaryKey( int buildingId);
}
