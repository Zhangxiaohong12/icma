package org.hengsir.icma.jw.bean;

/**
 * @author hengsir
 * @date 2018/5/9 下午2:28
 */
public class LoginUser {
    private String userName;
    private String password;
    private String code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
