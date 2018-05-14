package org.hengsir.icma.jw.handle;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.jw.bean.CourseTable;
import org.hengsir.icma.jw.bean.LoginUser;

/**
 * @author hengsir
 * @date 2018/5/9 下午2:33
 */
public interface HttpInterface {
    /**
     * 初始化，主要用于收集cookie和viewState
     */
    public void init();

    /**
     * 根据指定url发送给请求
     * @param url 请求url
     * @param ref 引用
     * @return 响应页面的HTML文档
     */
    public StringBuffer sendGetRequest(String url,String ref);

    /**
     * 根据指定url和参数值发送post请求
     * @param url 请求url
     * @param ref 引用
     * @param entity 参数列表
     * @return 响应页面的HTML文档
     */
    public StringBuffer sendPostRequest(String url,String ref, UrlEncodedFormEntity entity);

    /**
     * 获取验证码
     * @return 验证码图片
     */
    public byte[] getCheckImg();

    /**
     * 登陆
     * @param user 用户信息
     * @return 返回登陆是否成功
     */
    public boolean login(LoginUser user);

    /**
     * 根据学年度和学期获取课表
     * @param xnd 学年度
     * @param xqd 学期
     * @return 课表
     */
    public CourseTable getCourseTable(String xnd, String xqd);

    /**
     * 根据学年度和学期获取课表的Json串
     * @param xnd 学年度
     * @param xqd 学期
     * @return 课表Json串
     */
    public String getCourseTableAsJson(String xnd,String xqd);

    /**
     * 获取个人信息
     * @return 个人信息
     */
    public User getPersonalInfo();

    /**
     * 获取错误信息
     * @return 返回错误信息
     */
    public String getErrorMessege();
}
