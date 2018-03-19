function changeIcon(icon){
    document.getElementById("icons").innerHTML=icon;
    document.getElementById("icon").value=icon;
}

var Obj = {
    appendHtml : function() {
        $.getJSON("/jsondata/icon.json", function(data) {
            var html = '<li role="presentation">';
            $.each(data, function(iconIndex, icon) {
                var iconInfo = {};
                iconInfo.index = iconIndex;
                iconInfo.icon = icon["icon"];
                html += Obj.buildHtml(iconInfo);
            })
            html += '</li>';
            $('.dropdown #menus').append(html);
        });
    },
    buildHtml : function(iconInfo) {
        var index = iconInfo.index;
        var icon = iconInfo.icon;
        var html = '';
        if (index == 0) {
            html += '<span><a style="color:black;cursor:pointer;" title="'+icon+'" onclick="changeIcon(\''+ icon +'\')">';
            html+='<i class="'+ icon + '" style="margin: 3px;font-size:20px;"></i></a></span>';
        } else if (index % 20 == 0) {
            html += '</li><li role="presentation">';
            html += '<span><a style="color:black;cursor:pointer;" title="'+icon+'" onclick="changeIcon(\''+ icon +'\')">';
            html+='<i class="' + icon + '" style="margin: 3px;font-size:20px;"></i></a></span>';
        } else {
            html += '<span><a style="color:black;cursor:pointer;" title="'+icon+'" onclick="changeIcon(\''+ icon +'\')">';
            html+='<i class="' + icon + '" style="margin: 3px;font-size:20px;"></i></a></span>';
        }
        return html;
    }
}

function changeIcon(icon){
    document.getElementById("icons").innerHTML=icon;
    document.getElementById("menuIcon").value=icon;
}

function changeMenu(menu){
    var superCode=document.getElementById("superCode1").value;
    //比较选中的值和它本身的code值看是否相等
    if(superCode == menu){
        BootboxExt.alert("请误选择自己做菜单 默认为不修改");
        document.getElementById("menuSuperCode").value=document.getElementById("superCode2").value;
    }else{
        document.getElementById("menuSuperCode").value=menu;
    }
}

//在添加页面，修改父菜单时，判断权限是否可选
var addAppIdNum = 1;
var addSysIdFlag = false;
function findAddSysAPPId(){
    addAppIdNum = 1;
    var code = $("#menuSuperCode").val();

}


$(function() {
    //初始化显示图标的方法
    Obj.appendHtml();
    $("#searchSelect").select2({
        formatNoMatches: function(term) {
            return "未找到数据";
        }
    });
});

var leftMenuForm = $('#leftMenuForm');
leftMenuForm.validate({
    errorElement: 'span',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
        name: {
            blank:true,
            required: true,
            stringMaxLength:50
        },
        code: {

            required: true,
            newCharacterNo2:true,
            stringMaxLength:50
        },
        sort: {
            required: true,
            number: true,
            stringMaxLength:22
        },
        href: {
            stringMaxLength:200
        },
        sysId1: {
            required: true
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
        if (leftMenuForm.valid()) { //验证通过

            var code = $("#code").val().trim();
            if("" != code && null !=code){
                $("#btnSave").attr("disabled",true);
                Shade.blockUI($("#menuBody"));
                $.post("/menu/valid-left-menu-Code",{code: code},
                    function(data){
                        $("#btnSave").attr("disabled",false);
                        Shade.unblockUI($("#menuBody"));
                        if(data.result == true){
                            BootboxExt.alert("编码已存在");
                            return;
                        }else if(data.result == false){
                            //查询编码不存在！！;
                            $.post("/menu/add-left-menu",
                                $("#leftMenuForm").serialize(),
                                function(data){
                                    if(data.result == true){
                                        BootboxExt.alert("新增成功",function(res){
                                            location.href="/menu/search";
                                        });
                                    }else{
                                        BootboxExt.alert("新增失败",function(res) {
                                            window.location.reload();
                                        });
                                    }
                                });
                        }
                    });
            }else{
                BootboxExt.alert("请输入编码");
                return;
            }

            addSysIdFlag = false;

        }
    });

    $("#btnUpdate").click(function () {
        if (leftMenuForm.valid()) { //验证通过

            var rCode = $("#rCode").val();
            var code = $("#uCode").val();
            if("" != code && null !=code){
                //alert("rCode="+rCode+"---code="+code);
                if(rCode != code){
                    $("#btnUpdate").attr("disabled",true);
                    Shade.blockUI($("#menuBody"));
                    $.post("/menu/valid-left-menu-Code",{code: code},
                        function(data){
                            $("#btnUpdate").attr("disabled",false);
                            Shade.unblockUI($("#menuBody"));
                            if(data.result == true){
                                BootboxExt.alert("编码已存在");
                                return;
                            }else if(data.result == false){
                                $("#btnUpdate").attr("disabled",true);
                                Shade.blockUI($("#menuBody"));
                                $.post("/menu/update-left-menu",
                                    $("#leftMenuForm").serialize(),
                                    function(data){
                                        $("#btnUpdate").attr("disabled",false);
                                        Shade.unblockUI($("#menuBody"));
                                        if(data.result == true){
                                            BootboxExt.alert("修改成功",function(res){
                                                location.href="/menu/search";
                                            });
                                        }else{
                                            BootboxExt.alert("修改失败",function(res){
                                                window.location.reload();
                                            });
                                        }
                                    });

                            }else{
                                //codeExitFlag = false;
                                //alert("异常");
                            }
                        });
                }else if (rCode == code){
                    $("#btnUpdate").attr("disabled",true);
                    Shade.blockUI($("#menuBody"));
                    $.post("/menu/update-left-menu",
                        $("#leftMenuForm").serialize(),
                        function(data){
                            $("#btnUpdate").attr("disabled",false);
                            Shade.unblockUI($("#menuBody"));
                            if(data.result == true){
                                BootboxExt.alert("修改成功",function(res){
                                    location.href="/menu/search";
                                });
                            }else{
                                BootboxExt.alert("修改失败",function(res){
                                    window.location.reload();
                                });
                            }
                        });
                }else{
                    //异常情况
                }
            }else{
                BootboxExt.alert("请输入编码");
            }
        }
    });
});




