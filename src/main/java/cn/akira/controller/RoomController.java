package cn.akira.controller;

import cn.akira.pojo.Building;
import cn.akira.pojo.ColComm;
import cn.akira.pojo.ResponseData;
import cn.akira.pojo.Room;
import cn.akira.service.BuildingService;
import cn.akira.service.ColCommService;
import cn.akira.service.RoomService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ColCommService colCommService;

    @RequestMapping("/toRoomList")
    public String toAppointmentRoom() {
        return "/room/listRooms";
    }

    @RequestMapping("/queryRooms")
    @ResponseBody
    public ResponseData listRooms(Room conditions) {
        ResponseData responseData = new ResponseData();
        try {
            List<Room> rooms = roomService.queryByConditions(conditions);
            String s = JSON.toJSONString(rooms);
            Object roomData = JSONObject.parse(s);
            responseData.setResource(roomData);
            responseData.setCustomProp(rooms.size());
            responseData.setStatus(0);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setExceptionInfo(e);
        }
        return responseData;
    }

    @RequestMapping("/getSelectList")
    @ResponseBody
    public ResponseData getRoomTypeSelectList() throws Exception {
        ResponseData responseData = new ResponseData();
        Map<String, List<?>> selectMap = new HashMap<>();
        List<ColComm> roomTypes = colCommService.queryAttributes("room_type");
        List<Building> buildings = buildingService.queryAllIdAndName();
        selectMap.put("roomTypes", roomTypes);
        selectMap.put("buildings", buildings);
        responseData.setResource(selectMap);
        return responseData;
    }
}
