$(function() {
    $("#btnSave").click(function () {
        if (roleForm.valid()) { //验证通过
            $("#btnSave").attr("disabled", true);
            Shade.blockUI($("#roleBody"));
            $.post("/role/add",
                $("#addFrom").serialize(),
                function (data) {
                    $("#btnSave").attr("disabled", false);
                    Shade.unblockUI($("#roleBody"));
                    if (data.result == true) {
                        BootboxExt.alert("新增成功", function (res) {
                            location.href = "/role/search";
                        });
                    } else {
                        BootboxExt.alert("新增失败", function (res) {
                            window.location.reload();
                        });
                    }
                });
        }
    })
    $("#btnUpdate").click(function () {
        if (roleForm.valid()) { //验证通过
            $("#btnUpdate").attr("disabled", true);
            Shade.blockUI($("#roleBody"));
            $.post("/role/update",
                $("#updateFrom").serialize(),
                function (data) {
                    $("#btnUpdate").attr("disabled", false);
                    Shade.unblockUI($("#roleBody"));
                    if (data.result == true) {
                        BootboxExt.alert("修改成功", function (res) {
                            location.href = "/role/search";
                        });
                    } else {
                        BootboxExt.alert("修改失败", function (res) {
                            window.location.reload();
                        });
                    }
                })
        }
    });
    $("#tree-modal").on("hidden.bs.modal",function(e){
        $(this).removeData();
        $.jstree.reference("#tree_cl").destroy(false);
    });
    $("#view-modal").on("hidden.bs.modal",function(e){
        $(this).removeData();
        $.jstree.reference("#view_tree").destroy(false);
    });
    var rightArr = "";
    $("#tree-btnSave").click(function () {
        $("#tree-btnSave").attr("disabled", true);
        Shade.blockUI($("#roleBody"));
        var rightStrArray = getCheckboxTreeSelNode();
        if(rightStrArray.length == 0){
            $("#tree-btnSave").attr("disabled", false);
            Shade.unblockUI($("#roleBody"));
            BootboxExt.alert("请先选择权限信息!");
            return;
        }
        for (var i = 0; i < rightStrArray.length; i++){
            if(i > 0){
                rightArr += ",";
            }
            rightArr += rightStrArray[i];
        }

        var roleId = $("#roleId").val();
        $.get("/role_right/add", { rightArr:rightArr, roleId: roleId},
            function(data){  //此处是回调函数 接收从后台传回的值
                $("#tree-btnSave").attr("disabled", false);
               Shade.unblockUI($("#roleBody"));
                if(data.result == true){
                    BootboxExt.alert("分配成功", function (res) {
                        location.href = "/role/search";
                    });
                }
            }, "json");
    });
});



function treeRole(roleId){
    $("#roleId").val(roleId);
    var checkId = new Array();
    $.get("/role_right/getRoleRelRight", {roleId: roleId},
        function(data){  //此处是回调函数 接收从后台传回的值
            var dataArr = data.result.split(",");
            for (var j = 0; j < dataArr.length; j++) {
                checkId.push(dataArr[j]);
            }
        }, "json");
    var menuTree = $("#tree_cl").bind("loaded.jstree",function(e,data){
        $("#tree_cl").jstree("open_all");
        $("#tree_cl").find("li").each(function(){
            if(checkId == null){
                // $("#tree_cl").jstree("check_node",jQuery(this));
            }else {
                for(var i=0;i<checkId.length;i++){
                    var currentNode = $('#tree_cl').jstree("get_node", checkId[i]);
                    if(jQuery(this).attr("id") == checkId[i] && $('#tree_cl').jstree("is_leaf", currentNode)){
                        jQuery("#tree_cl").jstree("check_node",jQuery(this));
                    }
                }
            }
        });
    }).jstree({
        "core" : {
            "data":{
                "url":'/right/getRightTree/',
                "dataType":"json",
                "cache":false
            },
            "attr":{
                "class":"jstree-checked"
            }
        },
        "types" : {
            "default" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "file" : {
                "icon" : "glyphicon glyphicon-ok"
            }
        },
        "checkbox" : {
            "keep_selected_style" : false,
            "real_checkboxes" : true,
            // "three_state": false,
            "tie_selection":true,
        },
        "plugins" : ["dnd", "search",
            "types", "wholerow","checkbox"
        ]
    });
}

function getCheckboxTreeSelNode(){
    var ids = Array();
    $("#tree_cl").find("li").each(function(){
        var liid = $(this).attr("id");
        var currentNode = $('#tree_cl').jstree("get_node", liid);
        if($("#tree_cl").jstree("is_undetermined",currentNode)){
            ids.push(liid);
        }
    });
    var nodes = $("#tree_cl").jstree("get_checked");
    for(var i=0, checkedLength = nodes.length; i<checkedLength;i++){
        ids.push(nodes[i]);
    }
    return ids;
}

// function getCheckboxTreeSelNode(){
//
//     var ids = Array();
//     $("#tree_cl").find(".jstree-undetermined").each(function(){
//         debugger;
//         ids.push($(this).attr("id"));
//     });
//
//
//     //var checkedNodes = $('#tree_cl').jstree("get_all_checked");
//     var nodes = $("#tree_cl").jstree("get_checked");
//     for(var i=0, checkedLength = nodes.length; i<checkedLength;i++){
//         ids.push(nodes[i]);
//     }
//     return ids;
// }


function viewRole(roleId) {
    $("#view_tree").jstree({
        "plugins" : ["types","themes", "wholerow"],
        "core" : {
            "themes" : { "stripes" : true },  // 条纹主题
            "animation": 0,
            'data' : {
                'url' : function (node) {
                    return '/right/viewRightTree/'+roleId;
                }
            }
        },
        "types" : {
            "default" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "file" : {
                "icon" : "glyphicon glyphicon-ok"
            }
        }
    }).bind("loaded.jstree", function (e, data) {
        $("#viewTree p").remove();
        if(data.instance._cnt==0){
            //BootboxExt.alert("该角色没有任何权限");
            $("#viewTree").append("<p>该角色没有任何权限</p>");
        }
        data.instance.open_all();

    });
}

function deleteRole(id) {
    BootboxExt.confirm("确认删除吗？", function (res) {
        if (res) {
            $.get("/role/remove", { id: id}, function (data)
            {
                if(data.result == true){
                    BootboxExt.alert("删除成功", function (res) {
                        location.href = "/role/search";
                    });
                }else{
                    BootboxExt.alert("删除失败", function (res) {
                        window.location.reload();
                    });
                }
            }, "json");
        }
    });
}
var roleVo = new Object();
function setSearchParam() {
    roleVo.roleName = $("#roleName").val();
    roleVo.roleStatus = $("#roleStatus").val();
    roleVo.createTimeBegin = $("#createTimeBegin").val();
    roleVo.craeteTimeEnd = $("#craeteTimeEnd").val();
}
jQuery(document).ready(function () {
    setSearchParam();
    $("#createTimeBegin").datetimepicker({
        language: 'zh-CN',
        timePicker: false,
        minView:"month",
        format: "yyyy-mm-dd",
        autoClose: true,
        clearBtn: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $("#createTimeEnd").datetimepicker("setStartDate", $("#createTimeBegin").val());
        $("#createTimeBegin").datetimepicker("hide");
    });
    $("#createTimeEnd").datetimepicker({
        language: 'zh-CN',
        timePicker: false,
        minView:"month",
        format: "yyyy-mm-dd",
        autoClose: true,
        clearBtn: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $("#createBegin").datetimepicker("setEndDate", $("#createTimeEnd").val());
        $("#createTimeEnd").datetimepicker("hide");
    });
    /**
     * 交易开始时间清空事件
     */
    $("#createTimeBeginClear").click(function () {
        $("#createTimeBegin").val("");
    });
    /**
     * 交易结束时间清空事件
     */
    $("#createTimeEndClear").click(function () {
        $("#createTimeEnd").val("");
    });
});
