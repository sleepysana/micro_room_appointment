package cn.akira.controller;

import cn.akira.pojo.*;
import cn.akira.service.BuildingService;
import cn.akira.service.ColCommService;
import cn.akira.service.RoomService;
import cn.akira.service.RoomUseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ColCommService colCommService;

    @Autowired
    private RoomUseService roomUseService;

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

    @RequestMapping("/showApplyUse/{roomId}")
    public String showApplyUse(@PathVariable String roomId, Model model) {
        model.addAttribute("showApplyUseRoomId", roomId);
        return "/room/roomUseApply";
    }

    @RequestMapping("applyUse")
    @ResponseBody
    public ResponseData applyUse(RoomUse roomUse,
                                 @RequestParam("startTime") String startTime,
                                 @RequestParam("endTime") String endTime,
                                 HttpSession session) {
        ResponseData responseData = new ResponseData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = df.parse(startTime);
            Date endDate = df.parse(endTime);
            roomUse.setStartDate(startDate);
            roomUse.setEndDate(endDate);
            User sessionUser = (User) session.getAttribute("SESSION_USER");
            roomUse.setApplyUserId(sessionUser.getUserId());
            roomUse.setApproveState("-1");
            roomUseService.insert(roomUse);
            responseData.setMessage("申请已提交, 待管理员阅后审核结果将会下发到您的邮箱，请留意");
        } catch (ParseException e) {
            responseData.setFlag(false);
            responseData.setMessage("日期格式不正确");
        }catch (Exception e){
            e.printStackTrace();
            responseData.setExceptionInfo(e);
            responseData.setMessage("遇到错误:"+e.getMessage());
        }
        return responseData;
    }
}
