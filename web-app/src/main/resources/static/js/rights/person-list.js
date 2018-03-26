function deletePerson(personId) {
    BootboxExt.confirm("确认删除吗？", function (res) {
        if (res) {
            $.get("/person/delete-person", {personId: personId}, function (data) {
                if (data.result == true) {
                    BootboxExt.alert("删除成功", function (res) {
                        location.href = "/person/search";
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

function view(personId){
    Shade.blockUI($('body'));
    window.location.href = "/person/view?=" + personId;
}






