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
                <input type="number" id="phone" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">房间类型</label>
            <div class="layui-input-inline">
                <select name="roomType" id="roomType" lay-search></select>
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
    var fatherOrMother = parent;
    var TABLE_INS;

    layui.use(['form', 'table', 'layer'], function () {

        var table = layui.table,
            form = layui.form,
            layer = layui.layer;
        // var layer = layui.layer;

        /**
         * 表格参数配置开始
         */
        var tableIns = table.render({
            id: 'roomListData',
            even: true,
            width: "auto",
            skin: "row",
            elem: '#roomList',
            method: "POST",
            toolbar: 'default',
            loading: true,
            height: 'full-58',
            url: '${path}/room/listRooms', //数据接口
            page: true,  //开启分页
            parseData: function (data) {
                console.log("表数据:", data);
                return {
                    "code": data.status,
                    "msg": data.message,
                    "count": data.customProp,
                    "data": data.resource
                };
            },
            cols: [[ //表头
                {field: 'chk', type: 'checkbox', fixed: 'left'},
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
                    templet: '<div><div class="layui-btn-group">' +
                        '  <button type="button" class="layui-btn layui-btn-xs id{{d.id}}" onclick="showEditUser({{d.id}})">' +
                        '    <i class="layui-icon">&#xe642;</i>' +
                        '  </button>' +
                        '  <button type="button" class="layui-btn layui-btn-xs layui-btn-danger id{{d.id}}" onclick="deleteUser({{d.id}})">' +
                        '    <i class="layui-icon">&#xe640;</i>' +
                        '</div></div>',
                    width: 85
                }
            ]], done: function () {
                console.log("渲染完成回调");
                $(".id1").hide();
                $(".id2").hide();
            }
        });
        TABLE_INS = tableIns;
        // console.log("表格参数: ", tableIns);

        //监听表格复选框点击
        table.on('checkbox(roomListLayFilter)', function (data) {
            console.log("单行数据(这是单击该行时获取到的该行的数据):", data); //这是单击该行时获取到的该行的数据
            console.log("所有行数据", table.checkStatus('roomListData'));
        });

        /**
         * layui按钮点击监听
         * @type {*|jQuery|layUI}
         */
        var $ = layui.$, active = {
            getCheckData: function () { //获取选中数据
                var checkStatus = table.checkStatus('roomListData'),
                    data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            },
            getCheckLength: function () { //获取选中数目
                var checkStatus = table.checkStatus('roomListData'),
                    data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
            },
            isAll: function () { //验证是否全选
                var checkStatus = table.checkStatus('roomListData');
                layer.msg(checkStatus.isAll ? '全选' : '未全选')
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
        table.on('toolbar(roomListLayFilter)', function (data) {
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
                    layer.alert("编辑");
                    break;
                case 'delete':
                    var msg = '你没有选择任何数据';
                    var selectedData = table.checkStatus("roomListData").data;
                    if (selectedData.length < 1) {
                        layer.alert(msg);
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
                    layer.confirm(msg, {
                            btn: ['当然', '再考虑'] //可以无限个按钮
                        },
                        function (index) {
                            layer.close(index);
                            layer.load(1);
                            $.ajax({
                                type: "post",
                                url: "${path}/user/deleteUsers",
                                data: {"ids": ids},
                                dataType: "json",
                                success: function (data) {
                                    if (data.flag) {
                                        layer.close(layer.index);
                                        layer.alert(data.message, {
                                            yes: function () {
                                                tableIns.reload();
                                                layer.close(layer.index);
                                            }
                                        });
                                    } else {
                                        layer.close(layer.index);
                                        layer.alert(data.message);
                                    }
                                }, error: function (e) {
                                    console.log(e);
                                    layer.close(layer.index);
                                    layer.alert("请求失败了");
                                }
                            });
                        },
                        function (index) {
                            layer.close(index);
                        });
                    break;
            }
        });

        $.ajax({
            url: '${path}/room/getRoomTypeSelectList',
            type: "post",
            dataType: "json",
            contentType: "application/json",
            async: false,//这得注意是同步
            success: function (result) {
                let html = '<option value="" selected></option>';
                let resultData = result.resource;
                for (let data in resultData) {
                     html += '<option value = "' + resultData[data].attributeKey + '">' + resultData[data].attributeValue + '</option>'
                }
                $("#roomType").html(html);
            }
        });
        form.render('select');//需要渲染一下
    });

    function showEditUser(id) {
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

    function deleteUser(id) {
        if (id <= 981009) {
            return null;
        }
        layer.confirm('确定要干掉这个人么?', {
            btn: ['当然', '再考虑']
        }, function () {
            layer.load(1);
            $.ajax({
                type: "post",
                url: "${path}/user/deleteUser",
                data: {"id": id},
                dataType: "json",
                async: false,
                success: function (data) {
                    console.log("删除用户成功回调:", data);
                    if (data.flag) {
                        layer.closeAll();
                        layer.alert(data.message);
                        TABLE_INS.reload();
                    }
                    if (data.errInfo !== null) {
                        goToErrorPage(data);
                    }
                }
            })
        }, function () {
            layer.closeAll();
        })
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
</script>
</body>
</html>