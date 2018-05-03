function deletePerson(id) {
    BootboxExt.confirm("确认删除吗？", function (res) {
        if (res) {
            $.get("/youtu/deletePersonId", { personId: id}, function (data)
            {
                if(data.result == true){
                    BootboxExt.alert("删除成功", function (res) {
                        location.href = "/youtu/searchPersons";
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