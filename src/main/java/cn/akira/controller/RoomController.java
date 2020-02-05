package cn.akira.controller;

import cn.akira.pojo.*;
import cn.akira.service.*;
import cn.akira.thread.SendEmailThread;
import cn.akira.util.CastUtil;
import cn.akira.util.NetUtil;
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
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private RoomUseService roomUseService;

    @Autowired
    private UserService userService;

    @RequestMapping("/toRoomList")
    public String toAppointmentRoom() {
        return "/room/listRooms";
    }

    @RequestMapping("/roomApplyMng")
    public String toRoomApplyMng() {
        return "/room/roomApplyMng";
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
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setExceptionInfo(e);
            responseData.setMessage("遇到错误:" + e.getMessage());
        }
        return responseData;
    }

    @RequestMapping("/queryRoomUses")
    @ResponseBody
    public ResponseData queryRoomUses(RoomUse requestedRoomUse) {
        ResponseData responseData = new ResponseData();
        try {
            List<RoomUse> roomUses = roomUseService.queryRoomUse(requestedRoomUse);
            responseData.setStatus(0);
            responseData.setResource(roomUses);
            responseData.setCustomProp(roomUses.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setExceptionInfo(e);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    @RequestMapping("/roomUseApprove")
    @ResponseBody
    public ResponseData roomUseApprove(RoomUse roomUse, HttpSession session) {
        ResponseData responseData = new ResponseData();
        User sessionUser = (User) session.getAttribute("SESSION_USER");
        try {
            roomUse.setApproveUserId(sessionUser.getUserId());
            roomUseService.updateRoomUse(roomUse);
            RoomUse onlyHaveSeqRoomUse = new RoomUse();
            onlyHaveSeqRoomUse.setAppSeq(roomUse.getAppSeq());
            List<RoomUse> roomUses = roomUseService.queryRoomUse(onlyHaveSeqRoomUse);
            User applyUser = new User();
            applyUser.setUserId(roomUses.get(0).getApplyUserId());
            String loginEmail = userService.getUser(applyUser).getLoginEmail();
            String title = "预约结果通知", content, responseMessage;
            if ("0".equals(roomUse.getApproveState())) {
                roomUse.setRejectReason("长得丑"); //todo 这个以后改
                content = "尊敬的用户，很遗憾的通知您，你在" + CastUtil.formatDate(roomUses.get(0).getApplyDate()) +
                        "对" + roomUses.get(0).getRoomId() + "教室(预约使用时间段:" +
                        CastUtil.formatDate(roomUses.get(0).getStartDate()) + " ~ " +
                        CastUtil.formatDate(roomUses.get(0).getEndDate()) + ")的申请并未通过," +
                        "原因：" + roomUses.get(0).getRejectReason();
                responseMessage = "已拒绝";
            } else {
                content = "尊敬的用户，你在" + CastUtil.formatDate(roomUses.get(0).getApplyDate()) +
                        "对" + roomUses.get(0).getRoomId() + "教室(预约使用时间段:" +
                        CastUtil.formatDate(roomUses.get(0).getStartDate()) + " ~ " +
                        CastUtil.formatDate(roomUses.get(0).getEndDate()) + ")的申请已通过审核,届时请务必到达该教室";
                responseMessage = "已通过";
            }
            Thread sendEmailThread = new Thread(new SendEmailThread(loginEmail,title,content));
            sendEmailThread.start();
            responseData.setMessage(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setExceptionInfo(e);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}
