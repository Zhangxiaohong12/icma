


/**
 * 返回按钮。
 */
function returnSearch() {
    Shade.blockUI($("#tidBoby"));
    window.location.href = "/rights/user/search";
}




/**
 * 返回按钮。
 */
function returnSearch() {
    Shade.blockUI($("#tidBoby"));
    window.location.href = "/rights/user/search";
}

function clearMerchant(){
    var sysId = $("#sysId").val();
    //如果sysId等于1，清空商户，禁用商户选择
    if(sysId==1){
        $("#merchantId").val("");
        $("#merchantName").val("");
        $('#merchantName').valid();

        $("#subMid").val("");
        $("#subMidName").val("");
        $('#subMidName').valid();
    }

    if ( 1 == sysId) {
        $('#mchDiv').hide();
    } else {
        $('#mchDiv').show();
    }



}