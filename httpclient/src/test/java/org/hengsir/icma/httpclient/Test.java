package org.hengsir.icma.httpclient;

import org.apache.shiro.codec.Base64;
import org.hengsir.icma.jw.bean.LoginUser;
import org.hengsir.icma.jw.handle.HttpInterface;
import org.hengsir.icma.jw.handle.impl.HttpInterfaceImpl;


/**
 * @author hengsir
 * @date 2018/5/9 下午2:53
 */
public class Test {
    public static void main(String[] args) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName("1513454");
        loginUser.setPassword("440421199606088032");
        HttpInterface http = new HttpInterfaceImpl();
        byte[] b = http.getCheckImg();
        String check = new String(b);
        loginUser.setCode(check);

        if (http.login(loginUser)) {
            http.getPersonalInfo();
        }

    }
}
