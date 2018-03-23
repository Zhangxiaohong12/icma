var pwdForm = $('#pwdForm');
pwdForm.validate({
    errorElement: 'span',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
        oldPwd: {
            required: true,
            alnum: true,
            stringMaxLength: 64
        },
        newPwd: {
            required: true,
            alnum: true,
            stringMaxLength: 64
        },
        newPwd1: {
            required: true,
            alnum: true,
            stringMaxLength: 64
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
    $("#btnUpdate").click(function () {

        if (pwdForm.valid()) { //验证通过
            var newPwd = $("#newPwd").val();
            var newPwd1 = $("#newPwd1").val();
            if(newPwd != newPwd1){
                BootboxExt.alert("新密码与确认不一样");
                return;
            }
            $("#btnUpdate").attr("disabled", true);
            Shade.blockUI($("#pwdBody"));
            $.post("/user/update-pwd",
                $("#pwdForm").serialize(),
                function (data) {
                    $("#btnUpdate").attr("disabled", false);
                    Shade.unblockUI($("#pwdBody"));
                    if (data.result == "success") {
                        BootboxExt.alert("修改成功", function (res) {
                            location.href = "/user/search";
                        });
                    } else if (data.result == "notSame") {
                        BootboxExt.alert("原来密码不正确", function (res) {
                            $("#oldPwd").val('');
                            $("#oldPwd").focus();
                        });
                    } else {
                        BootboxExt.alert("修改失败", function (res) {
                            window.location.reload();
                        });
                    }
                })
        }
    });
});