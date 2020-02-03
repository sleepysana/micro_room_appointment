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

import java.util.LinkedList;
import java.util.List;

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
    public String toAppointmentRoom(){
        return "/room/listRooms";
    }

    @RequestMapping("/listRooms")
    @ResponseBody
    public ResponseData listRooms(){
        ResponseData responseData = new ResponseData();
        try {
            List<Room> rooms = roomService.queryAll();
            LinkedList<Room> newRoomsList = new LinkedList<>();
            for (Room room : rooms) {
                Integer buildingId = room.getBuildingId();
                Building building = buildingService.queryByPrimaryKey(buildingId);
                room.setBuilding(building);
                newRoomsList.add(room);
            }
            String s = JSON.toJSONString(newRoomsList);
            Object roomData = JSONObject.parse(s);
            responseData.setResource(roomData);
            responseData.setCustomProp(rooms.size());
            responseData.setStatus(0);
            return responseData;
        }catch (Exception e){
            e.printStackTrace();
            responseData.setExceptionInfo(e);
        }
        return responseData;
    }

    @RequestMapping("/getRoomTypeSelectList")
    @ResponseBody
    public ResponseData getRoomTypeSelectList(){
        ResponseData responseData = new ResponseData();
        List<ColComm> roomTypes = colCommService.queryAttributes("room_type");
        responseData.setResource(roomTypes);
        return responseData;
    }
}
