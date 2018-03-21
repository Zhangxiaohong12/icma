var schoolForm = $('#schoolForm');
schoolForm.validate({
    errorElement: 'span',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
        schoolCode: {
            blank: true,
            required: true,
            stringMaxLength: 50
        },
        schoolName: {
            required: true,
            chcharacter: true,
            stringMaxLength: 50
        }
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
        if (schoolForm.valid()) { //验证通过
            var schoolCode = $("#schoolCode").val().trim();
            if ("" != schoolCode && null != schoolCode) {
                $("#btnSave").attr("disabled", true);
                Shade.blockUI($("#schoolBody"));
                $.post("/sxc/school/validSchoolCode", {schoolCode: schoolCode},
                    function (data) {
                        $("#btnSave").attr("disabled", false);
                        Shade.unblockUI($("#schoolBody"));
                        if (data.result == true) {
                            BootboxExt.alert("学校代码已存在");
                            return;
                        } else if (data.result == false) {
                            //查询编码不存在！！;
                            $.post("/sxc/school/add",
                                $("#schoolForm").serialize(),
                                function (data) {
                                    if (data.result == true) {
                                        BootboxExt.alert("新增成功", function (res) {
                                            location.href = "/sxc/school/search";
                                        });
                                    } else {
                                        BootboxExt.alert("新增失败", function (res) {
                                            window.location.reload();
                                        });
                                    }
                                });
                        }
                    });
            } else {
                BootboxExt.alert("请输入学校代码");
                return;
            }

            addSysIdFlag = false;

        }
    });

    $("#btnUpdate").click(function () {
        if (schoolForm.valid()) { //验证通过
            var schoolCode = $("#schoolCode").val().trim();
            //alert("rCode="+rCode+"---code="+code);
            $("#btnUpdate").attr("disabled", true);
            Shade.blockUI($("#menuBody"));
            $.post("/sxc/school/update",
                $("#schoolForm").serialize(),
                function (data) {
                    $("#btnUpdate").attr("disabled", false);
                    Shade.unblockUI($("#menuBody"));
                    if (data.result == true) {
                        BootboxExt.alert("修改成功", function (res) {
                            location.href = "/sxc/school/search";
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




