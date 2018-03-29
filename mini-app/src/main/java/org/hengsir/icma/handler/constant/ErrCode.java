package org.hengsir.icma.handler.constant;

/**
 * 响应码定义。
 *
 */
public enum ErrCode {
    ERRC_SUCCESS("00", "交易受理"),
    ERRC_FAIL("51", "请求失败"),
    ERRC_SYSTEM_ERROR("96", "系统故障"),
    ERRC_FORMAT_ERROR("30", "格式错误"),
    ERRC_MISSING_ERROR("31", "参数缺失");

    private String code;
    private String description;

    ErrCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

}
