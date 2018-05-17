function view(id) {
    Shade.blockUI($('body'));
    window.location.href = "/youtu/hist/histView?id=" + id;
}

jQuery(document).ready(function () {

    $("#createTimeBegin").datetimepicker({
        language: 'zh-CN',
        timePicker: false,
        minView: "month",
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
        minView: "month",
        format: "yyyy-mm-dd",
        autoClose: true,
        clearBtn: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $("#createTimeBegin").datetimepicker("setEndDate", $("#createTimeEnd").val());
        $("#createTimeEnd").datetimepicker("hide");
    });

    $("#createTimeBeginClear").click(function () {
        $("#createTimeBegin").val("");
    });

    $("#createTimeEndClear").click(function () {
        $("#createTimeEnd").val("");
    });
});

