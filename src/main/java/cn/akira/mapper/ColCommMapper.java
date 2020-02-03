package cn.akira.mapper;

import cn.akira.pojo.ColComm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ColCommMapper {
List<ColComm> queryAttributes(@Param("colName") String colName);
}