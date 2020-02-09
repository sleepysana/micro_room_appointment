<%--suppress HtmlFormInputWithoutLabel,HtmlUnknownAttribute--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<html>
<head>
    <title>填写预约信息</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${path}/static/css/layui/layui.css">
    <style type="text/css">
        .laydate-time-list > li {
            width: 50% !important;
        }

        .laydate-time-list > li:last-child {
            display: none;
        }
    </style>
</head>
<body>
<form class="layui-form layui-form-pane" style="margin-left: 85px;">
    <br>

    <div class="layui-form-item">
        <label class="layui-form-label">教室编号</label>
        <div class="layui-input-inline">
            <input type="text" name="roomId" id="roomId" lay-verify="required" autocomplete="off"
                   class="layui-input" value="${user.userId}" readonly>
        </div>
        <label class="layui-form-label">使用日期</label>
        <div class="layui-input-inline">
            <input type="text" name="startDate"
                   id="startDate" readonly
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开始时间</label>
        <div class="layui-input-inline">
            <input type="text" name="startTime"
                   id="startTime" readonly
                   autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">结束时间</label>
        <div class="layui-input-inline">
            <input type="text" name="startTime"
                   id="endTime" readonly
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text" style="width: 608px;height: 210px;">
        <label class="layui-form-label">申请原因(用途)</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容" id="applyReason" class="layui-textarea" style="height: 100%;"></textarea>
        </div>
    </div>

    <div class="layui-form-item" style="text-align: center;bottom: 11px;width: 608px;position: fixed;">
        <div class="layui-input-block" style="margin: auto">
            <button type="button" class="layui-btn" id="submit" style="height: 43px;width: 100px;font-size: 18px;">
                提交
            </button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript" src="${path}/static/js/common/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="${path}/static/js/layui/layui.js"></script>
<%--suppress JSUnfilteredForInLoop --%>
<script type="text/javascript">
    let _layer;
    layui.use(['laydate', 'layer'], function () {
        let laydate = layui.laydate;
        let layer = layui.layer;
        _layer = layer;
        laydate.render({
            elem: '#startDate',
            type: 'date',
            min: getDateFormat('2', 0, '-'),
            max: 7,
            done: function (value) {
                value = value.replace(/-/g, '');
                let now = getDateFormat('2');
                let oneWeekLater = getDateFormat('2', 7);
                if (value !== '' && value !== null && value !== undefined
                    && parseInt(value) >= parseInt(now)
                    && parseInt(value) <= parseInt(oneWeekLater)
                ) {
                    laydate.render({
                        elem: '#startTime',
                        type: 'time',
                        format: 'HH:mm',
                        min: '07:00:00',
                        max: '22:00:00',
                        btns: ['clear', 'confirm'],
                        done: function (value) {
                            let date = $("#startDate").val();
                            if (value !== '' && date === '') {
                                layer.alert("请选择预约日期");
                            }
                        }
                    });
                    laydate.render({
                        elem: '#endTime',
                        type: 'time',
                        format: 'HH:mm',
                        min: '07:15:00',
                        max: '22:15:00',
                        btns: ['clear', 'confirm'],
                        done: function (value) {
                            let date = $("#startDate").val();
                            if (value !== '' && date === '') {
                                layer.alert("请选择预约日期");
                            }
                        }
                    });
                }
            }
        });
    });

    $('#submit').click(function () {
        let startDate = $('#startDate').val();
        let time1 = $('#startTime').val();
        let time2 = $('#endTime').val();
        let startTime = startDate + ' ' + time1 + ':00';
        let endTime = startDate + ' ' + time2 + ':00';
        let applyReason = $('#applyReason').val();
        if (startDate === undefined || startDate.replace(/\s+/g, '') === '') {
            layer.alert('请完善预约时间信息');
            return false;
        } else if (time1 === undefined || time1.replace(/\s+/g, '') === '') {
            layer.alert('请填写开始时间');
            return false;
        } else if (time2 === undefined || time2.replace(/\s+/g, '') === '') {
            layer.alert('请填写结束时间');
            return false;
        } else if (parseInt(startTime.replace(/-|\s+|:/g, '')) < parseInt(getDateFormat('1'))) {
            layer.alert('开始预约时间不应早于现在');
            return false;
        } else if (timeDifference(time1, time2) < 0) {
            layer.alert("结束时间不应早于开始时间");
            return false;
        } else if (timeDifference(time1, time2) < 15) {
            layer.alert("教室使用时间不应低于15分钟");
            return false;
        } else if (timeDifference(time1, time2) > 180) {
            layer.alert("使用时间过长，请控制在3小时以内");
            return false;
        } else if (applyReason === undefined || applyReason.replace(/\s+/g, '') === '') {
            layer.alert('请填写申请原因(用途)');
            return false;
        }
        $.ajax({
            type: 'POST',
            url: '${path}/room/approve',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
            data: {
                'roomId': $('#roomId').val(),
                'startTime': startTime,
                'endTime': endTime,
                'applyReason': applyReason
            },
            dataType: 'json',
            success: function (data) {
                _layer.alert(data.message);
            }
        })
    });

    //计算时间差（相差分钟）
    function timeDifference(startTime, endTime) {
        let start1 = startTime.split(":");
        let startAll = parseInt(start1[0] * 60) + parseInt(start1[1]);

        let end1 = endTime.split(":");
        let endAll = parseInt(end1[0] * 60) + parseInt(end1[1]);
        return endAll - startAll;
    }

    function getDateFormat(formatType, days, sep1, sep2) {
        let date = new Date();
        if (days !== undefined) {
            date.setDate(date.getDate() + days);
        }
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();
        month = month < 9 ? '0' + month : month;
        day = day < 9 ? '0' + day : day;
        hour = hour < 9 ? '0' + hour : hour;
        minute = minute < 9 ? '0' + minute : minute;
        second = second < 9 ? '0' + second : second;

        if (formatType === '1') {
            if (sep1 !== undefined && sep2 !== undefined) {
                return year + sep1 + month + sep1 + day + ' ' + hour + sep2 + minute + sep2 + second;
            } else {
                return year + month + day + hour + minute + second;
            }
        } else if (formatType === '2') {
            if (sep1 !== undefined) {
                return year + sep1 + month + sep1 + day;
            } else {
                return year + month + day;
            }
        }
    }
</script>
</html>