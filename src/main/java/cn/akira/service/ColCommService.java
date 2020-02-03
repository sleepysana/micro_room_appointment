package cn.akira.service;

import cn.akira.pojo.ColComm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColCommService {
    List<ColComm> queryAttributes(@Param("colName") String colName);
}
