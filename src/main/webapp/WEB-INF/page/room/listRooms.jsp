<%--suppress HtmlUnknownAttribute,JSValidateJSDoc,JSUnresolvedVariable,SpellCheckingInspection --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>教室列表</title>
    <link rel="stylesheet" href="${path}/static/css/layui/layui.css" media="all">
</head>
<body>
<div style="margin-top: 30px"></div>
<form class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">教室编号</label>
            <div class="layui-input-inline">
                <input type="number" id="roomId_q" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">房间类型</label>
            <div class="layui-input-inline">
                <select name="roomType" id="roomType" lay-filter="roomType"></select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">教学楼</label>
            <div class="layui-input-inline">
                <select name="buildings" id="buildings" lay-filter="buildings"></select>
            </div>
        </div>
    </div>
</form>
<table id="roomList" lay-filter="roomListLayFilter" style="height: 100%"></table>
<%--lay-filter ： 事件过滤器（公用属性），主要用于事件的精确匹配，跟选择器类似--%>
<div class="layui-btn-group getSelectedRowDetail">
    <button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
    <button class="layui-btn" data-type="getCheckLength">获取选中数目</button>
    <button class="layui-btn" data-type="isAll">验证是否全选</button>
</div>
<script src="${path}/static/js/common/jquery-3.4.1.min.js" type="text/javascript"></script>
<script src="${path}/static/js/layui/layui.js"></script>
<%--suppress JSUnfilteredForInLoop --%>
<script type="text/javascript">
    let fatherOrMother = parent;
    let select_html_init = '<option value="" selected></option>';
    let select_html_1, select_html_2;
    let tableData;
    let TABLE_INS;
    let layer;
    let form;
    let table;

    function getValue(elementId) {
        return $('#' + elementId).val();
    }

    layui.use(['form', 'table', 'layer'], function () {
        let _table = layui.table,
            _form = layui.form,
            _layer = layui.layer;
        layer = _layer;
        form = _form;
        table = _table;
        /**
         * 表格参数配置开始
         */
        var tableIns = _table.render(getTableRender());
        TABLE_INS = tableIns;
        // console.log("表格参数: ", tableIns);

        //监听表格复选框点击
        _table.on('checkbox(roomListLayFilter)', function (data) {
            console.log("单行数据(这是单击该行时获取到的该行的数据):", data); //这是单击该行时获取到的该行的数据
            console.log("所有行数据", _table.checkStatus('roomListData'));
        });

        /**
         * layui按钮点击监听
         * @type {*|jQuery|layUI}
         */
        var $ = layui.$, active = {
            getCheckData: function () { //获取选中数据
                var checkStatus = _table.checkStatus('roomListData'),
                    data = checkStatus.data;
                _layer.alert(JSON.stringify(data));
            },
            getCheckLength: function () { //获取选中数目
                var checkStatus = _table.checkStatus('roomListData'),
                    data = checkStatus.data;
                _layer.msg('选中了：' + data.length + ' 个');
            },
            isAll: function () { //验证是否全选
                var checkStatus = _table.checkStatus('roomListData');
                _layer.msg(checkStatus.isAll ? '全选' : '未全选')
            }
        };
        //点击事件参数配置
        $('.getSelectedRowDetail .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        /**
         * 工具栏监听
         */
        _table.on('toolbar(roomListLayFilter)', function (data) {
            switch (data.event) {
                case 'add':
                    parent.layer.open({
                        type: 2,
                        moveOut: true,
                        scrollbar: false,
                        title: '添加一个用户',
                        closeBtn: 1,
                        area: ['1000px', '620px'],
                        <%--content: '${path}/user/showAddUser'--%>
                        content: '${path}/user/showAddUser',
                        end: function () {
                            layui.table.reload("roomListData");
                        }
                    });
                    break;
                case 'update':
                    _layer.alert("编辑");
                    break;
                case 'delete':
                    var msg = '你没有选择任何数据';
                    var selectedData = _table.checkStatus("roomListData").data;
                    if (selectedData.length < 1) {
                        _layer.alert(msg);
                        return;
                    } else if (selectedData.length === 1) {
                        msg = '确定要删除这条数据蛮?';
                    } else {
                        msg = '确定要删除这些数据嘛?';
                    }
                    var ids = [];
                    for (var i = 0; i < selectedData.length; i++) {
                        ids[i] = selectedData[i].id
                    }
                    _layer.confirm(msg, {
                            btn: ['当然', '再考虑'] //可以无限个按钮
                        },
                        function (index) {
                            _layer.close(index);
                            _layer.load(1);
                            $.ajax({
                                type: "post",
                                url: "${path}/user/deleteUsers",
                                data: {"ids": ids},
                                dataType: "json",
                                success: function (data) {
                                    if (data.flag) {
                                        _layer.close(_layer.index);
                                        _layer.alert(data.message, {
                                            yes: function () {
                                                tableIns.reload();
                                                _layer.close(_layer.index);
                                            }
                                        });
                                    } else {
                                        _layer.close(_layer.index);
                                        _layer.alert(data.message);
                                    }
                                }, error: function (e) {
                                    console.log(e);
                                    _layer.close(_layer.index);
                                    _layer.alert("请求失败了");
                                }
                            });
                        },
                        function (index) {
                            _layer.close(index);
                        });
                    break;
            }
        });

        //获取查询条件的选择框数据
        $.ajax({
            url: '${path}/room/getSelectList',
            type: "post",
            dataType: "json",
            contentType: "application/json",
            async: false,//这得注意是同步
            success: function (result) {
                select_html_1 = select_html_init;
                select_html_2 = select_html_init;
                let resultData = result.resource;
                for (let data in resultData.roomTypes) {
                    select_html_1 += '<option value = "' + resultData.roomTypes[data].attributeKey + '">' + resultData.roomTypes[data].attributeValue + '</option>'
                }
                for (let data in resultData.buildings) {
                    select_html_2 += '<option value = "' + resultData.buildings[data].buildingId + '">' + resultData.buildings[data].buildingName + '</option>'
                }
                $("#roomType").html(select_html_1);
                $("#buildings").html(select_html_2);
            }
        });
        _form.render('select');//需要渲染一下

        _form.on('select(roomType)', function () {
            queryByChoises();
        });
        _form.on('select(buildings)', function () {
            queryByChoises();
        });
    });

    $("#roomId_q").keyup(function () {
        queryByRoomId($("#roomId_q"));
    });
    $("#roomId_q").change(function () {
        queryByRoomId($("#roomId_q"));
    });

    function queryByChoises() {
        let roomType = $("#roomType").val();
        let buildingId = $("#buildings").val();
        if (roomType !== '' && roomType !== undefined && roomType !== null) {
            table.render(getTableRender());
            $("#roomId_q").val('');
        } else if (buildingId !== '' && buildingId !== undefined && buildingId !== null) {
            table.render(getTableRender());
        } else return false;
    }


    function queryByRoomId(_this) {
        if (_this.val().length < 4) {
            $("#roomType").removeAttr('disabled');
            $("#buildings").removeAttr('disabled');
            select_html_init = '<option value="" selected>教室编号查询...</option>';
            $("#roomType").html(select_html_1);
            $("#buildings").html(select_html_2);
            form.render('select');
        } else if (_this.val().length >= 4) {
            $("#roomType").attr('disabled', 'disabled');
            $("#buildings").attr('disabled', 'disabled');
            $("#roomType").html(select_html_init);
            $("#buildings").html(select_html_init);
            form.render('select');
            table.render(getTableRender());
        }
    }

    function showUseInfo(id) {
        parent.layer.open({
            type: 2,
            moveOut: true,
            scrollbar: false,
            title: '编辑用户 ' + id,
            closeBtn: 1,
            area: ['1000px', '620px'],
            content: '${path}/user/showEditUser/' + id,
            end: function () {
                layui.table.reload("roomListData");
            }
        });
    }

    function applyUse(roomId) {
        parent.layer.open({
            type: 2,
            moveOut: true,
            scrollbar: false,
            title: '预约教室: ' + roomId,
            closeBtn: 1,
            area: ['785px', '500px'],
            content: '${path}/room/showApplyUse/' + roomId,
            end: function () {
                layui.table.reload("roomListData");
            }
        });
    }

    function goToErrorPage(errData) {
        //创建form表单
        var temp_form = document.createElement("form");
        temp_form.action = "${path}/error";
        //如需打开新窗口，form的target属性要设置为'_blank'
        temp_form.target = "_self";
        temp_form.method = "post";
        temp_form.style.display = "none";
        //添加参数
        for (var key in errData) {
            var opt = document.createElement("textarea");
            opt.name = key;
            opt.value = errData[key];
            temp_form.appendChild(opt);
        }
        document.body.appendChild(temp_form);
        //提交数据
        temp_form.submit();
    }

    function getTableRender() {
        return {
            id: 'roomListData',
            url: '${path}/room/queryRooms', //数据接口
            method: "POST",
            where: {
                'roomId': getValue('roomId_q'),
                'roomType': getValue('roomType'),
                'buildingId': getValue('buildings')
            },
            even: true,
            width: "auto",
            skin: "row",
            elem: '#roomList',
            // toolbar: 'default',
            loading: true,
            height: 'full-58',
            page: true,  //开启分页
            parseData: function (data) {
                tableData = data;
                console.log("表数据:", tableData);
                return {
                    "code": tableData.status,
                    "msg": tableData.message,
                    "count": tableData.customProp,
                    "data": tableData.resource
                };
            },
            cols: [[ //表头
                // {field: 'chk', type: 'checkbox', fixed: 'left'},
                {field: 'roomId', title: '房间编号', width: 120, sort: true, fixed: 'left'},
                {field: 'roomName', title: '房间名', width: 400},
                {field: 'roomType', title: '房间类型', width: 300, sort: true},
                {field: 'seats', title: '座位数', width: 120},
                {
                    field: 'building.buildingName',
                    title: '所属教学楼',
                    templet: '<div>{{d.building.buildingName}}</div>',
                    width: 190
                },
                {field: 'roomState', title: '能否预约', width: 130},
                {field: 'lockReason', title: '不能预约原因', width: 400},

                {
                    field: 'operation', title: '操作', fixed: 'right',
                    templet: ' <div><div class="layui-btn-group">' +
                        '  <button type="button" class="layui-btn layui-btn-xs id{{d.id}}" onclick="showUseInfo({{d.roomId}})">' +
                        '    <i class="layui-icon">&#xe615;</i>' +
                        '  </button>' +
                        '  <button type="button" class="layui-btn layui-btn-xs id{{d.id}}" onclick="applyUse({{d.roomId}})">' +
                        '    <i class="layui-icon">&#xe66c;</i>' +
                        '</div></div>',
                    width: 85
                }
            ]], done: function () {
                console.log("渲染完成回调");
                $(".id1").hide();
                $(".id2").hide();
            }
        };
    }

</script>
</body>
</html>