$(function () {
    init();
});
var init = function () {
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
    var initMarker = null;
    //显示商户位置
    if (latitude !== null && longitude !== null) {
        initMarker = new qq.maps.Marker({
            map: map,
            position: center,//位置
            // animation: qq.maps.MarkerAnimation.DROP,//图标从天而降
            draggable: true//可以拖动
        });
        info.open();
        info.setPosition(initMarker);
        //点击图标出提示
        qq.maps.event.addListener(initMarker, 'click', function (event) {
            info.open();
        });
        //当用户停止拖动标注时会触发此事件
        qq.maps.event.addListener(initMarker, 'dragend', function (event) {
            //保存经纬度
            var lat = event.latLng.getLat().toFixed(6);
            var lng = event.latLng.getLng().toFixed(6);
            saveLatLng(mchInfo.mid, lng, lat);
        });
    }
    //地图点击事件
    qq.maps.event.addListener(map, 'click', function (event) {
        //标志物清楚后续创建新标志物
        if (initMarker !== null) {
            initMarker.setMap(null);
        }
        var lat = event.latLng.getLat().toFixed(6);//纬度
        var lng = event.latLng.getLng().toFixed(6);//经度
        saveLatLng(mchInfo.mid, lng, lat);//保存经纬度到数据库
        //定义标志物
        var marker = new qq.maps.Marker({
            map: map,
            position: event.latLng,//位置
            animation: qq.maps.MarkerAnimation.DROP,//图标从天而降
            draggable: true//可以拖动

        });
        info.open();
        info.setPosition(marker);
        qq.maps.event.addListener(map, 'click', function (event) {
            marker.setMap(null);
        });
        //点击图标出提示
        qq.maps.event.addListener(marker, 'click', function (event) {
            info.open();
        });
        //当用户停止拖动标注时会触发此事件
        qq.maps.event.addListener(marker, 'dragend', function (event) {
            //保存经纬度
            var lat = event.latLng.getLat().toFixed(6);
            var lng = event.latLng.getLng().toFixed(6);
            saveLatLng(mchInfo.mid, lng, lat);
        });
    });
};

//商户标注--保存经纬度
function saveLatLng(mid, lng, lat) {
    // alert("商户号：" + mid + "，经度：" + lng + "，纬度：" + lat);
    $.post("/mch/info/latLng", {mid: mid, lng: lng, lat: lat}, function (data) {
        if (data.result === true) {
            BootboxExt.alert("标注成功", function (res) {
            });
        } else {
            BootboxExt.alert("标注失败", function (res) {
            });
        }
    }, "json");
}