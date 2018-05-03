package org.hengsir.icma.manage.youtu.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.ClassVo;
import org.hengsir.icma.service.YoutuService;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/5/2 下午2:24
 */
@Controller
@RequestMapping("/youtu")
public class YoutuController {

    @Autowired
    private SXCDao sxcDao;

    @Autowired
    private YoutuService youtuService;

    /**
     * 查询优图中组的个体列表，用于数据库出现不同步
     * @param groupId
     * @param index
     * @param size
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     * @throws KeyManagementException
     */
    @RequestMapping("/searchPersons")
    @RequiresPermissions("person_list:search")
    public ModelAndView searchPersons(String groupId,@RequestParam(value = "pageNum", defaultValue = "1") int index,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int size) throws IOException, NoSuchAlgorithmException, JSONException, KeyManagementException {
        ModelAndView model = new ModelAndView();
        model.setViewName("/youtu/person");
        List<String> page = youtuService.getPersons(new Page<>(index,size),groupId);
        model.addObject("list", page);
        model.addObject("groupId", groupId);
        return model;

    }

    /**
     * 删除优图中多余的个体
     * @param personId
     * @return
     */
    @RequiresPermissions("person_list:delete")
    @RequestMapping("/deletePersonId")
    @ResponseBody
    public Object deletePersonId(String personId){
        JSONObject jsonObject = new JSONObject();
        boolean result = youtuService.deletePersonId(personId);
        jsonObject.accumulate("result",result);
        return jsonObject;

    }

    @RequestMapping("/get-groupIds")
    @RequiresPermissions("get_groupIds:search")
    public ModelAndView searchGroupIds(@RequestParam(value = "pageNum", defaultValue = "1") int index,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int size) throws IOException, NoSuchAlgorithmException, JSONException, KeyManagementException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/youtu/groupIds");
        Page<Class> page = youtuService.getGroupIds(new Page<>(index,size));
        modelAndView.addObject("list", page.getResult());
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        return modelAndView;
    }
}
