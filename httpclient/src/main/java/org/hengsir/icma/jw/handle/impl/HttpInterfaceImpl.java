package org.hengsir.icma.jw.handle.impl;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.jw.bean.Course;
import org.hengsir.icma.jw.bean.CourseTable;
import org.hengsir.icma.jw.bean.LoginUser;
import org.hengsir.icma.jw.constant.Constant;
import org.hengsir.icma.jw.handle.HttpInterface;
import org.hengsir.icma.jw.tool.HtmlTools;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hengsir
 * @date 2018/5/9 下午2:35
 */
@Service
public class HttpInterfaceImpl implements HttpInterface {
    /**
     * Http客户端
     */
    private CloseableHttpClient mHttpClient;

    /**
     * 记录cookie
     */
    private String mCookie;

    /**
     * 记录正方教务系统页面表单的__VIEWSTATE的值
     */
    private String mViewState;

    /**
     * 已登陆用户的信息
     */
    private LoginUser mUser;

    /**
     * 登陆错误信息
     */
    private String mErrorMessege;

    /**
     * 查询课程表信息的URL
     */
    private String mCourseURL;

    /**
     * 查询个人信息的URL
     */
    private String mPersonalInfoURL;

    /**
     * 查询成绩表的URL
     */
    private String mScorceURL;

    /**
     * 构造函数
     */
    public HttpInterfaceImpl() {
        this.mErrorMessege = "no error";
        mHttpClient = HttpClients.createDefault();
        init();
    }

    @Override
    public void init() {
        String url = Constant.BASE_URL;
        try {

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = mHttpClient.execute(httpGet);//提交请求获得响应
            mCookie = response.getFirstHeader("Set-Cookie").getValue();    //获取cookie
            StringBuffer sb = sendGetRequest(url, null);    //发送访问请求并获得响应页面
            mViewState = HtmlTools.findViewState(sb.toString()); //提取页面表单中的__VIEWSTATE的值

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public StringBuffer sendGetRequest(String url, String ref) {

        StringBuffer sb = new StringBuffer();
        InputStream in = null;

        try {

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Cookie", mCookie);//设置cookie
            if (ref != null && !ref.equals("")) {
                httpGet.setHeader("Referer", ref);//如果有地址引用则设置
            }
            CloseableHttpResponse response = mHttpClient.execute(httpGet);//提交请求获得响应
            in = response.getEntity().getContent();

            //获取响应内容
            if (in != null) {
                int len = -1;
                byte[] data = new byte[1024];
                while ((len = in.read(data)) != -1) {
                    String s = new String(data, 0, len, Constant.ENCODING);
                    sb.append(s);
                }
            }


        } catch (Exception e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }

        return sb;
    }

    @Override
    public StringBuffer sendPostRequest(String url, String ref, UrlEncodedFormEntity entity) {
        StringBuffer sb = new StringBuffer();
        HttpPost httpPost = new HttpPost(url);
        InputStream in = null;
        try {
            httpPost.setHeader("Cookie", mCookie);    //设置cookie
            if (ref != null && !ref.equals("")) {
                httpPost.setHeader("Referer", ref);//如果有地址引用则设置
            }
            httpPost.setEntity(entity);//设置请求参数
            CloseableHttpResponse response = mHttpClient.execute(httpPost);//提交请求
            in = response.getEntity().getContent();//获得响应流对象

            //获取响应内容
            int len = -1;
            byte[] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                String s = new String(data, 0, len, Constant.ENCODING);
                sb.append(s);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
        return sb;
    }

    @Override
    public byte[] getCheckImg() {

        String url = Constant.CHECK_IMAGE_URL;
        HttpGet httpGet = new HttpGet(url);
        byte[] imgByte = null;

        try {

            CloseableHttpResponse response = mHttpClient.execute(httpGet);
            imgByte = EntityUtils.toByteArray(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgByte;
    }

    @Override
    public boolean login(LoginUser user) {

        //组织登陆请求参数
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("__VIEWSTATE", mViewState));//__VIEWSTATE，不可缺少这个参数
        params.add(new BasicNameValuePair("txtUserName", user.getUserName()));//学号
        params.add(new BasicNameValuePair("TextBox2", user.getPassword()));//密码
        params.add(new BasicNameValuePair("txtSecretCode", user.getCode()));//验证码
        params.add(new BasicNameValuePair("RadioButtonList1", Constant.RADIO_BUTTON_LIST));//登陆用户类型
        params.add(new BasicNameValuePair("Button1", ""));
        params.add(new BasicNameValuePair("lbLanguage", ""));
        params.add(new BasicNameValuePair("hidPdrs", ""));
        params.add(new BasicNameValuePair("hidsc", ""));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Constant.ENCODING);    //封装成参数对象
            StringBuffer sb = sendPostRequest(Constant.LOGIN_URL, null, entity);//发送请求
            mUser = user;//记录登陆的用户
            String html = sb.toString();

            //检测是否有登陆错误的信息，有则记录信息，否则登陆成功
            if (html.contains(Constant.CHECK_ERROR)) {
                mErrorMessege = Constant.CHECK_ERROR;
            } else if (html.contains(Constant.CHECK_NULL_ERROR)) {
                mErrorMessege = Constant.CHECK_NULL_ERROR;
            } else if (html.contains(Constant.PASSWD_ERROR)) {
                mErrorMessege = Constant.PASSWD_ERROR;
            } else {
                //登陆成功，重定向获取主页面
                StringBuffer userHtml = sendGetRequest(Constant.STUDENT_URL + user.getUserName(), null);
                saveQueryURL(userHtml.toString());    //根据响应内容找到并保存查询各种信息的URL
                return true;//返回登陆成功
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CourseTable getCourseTable(String xnd, String xqd) {

        return null;
    }

    @Override
    public String getCourseTableAsJson(String xnd, String xqd) {

        CourseTable courseTable = getCourseTable(xnd, xqd);
        StringBuffer sb = new StringBuffer();

        sb.append("{");
        for (Course c : courseTable.getCourses()) {
            sb.append("\n\t{");
            sb.append("\n\t\t\"name\":\"" + c.getName() + "\"");
            sb.append("\n\t\t\"classRoom\":\"" + c.getClassRoom() + "\"");
            sb.append("\n\t\t\"teacher\":\"" + c.getTeacher() + "\"");
            sb.append("\n\t\t\"classTime\":\"" + c.getClassTime() + "\"");
            sb.append("\n\t\t\"weekNum\":\"" + c.getWeekNum() + "\"");
            sb.append("\n\t\t\"startWeek\":\"" + c.getStartWeek() + "\"");
            sb.append("\n\t\t\"endWeek\":\"" + c.getEndWeek() + "\"");
            sb.append("\n\t\t\"weekState\":\"" + c.getWeekState() + "\"");
            sb.append("\n\t\t\"number\":\"" + c.getNumber() + "\"");
            sb.append("\n\t\t\"day\":\"" + c.getDay() + "\"");
            sb.append("\n\t}\n");
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    public User getPersonalInfo() {
        String url = Constant.BASE_URL + mPersonalInfoURL;//查询个人信息的URL
        String referer = Constant.STUDENT_URL + mUser.getUserName();//引用地址
        StringBuffer personalHtml = sendGetRequest(url,referer);
        //System.out.println(personalHtml);
        return null;
    }


    @Override
    public String getErrorMessege() {
        return mErrorMessege;
    }

    /**
     * 查找并保存查询各种信息的URL
     *
     * @param html HTML文档
     */
    private void saveQueryURL(String html) {
        String pattern = "<a href=\"(\\w+)\\.aspx\\?xh=(\\d+)&xm=(.+?)&gnmkdm=N(\\d+)\" target='zhuti' onclick=\"GetMc\\('(.+?)'\\);\">(.+?)</a>";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(html);

        while (m.find()) {
            String res = m.group();
            String url = res.substring(res.indexOf("href=\"") + 6);
            url = url.substring(0, url.indexOf("\""));
            url = "/" + url;

            if (res.contains("学生个人课表")) {
                mCourseURL = url;
                continue;
            }
            if (res.contains("成绩查询")) {
                mScorceURL = url;
                continue;
            }
            if (res.contains("个人信息")) {
                mPersonalInfoURL = url;
            }
        }

    }


    public LoginUser getmUser() {
        return mUser;
    }
}
