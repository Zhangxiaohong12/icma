package org.hengsir.icma.manage.item.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hengsir.icma.dao.ItemDao;
import org.hengsir.icma.dao.ItemDetailDao;
import org.hengsir.icma.entity.Item;
import org.hengsir.icma.entity.ItemDetail;
import org.hengsir.icma.entity.ItemVo;
import org.hengsir.icma.service.ItemDetailService;
import org.hengsir.icma.service.ItemService;
import org.hengsir.icma.utils.ItemCenter;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controller：数据字典。
 *
 * @author lijiguang 2017年5月20日
 * @version 1.0.0
 */
@RequestMapping("/item")
@Controller
public class ItemController implements InitializingBean{
    //日志
    private static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDetailDao itemDetailDao;

    @Autowired
    private ItemDetailService itemDetailService;

    /**
     * 数字字典查询。
     *
     * @return 查询页面
     */
    @RequestMapping(value = "/search")
    @RequiresPermissions("item:search")
    public ModelAndView search(
            ItemVo itemDto, @RequestParam(value = "pageNum", defaultValue = "1") int index,
            @RequestParam(value = "pageSize", defaultValue = "10") int size) {
        Page<ItemVo> page = itemDao.findByPage(itemDto, new Page<>(index, size));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/system-data/item-list");
        modelAndView.addObject("list", page.getResult());
        modelAndView.addObject("pageHtml", PageHtmlUtil.initHtml(page));
        return modelAndView;
    }

    /**
     * 数字字典详细。
     *
     * @param id 主键
     * @return 详细页面
     */
    @RequestMapping(value = "/detail")
    @RequiresPermissions("itemDetail:search")
    public ModelAndView detail(@Param("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        ItemVo itemDto = itemDao.findById(id);
        modelAndView.setViewName("/system-data/item-detail-list");
        modelAndView.addObject("itemDTO", itemDto);
        return modelAndView;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ItemVo> items = itemDao.findByCategory(null);
        ItemCenter.putItem(items);//将字典放入内存中
        Map<String, List<Map<String, Object>>> jsonMap = new HashMap<>();
        for (ItemVo itemDto : items) {
            String gateGory = itemDto.getCategory();
            List<Map<String, Object>> jsonList = jsonMap.computeIfAbsent(
                gateGory, k -> new ArrayList<>());
            for (int j = 0; j < itemDto.getItemDetails().size(); j++) {
                Map<String, Object> map = new HashMap<>();
                ItemDetail itd = itemDto.getItemDetails().get(j);
                map.put("C", itd.getCode());
                map.put("S", itd.getSupperCode());
                map.put("N", itd.getName());
                map.put("Z", itd.getStatus());
                jsonList.add(map);
            }
        }
        if (jsonMap.size() > 0) {
            /*暂时这样获取路径，需要优化。*/
            String classLoaderPath = this.getClass().getResource("").getPath();
            String filePath =
                classLoaderPath.substring(0, classLoaderPath.indexOf("target"))+"src/main/resources/static/" + "select" +
                File.separatorChar + "item";
            for (Map.Entry<String, List<Map<String, Object>>> entry : jsonMap.entrySet()) {
                List<Map<String, Object>> list = entry.getValue();
                String data = JSON.toJSONString(list);
                try {
                    FileUtils.writeStringToFile(
                        new File(filePath + File.separatorChar + entry.getKey() + ".txt"), data,
                        "UTF-8");
                } catch (IOException exception) {
                    logger.error("{}", exception);
                }
            }
        }
    }

    /**
     * 数据字典增加页面。
     *
     * @return 需要的条件视图
     */
    @RequestMapping(value = "/toadd-item", method = {RequestMethod.GET})
    @RequiresPermissions("item:add")
    public ModelAndView toaddItem() {
        ModelAndView modelAndView = new ModelAndView();
        List<Item> list = itemDao.read();
        ItemVo itemDto = itemDao.findById(list.get(0).getId());
        modelAndView.setViewName("/system-data/item-add");
        modelAndView.addObject("itemDto", itemDto);
        return modelAndView;
    }

    /**
     * 新增数据字典。
     *
     * @param itemVo 数据字典扩展对象
     * @return json数据
     * @throws Exception 异常
     */
    @RequestMapping(value = "/add-item", method = {RequestMethod.POST})
    @ResponseBody
    public Object addItem(ItemVo itemVo) throws Exception {
        boolean flag = false;
        try {
            Item item = convertItem(itemVo.getCategory(), itemVo.getName(), itemVo.getDescription(),
                                    itemVo.getStatus(), itemVo.getOrderId());
            List<ItemDetail> list = convertItemDetailByJsonArray(
                itemVo.getCategory(), itemVo.getItemDetailStr());
            Item itemFlag = itemService.addItem(item);
            flag = addItemAndItemDetails(itemFlag, list);
        } catch (Exception ex) {
            logger.error("新增数据字典失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 验证类型是否存在。
     *
     * @param category 类型
     * @return json数据
     */
    @RequestMapping(value = "/valid-item-category", method = {RequestMethod.POST})
    @ResponseBody
    public Object validItemCategory(String category) {
        Boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        List<Item> list = itemDao.findItemNameExit(category);
        if (list.size() > 0) {
            flag = true;
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }


    /**
     * 数据字典修改的页面。
     *
     * @return 需要的条件视图
     */
    @RequestMapping(value = "/toupdate-item", method = {RequestMethod.GET})
    @RequiresPermissions("item:update")
    public ModelAndView toupdateItem(@Param("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        ItemVo itemDto = itemDao.findById(id);
        modelAndView.setViewName("/system-data/item-update");
        modelAndView.addObject("itemDTO", itemDto);
        modelAndView.addObject("list", itemDto.getItemDetails());
        return modelAndView;
    }

    /**
     * 更新数据字典。
     *
     * @param itemVo 数据字典详情的json数组
     * @return json数据
     */
    @RequestMapping(value = "/update-item", method = {RequestMethod.POST})
    @ResponseBody
    public Object updateItem(ItemVo itemVo) throws Exception {
        boolean flag = false;
        try {
            Item item = convertItem(itemVo.getCategory(), itemVo.getName(), itemVo.getDescription(),
                                    itemVo.getStatus(), itemVo.getOrderId());
            item.setId(itemVo.getId());
            List<ItemDetail> list = convertItemDetailByJsonArray(
                itemVo.getCategory(), itemVo.getItemDetailStr());
            boolean itemFlag = itemService.updateItem(item);
            Item itemExit = itemDao.findById(item.getId());
            if (itemFlag) {
                flag = this.addOrUpdate(itemExit, itemVo.getrCategory(), list);
            }
        } catch (Exception ex) {
            flag = false;
            logger.error("修改数据字典失败", ex);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 删除数据字典。
     *
     */
    @RequestMapping(value = "/delete-itemDetail", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("itemDetail:delete")
    public Object deleteItemDetail(@Param("id") int id) {
        JSONObject jsonObject = new JSONObject();
        boolean flag = itemDetailService.deleteItemDetailById(id);
        return jsonObject.accumulate("result", flag);
    }

    /**
     * 删除数据字典和详情。
     *
     * @return 返回json数据
     */
    @RequestMapping(value = "/todelete-item-itemDetail", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("item:delete")
    public Object todeleteItemAndItemDetail(@Param("id") int id) {
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        ItemVo itemVo = itemDao.findById(id);
        int size = itemVo.getItemDetails().size();
        if (size > 0) {
            flag = true;
        }
        return jsonObject.accumulate("result", flag);
    }

    /**
     * 删除数据字典和详情。
     *
     * @return json数据
     */
    @RequestMapping(value = "/delete-item-itemDetail", method = {RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions("item:delete")
    public Object deleteItemAndItemDetail(@Param("id") int id) {
        JSONObject jsonObject = new JSONObject();
        boolean flag = false;
        boolean itemFlag = false;
        ItemVo itemVo = itemDao.findById(id);
        int num = 0;
        int listSize = 0;
        if (null != itemVo) {
            List<ItemDetail> itemDetails = itemVo.getItemDetails();
            listSize = itemDetails.size();
            if (itemDetails.size() > 0) {
                for (ItemDetail detail : itemDetails) {
                    boolean detailFlag = itemDetailService.deleteItemDetailById(detail.getId());
                    if (detailFlag) {
                        num++;
                    }
                }
            }
            itemFlag = itemService.deleteItemById(itemVo.getId());
        }
        if (itemFlag == true && num == listSize) {
            flag = true;
        }
        return jsonObject.accumulate("result", flag);
    }

    /**
     * 数据字典详情增加页面。
     *
     * @return 需要的条件视图
     */
    @RequestMapping(value = "/toadd-itemDetail", method = {RequestMethod.GET})
    @RequiresPermissions("itemDetail:add")
    public ModelAndView toaddItemDetail(int id) {
        ModelAndView modelAndView = new ModelAndView();
        ItemVo itemDto = itemDao.findById(id);
        modelAndView.setViewName("/system-data/item-detail-add");
        modelAndView.addObject("itemDto", itemDto);
        return modelAndView;
    }

    /**
     * 增加数据字典详情。
     *
     * @param itemDetail 数据字典详情对象
     * @return 对象
     * @throws Exception json数据
     */
    @RequestMapping(value = "/add-item-detail", method = {RequestMethod.POST})
    @ResponseBody
    public Object addItemDetail(ItemDetail itemDetail) {
        JSONObject jsonObject = new JSONObject();
        ItemDetail detail = itemDetailService.addItemDetail(itemDetail);
        boolean flag = false;
        if (null != detail) {
            flag = true;
        }
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 数据字典详情修改页面。
     *
     * @return 需要的条件视图
     */
    @RequestMapping(value = "/toupdate-itemDetailSingle", method = {RequestMethod.GET})
    @RequiresPermissions("itemDetail:update")
    public ModelAndView toupdateItemDetailSingel(
            @Param("id") int id, @Param("itemId") int itemId) {
        ModelAndView modelAndView = new ModelAndView();
        ItemDetail itemDetail = itemDetailDao.findItemDetailById(id);
        modelAndView.setViewName("/system-data/item-detail-update");
        modelAndView.addObject("itemDetail", itemDetail);
        modelAndView.addObject("itemId", itemId);  //数据字典id,为了返回上级页面
        modelAndView.addObject("id", id); //数据字典详情id
        return modelAndView;
    }

    /**
     * 修改数据字典详情。
     *
     * @param itemDetail 数据字典详情对象
     * @return 对象
     * @throws Exception json数据
     */
    @RequestMapping(value = "/update-item-detailSingle", method = {RequestMethod.POST})
    @ResponseBody
    public Object updateItemDetailSingle(ItemDetail itemDetail) {
        JSONObject jsonObject = new JSONObject();
        Boolean flag = itemDetailService.updateItemDetailParam(itemDetail);
        jsonObject.accumulate("result", flag);
        return jsonObject;
    }

    /**
     * 数据字典详情的操作，包括增加和修改。
     *
     * @param itemFlag item是否增加或修改成功
     * @param list     数据字典详情集合
     * @return json数据
     */
    public boolean addItemAndItemDetails(Item itemFlag, List<ItemDetail> list) {
        boolean flag = false;
        int listSize = list.size();
        int addNum = 0;
        if (null != itemFlag) {
            if (list.size() > 0 && null != list) {
                for (ItemDetail detail : list) {
                    if (0 != detail.getId() && !"".equals(detail.getId())) {
                        if (0 == detail.getId()) {
                            ItemDetail itemDetail1 = itemDetailService.addItemDetail(detail);
                            if (null != itemDetail1) {
                                addNum++;
                            }
                        }
                    }
                }
            } else {
                flag = true;
            }
        }
        int optTotal = addNum;
        if (null != itemFlag && listSize == optTotal) {
            flag = true;
        }
        return flag;
    }

    /**
     * 数据字典详情的操作，包括增加和修改。
     *
     * @param itemFlag item是否增加或修改成功
     * @param list     数据字典详情集合
     * @return json数据
     */
    public boolean addOrUpdate(Item itemFlag, String reCategory, List<ItemDetail> list) {
        boolean flag = false;
        int listSize = list.size();
        int addNum = 0;
        if (null != itemFlag) {
            try {
                itemDetailService.deleteByCategory(reCategory);
                if (list.size() > 0 && null != list) {
                    String category = itemFlag.getCategory();
                    for (ItemDetail detail : list) {
                        detail.setCategory(category);
                        ItemDetail itemDetail1 = itemDetailService.addItemDetail(detail);
                        if (null != itemDetail1) {
                            addNum++;
                        }
                    }
                } else {
                    flag = true;
                }
            } catch (Exception ex) {
                logger.error("修改数据字典详情失败", ex);
                return false;
            }
        }
        if (null != itemFlag && listSize == addNum) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将页面传过来的数封装成一个Item对象。
     *
     * @param category    类型
     * @param name        名称
     * @param description 描述
     * @param status      状态
     * @param orderId     序号
     * @return 数据字典对象
     */
    public Item convertItem(
        String category, String name, String description, String status, int orderId) {
        Item item = new Item();
        item.setCategory(category);
        item.setName(name);
        item.setDescription(description);
        item.setStatus(status);
        item.setOrderId(orderId);
        return item;
    }

    /**
     * 将页面传的json数组解码，转换为ItemDetail集合。
     *
     * @param category    类型
     * @param itemDetails 数据字典json数组
     * @return 集合
     * @throws Exception 异常
     */
    public List<ItemDetail> convertItemDetailByJsonArray(String category, String itemDetails)
        throws Exception {
        List<ItemDetail> list = new ArrayList<>();
        String decodedItemDetail = URLDecoder.decode(itemDetails, "utf-8");
        JSONArray jsonArray = JSONArray.fromObject(decodedItemDetail);//解析json数组
        if (null != itemDetails && !"".equals(itemDetails)) {
            String content = JSONObject.fromObject(jsonArray.get(0)).get("code").toString();
            if (content.equals("没有数据")) {
                return list;
            } else {
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (null != jsonArray.get(i) && !"".equals(jsonArray.get(i))) {
                        JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                        String ids = jsonObject.get("otpEditId").toString();
                        int itemDetailId = Integer.valueOf(ids);
                        ItemDetail ds = new ItemDetail();
                        ds.setId(itemDetailId);
                        ds.setCode(jsonObject.get("code").toString());
                        ds.setSupperCode(jsonObject.get("supperCode").toString());
                        ds.setCategory(category);
                        ds.setDescription(jsonObject.get("description").toString());
                        ds.setName(jsonObject.get("name").toString());
                        ds.setOrderId(Integer.valueOf(jsonObject.get("orderId").toString()));
                        String status = "0";
                        String statusStr = jsonObject.get("status").toString();
                        if (statusStr.equals("有效")) {
                            status = "1";
                        }
                        ds.setStatus(status);
                        list.add(ds);
                    }
                }
            }
            //数组结束jsonArray
        }
        return list;
    }
}
