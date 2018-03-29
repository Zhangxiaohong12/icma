package org.hengsir.icma.handler.constant;

/**
 * 类型枚举。
 *
 * @author zhangwenjun
 */
public enum TypeEnum {

    SCHOOL("10", "获取学校"), XIBIE("20", "获取系别"), CLASS("30", "获取班级"), UPLOAD(
        "40", "上传图片");

    private String code;
    private String des;

    TypeEnum(String code, String des) {
        this.code = code;
        this.des = des;
    }

    public String getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
