<%--suppress HtmlFormInputWithoutLabel,HtmlUnknownAttribute--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<html>
<head>
    <title>编辑用户</title>
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
<form class="layui-form">
    <br>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">教室编号</label>
            <div class="layui-input-inline">
                <input type="text" name="roomId" id="roomId" lay-verify="required|uname" autocomplete="off"
                       class="layui-input" value="${showApplyUseRoomId}">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">使用日期</label>
            <div class="layui-input-inline">
                <input type="text" name="startDate"
                       id="startDate" readonly
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">开始时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime"
                       id="startTime" readonly
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">结束时间</label>
            <div class="layui-input-inline">
                <input type="text" name="startTime"
                       id="endTime" readonly
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="text-align: center;bottom: 4px;width: 100%;position: fixed">
        <div class="layui-input-block" style="margin: auto">
            <button type="button" class="layui-btn" id="submit">提交</button>
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
            min: getDateFormat(),
            max: 7,
            done: function (value) {
                value = value.replace(/-/g, '');
                let now = getDateFormat().replace(/-/g, '');
                let oneWeekLater = getDateFormat(7).replace(/-/g, '');
                console.log('value --', value);
                console.log('value is \'\'?', value === '');
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

        // layer.alert(getDateFormat('1'));
        // layer.alert(getDateFormat('1'));
    });

    $('#submit').click(function () {
        $.ajax({
            type: 'POST',
            url: '${path}/room/applyUse',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
            data: {
                'roomId': $('#roomId').val(),
                'startTime': $('#startDate').val() + ' ' + $('#startTime').val()+':00',
                'endTime': $('#startDate').val() + ' ' + $('#endTime').val()+':00'
            },
            dataType: 'json',
            success: function (data) {
                _layer.alert(data.message);
            }
        })
    });

    function getDateFormat(days) {
        let date = new Date();
        if (days !== undefined) {
            date.setDate(date.getDate() + days);
        }
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();
        // let hour = date.getHours();
        // let minute = date.getMinutes();
        // let second = date.getSeconds();
        month = month < 9 ? '0' + month : month;
        day = day < 9 ? '0' + day : day;
        // hour = hour < 9 ? '0' + hour : hour;
        // minute = minute < 9 ? '0' + minute : minute;
        // second = second < 9 ? '0' + second : second;
        return year + '-' + month + '-' + day;
    }

</script>
</html>