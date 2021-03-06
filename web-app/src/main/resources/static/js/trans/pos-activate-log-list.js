var posActivateLogVo = new Object();

function setSearchParam() {
    posActivateLogVo.mid = $("#mid").val();
    posActivateLogVo.subMid = $("#subMid").val();
    posActivateLogVo.tid = $("#tid").val();
    posActivateLogVo.status = $("#status").val();
    posActivateLogVo.activateTimeBegin = $("#activateTimeBegin").val();
    posActivateLogVo.activateTimeEnd = $("#activateTimeEnd").val();
}

jQuery(document).ready(function () {
    setSearchParam();
    $("#activateTimeBegin").datetimepicker({
        language: 'zh-CN',
        timePicker: false,
        minView: "month",
        format: "yyyy-mm-dd",
        autoClose: true,
        clearBtn: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $("#activateTimeEnd").datetimepicker("setStartDate", $("#activateTimeBegin").val());
        $("#activateTimeBegin").datetimepicker("hide");
    });
    $("#activateTimeEnd").datetimepicker({
        language: 'zh-CN',
        timePicker: false,
        minView: "month",
        format: "yyyy-mm-dd",
        autoClose: true,
        clearBtn: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $("#activateTimeBegin").datetimepicker("setEndDate", $("#activateTimeEnd").val());
        $("#activateTimeEnd").datetimepicker("hide");
    });
    /**
     * 交易开始时间清空事件
     */
    $("#createTimeBeginClear").click(function () {
        $("#activateTimeBegin").val("");
    });
    /**
     * 交易结束时间清空事件
     */
    $("#createTimeEndClear").click(function () {
        $("#activateTimeEnd").val("");
    });
});

function clearFile() {
    $("#batchFile").val('');
}

// 文件修改时
$("#batchFile").change(function () {
    $("#batchImportBtn").val("上传");
    $("#progressBar").width("0%");
    var file = $(this).prop('files');
    if (file.length != 0) {
        $("#batchImportBtn").attr('disabled', false);
    }

});
var xhr = new XMLHttpRequest();

var response = "";

function callback() {
    //接收响应数据
    //判断对象状态是否交互完成，如果为4则交互完成
    if (xhr.readyState == 4) {
        //判断对象状态是否交互成功,如果成功则为200
        if (xhr.status == 200) {
            //接收数据,得到服务器输出的纯文本数据
            response = xhr.responseText;
        }
    }
}



/**
 * 日志查询导出excel。
 */
$("#export").click(function () {
    $("#export").attr('disabled', true);
    var el = $("#logBody");
    Shade.blockUI(el);
    $.ajax({
        type: "POST",
        data: posActivateLogVo,
        url: "/trans/pos-activate-log/export",
        dataType: "json",
        success: function (data) {
            Shade.unblockUI(el);
            $("#export").attr('disabled', false);
            if (data.code == 1) {
                var tempName = data.tempName;
                var fileName = data.fileName;
                var loadUrl = "/trans/pos-activate-log/down-excel-data?tempName=" + tempName + '&fileName=' + fileName;
                window.location.href = loadUrl;
            } else {
                BootboxExt.alert(data.msg, function () {
                });
            }
        },
        error: function () {
            BootboxExt.alert("网络异常，请联系维护人员", function () {
            });
        }
    });

})

/**
 * 下载模板
 */
function down() {
    var fileName = "pos-activate-log.xlsx";
    var loadUrl = "/trans/pos-activate-log/down?fileName=终端批量导入-模板.xlsx&realFileAddr=" + fileName;
    window.location.href = loadUrl;
}



