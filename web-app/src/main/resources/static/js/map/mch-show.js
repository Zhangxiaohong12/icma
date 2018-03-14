$(function () {
    init();
});
var init = function () {
    var mchInfo = {latitude: null, longitude: null};
    if (mchInfos === undefined || mchInfos === null || mchInfos.length === 0) {
        BootboxExt.alert("没有查询到已经标注地理位置的商户，请先操作商户管理进行地理位置标注");
    } else {
        mchInfo = mchInfos[0];//获取第一个为地图中心点
    }
    var latitude = mchInfo.latitude;//纬度
    var longitude = mchInfo.longitude;//经度
    var center = null;//地图中心点位置
    if (latitude === null || longitude === null) {
        center = new qq.maps.LatLng(23.179955, 113.420963);//蓝盾信息产业基本为默认中心点位置
    } else {
        center = new qq.maps.LatLng(latitude, longitude);//初始化中心点位置，经纬度不可以为null
    }
    //初始化地图
    var map = new qq.maps.Map(document.getElementById('container'), {
        center: center,
        zoom: 14
    });
    //浏览器客户端IP定位
    if (latitude === null || longitude === null) {
        //获取城市列表接口设置中心点
        var citylocation = new qq.maps.CityService({
            complete: function (result) {
                map.setCenter(result.detail.latLng);//定位
            }
        });
        //调用searchLocalCity();方法    根据用户IP查询城市信息。
        citylocation.searchLocalCity();
    }
    //实例化自动完成
    var ap = new qq.maps.place.Autocomplete(document.getElementById('place'));
    //调用Poi检索类。用于进行本地检索、周边检索等服务。
    var searchService = new qq.maps.SearchService({
        map: map
    });
    //添加监听事件
    qq.maps.event.addListener(ap, "confirm", function (res) {
        searchService.search(res.value);
    });
    for (var i = 0; i < mchInfos.length; i++) {
        mchInfo = mchInfos[i];
        //如果没有经纬度则无法标注
        if (mchInfo.latitude === null || mchInfo.longitude === null) {
            continue;
        }
        var locationPosition = new qq.maps.LatLng(mchInfo.latitude, mchInfo.longitude);
        //添加到提示窗
        var info = new qq.maps.InfoWindow({
            map: map
        });
        //提示框内容
        var infoText = '<div style="text-align:left;white-space:nowrap;' + 'margin:4px;">' + mchInfo.midShortName + '</div>';
        if (mchInfo.mchAddr !== null) {
            infoText += '<div style="text-align:left;white-space:nowrap;' + 'margin:4px;">地址:' + mchInfo.mchAddr + '</div>';
        }
        if (mchInfo.phone !== null) {
            infoText += '<div style="text-align:left;white-space:nowrap;' + 'margin:4px;">电话:' + mchInfo.phone + '</div>';
        }
        info.setContent(infoText);
        //创建标记
        var marker = new qq.maps.Marker({
            position: locationPosition,
            map: map,
            info: info
            // draggable: true
        });
        info.setPosition(marker);//提示框位置与marker一致
        info.open();//显示
        //点击图标出提示
        qq.maps.event.addListener(marker, 'click', function () {
            this.info.open()
        });
    }
};