package cn.akira.service.impl;

import cn.akira.mapper.ColCommMapper;
import cn.akira.pojo.ColComm;
import cn.akira.service.ColCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColCommServiceImpl implements ColCommService {

    @Autowired
    private ColCommMapper colCommMapper;

    @Override
    public List<ColComm> queryAttributes(String colName) {
        return  colCommMapper.queryAttributes(colName);
    }
}
