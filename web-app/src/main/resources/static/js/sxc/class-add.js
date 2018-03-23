var classForm = $('#classForm');
classForm.validate({
    errorElement: 'span',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
        className: {
            required: true,
            stringMaxLength: 50
        },
        xiBieName: {
            required: true
        },
        schoolName: {
            required: true
        },
    },

    highlight: function (element) {
        $(element)
            .closest('.form-group').addClass('has-error');
    },

    unhighlight: function (element) {
        $(element)
            .closest('.form-group').removeClass('has-error');
    },

    success: function (label) {
        label
            .closest('.form-group').removeClass('has-error');
    }


});


$(function () {
    $("#btnSave").click(function () {
        if (classForm.valid()) { //验证通过
            $("#btnSave").attr("disabled", true);
            Shade.blockUI($("#classBody"));
            $.post("/sxc/class/add",
                $("#classForm").serialize(),
                function (data) {
                    $("#btnSave").attr("disabled", false);
                    Shade.unblockUI($("#classBody"));
                    if (data.result == true) {
                        BootboxExt.alert("新增成功", function (res) {
                            location.href = "/sxc/class/search";
                        });
                    } else {
                        BootboxExt.alert("新增失败", function (res) {
                            window.location.reload();
                        });
                    }
                });
        }
    });

    $("#btnUpdate").click(function () {
        if (classForm.valid()) { //验证通过
            $("#btnUpdate").attr("disabled", true);
            Shade.blockUI($("#classBody"));
            $.post("/sxc/class/update",
                $("#classForm").serialize(),
                function (data) {
                    $("#btnUpdate").attr("disabled", false);
                    Shade.unblockUI($("#classBody"));
                    if (data.result == true) {
                        BootboxExt.alert("修改成功", function (res) {
                            location.href = "/sxc/class/search";
                        });
                    } else {
                        BootboxExt.alert("修改失败", function (res) {
                            window.location.reload();
                        });
                    }
                });
        }
    });
});

/**-------------------------------------------学校弹框开始-----------------------------------------------------------------------------------*/

/**
 * 是否选中学校。
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function schoolFormatter(value, row, index) {
    if (row.schoolId === $("#schoolId").val()) {
        return {
            disabled: false,//设置是否可用
            checked: true//设置选中
        };
    }
    return value;
}

/**
 * 学校弹出框查询参数。
 * @param params
 * @returns {{pageSize, pageNumber, mid: (*|jQuery), subMid: (*|jQuery), subMidShortName: (*|jQuery), subMidName: (*|jQuery)}}
 */
function schoolQueryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        pageSize: params.pageSize,   //页面大小
        pageNumber: params.pageNumber,  //页码
        schoolId: $("#schoolId").val(),
        schoolCode: $("#schoolCodeSearch").val(),
        schoolName: $("#schoolNameSearch").val(),
    };
    return temp;
}

/**
 * 学校弹出框查询。
 */
function refreshAlertSchoolData() {
    $("#schoolTable").bootstrapTable('refresh', {
        url: '/sxc/school/search-list',
        queryParams: schoolQueryParams
    });
}

/**
 * 选中学校信息提交。
 */
function selectSchool() {
    var selectInfo = $("#schoolTable").bootstrapTable('getSelections')[0];
    if (selectInfo == null || selectInfo.length < 1) {
        BootboxExt.alert("请选择一个学校");
        return;
    }
    $('#schoolModal').modal('hide');
    $("#schoolId").val(selectInfo == undefined ? "" : selectInfo.schoolId);
    $("#schoolName").val(selectInfo == undefined ? "" : selectInfo.schoolName);
    $('#schoolName').valid();
    clearXiBie();
}

/**
 * 初始化表格数据。
 */
function initSchoolTable() {

    $('#schoolModal').modal('show');
    //先销毁表格
    $('#schoolTable').bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#schoolTable").bootstrapTable({
        method: "GET",  //使用get请求到服务器获取数据
        url: '/sxc/school/search-list', //获取数据的Servlet地址
        striped: true,  //表格显示条纹
        showRefresh: true,  //显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParamsType: '',
        queryParams: schoolQueryParams,
        singleSelect: true,
        uniqueId: "schoolId",
        columns: [
            {
                field: 'checked',
                checkbox: true,
                formatter: schoolFormatter
            },
            {
                field: 'schoolId',
                title: '学校id'
            }, {
                field: 'schoolCode',
                title: '学校代码'
            }, {
                field: 'schoolName',
                title: '学校名称'
            }],
        onLoadError: function () {  //加载失败时执行
            BootboxExt.alert("加载数据失败", {time: 1500, icon: 2});
        }
    });
}

/**
 * 清空选中学校
 */
function clearSchool() {
    $("#schoolId").val("");
    $("#schoolName").val("");
    clearXiBie();
}

/**
 * 学校弹出框重置。
 */
function cleanSchoolQuery() {
    $("#schoolCodeSearch").val('');
    $("#schoolNameSearch").val('');
    refreshAlertSchoolData();
}

/**-------------------------------------------学校弹框结束-----------------------------------------------------------------------------------*/

/**
 * 是否选中系别。
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function xiBieFormatter(value, row, index) {
    if (row.xiBieId === $("#xiBieId").val()) {
        return {
            disabled: false,//设置是否可用
            checked: true//设置选中
        };
    }
    return value;
}

/**
 * 系别弹出框查询参数。
 * @param params
 * @returns {{pageSize, pageNumber, mid: (*|jQuery), subMid: (*|jQuery), subMidShortName: (*|jQuery), subMidName: (*|jQuery)}}
 */
function xiBieQueryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        pageSize: params.pageSize,   //页面大小
        pageNumber: params.pageNumber,  //页码
        schoolId: $("#schoolId").val(),
        xiBieName: $("#xiBieNameSearch").val(),
        xiBieId: $("#xiBieId").val(),
    };
    return temp;
}

/**
 * 系别弹出框查询。
 */
function refreshAlertXiBieData() {
    $("#xiBieTable").bootstrapTable('refresh', {
        url: '/sxc/xiBie/search-list',
        queryParams: xiBieQueryParams
    });
}

/**
 * 选中系别信息提交。
 */
function selectXiBie() {
    var selectInfo = $("#xiBieTable").bootstrapTable('getSelections')[0];
    if (selectInfo == null || selectInfo.length < 1) {
        BootboxExt.alert("请选择一个系别");
        return;
    }
    $('#xiBieModal').modal('hide');
    $("#xiBieId").val(selectInfo == undefined ? "" : selectInfo.xiBieId);
    $("#xiBieName").val(selectInfo == undefined ? "" : selectInfo.xiBieName);
    $('#xiBieName').valid();
}

/**
 * 初始化表格数据。
 */
function initXiBieTable() {
    var schoolStr = $("#schoolId").val();
    if(schoolStr == null || schoolStr == ''){
        BootboxExt.alert("请先选择学校");
        return;
    }
    $('#xiBieModal').modal('show');
    //先销毁表格
    $('#xiBieTable').bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#xiBieTable").bootstrapTable({
        method: "GET",  //使用get请求到服务器获取数据
        url: '/sxc/xiBie/search-list', //获取数据的Servlet地址
        striped: true,  //表格显示条纹
        showRefresh: true,  //显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParamsType: '',
        queryParams: xiBieQueryParams,
        singleSelect: true,
        uniqueId: "xiBieId",
        columns: [
            {
                field : 'checked',
                checkbox: true,
                formatter: xiBieFormatter
            }, {
                field: 'xiBieId',
                title: '系别号'
            }, {
                field: 'xiBieName',
                title: '系别名称'
            }],
        onLoadError: function () {  //加载失败时执行
            BootboxExt.alert("加载数据失败", {time: 1500, icon: 2});
        }
    });
}

/**
 * 清空选中系别
 */
function clearXiBie() {
    $("#xiBieId").val("");
    $("#xiBieName").val("");
}


/**
 * 系别弹出框重置。
 */
function cleanXiBieQuery() {
    $("#schoolCodeSearch").val('');
    $("#schoolNameSearch").val('');
    $("#xiBieNameSearch").val('');
    refreshAlertXiBieData();
}
/**-------------------------------------------系别弹框结束-----------------------------------------------------------------------------------*/




