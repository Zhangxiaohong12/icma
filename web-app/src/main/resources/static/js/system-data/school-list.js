function deleteSchool(id) {
    BootboxExt.confirm("确认删除吗？", function (res) {
        if (res) {
            $.get("/sxc/school/delete", { id: id}, function (data)
            {
                if(data.result == true){
                    BootboxExt.alert("删除成功", function (res) {
                        location.href = "/sxc/school/search";
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