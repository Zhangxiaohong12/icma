var addForm = $('#addFrom');
addForm.validate({
    errorElement: 'span',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
        personId: {
            required: true,
        },
        photo: {
            required: true,
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

function toAddImg(personId) {
    location.href = "/person/to-add-img?personId=" + personId;
}

function deleteImg(faceId, personId) {
    BootboxExt.confirm("确认删除吗？", function (res) {
        if (res) {
            $.get("/person/delete-img", {faceId: faceId, personId: personId}, function (data) {
                if (data.result == true) {
                    BootboxExt.alert("删除成功", function (res) {
                        //location.href = "/person/view?personId="+personId;
                        window.location.reload();
                    });
                } else {
                    BootboxExt.alert("删除失败", function (res) {
                        window.location.reload();
                    });
                }
            }, "json");
        }
    });
}

function checkFile(filename){
    var flag = false; //状态
    var arr = ["jpg","png","jpeg"];
    //取出上传文件的扩展名
    var index = filename.lastIndexOf(".");
    var ext = filename.substr(index+1).toLowerCase();
    //循环比较
    for(var i=0;i<arr.length;i++)
    {
        if(ext == arr[i])
        {
            flag = true; //一旦找到合适的，立即退出循环
            break;
        }
    }
    return flag;
}

$(function () {
    $("#btnSave").click(function () {
        var photo = $("#photo")[0].files[0];
        if (null == photo || '' == photo) {
            BootboxExt.alert("请先选择文件");
            return;
        }
        if (!checkFile($("#photo").val())){
            BootboxExt.alert("文件不合法");
            return;
        }
        var personId = $("#personId").val();
        if (null == personId || '' == personId) {
            BootboxExt.alert("personId不能为空");
            return;
        }

        var fd = new FormData();
        fd.append('photo', photo);
        fd.append('personId', personId);
        $("#btnSave").attr("disabled", true);
        Shade.blockUI($("#personBody"));
        $.ajax({
            url: "/person/sensitize",
            type: 'POST',
            data: fd,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#btnSave").attr("disabled", false);
                Shade.unblockUI($("#personBody"));
                if (data.result == "success") {
                    BootboxExt.alert("激活成功", function (res) {
                        location.href = "/person/search";
                    });
                } else if (data.result == "toMax"){
                    BootboxExt.alert("图片过大", function (res) {
                        $("#photo").val('');
                    });
                } else {
                    BootboxExt.alert("激活失败", function (res) {
                        window.location.reload();
                    });
                }
            }
        });

    });


});








