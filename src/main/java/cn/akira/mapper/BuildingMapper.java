package cn.akira.mapper;

import cn.akira.pojo.Building;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BuildingMapper {
    int deleteByPrimaryKey(BigDecimal buildingId);

    int insert(Building record);

    int insertSelective(Building record);

    Building selectByPrimaryKey(BigDecimal buildingId);

    int updateByPrimaryKeySelective(Building record);

    int updateByPrimaryKey(Building record);

    Building queryByPrimaryKey(@Param("buildingId") int buildingId);

    List<Building> queryAllIdAndName();
}