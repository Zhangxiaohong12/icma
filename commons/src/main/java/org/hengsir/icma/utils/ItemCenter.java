package org.hengsir.icma.utils;


import org.hengsir.icma.entity.ItemDetail;
import org.hengsir.icma.entity.ItemVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典code转为name内存。
 *
 * @author hengsir 2017-5-22
 * @version 1.0.1
 */
public class ItemCenter {

    /**
     * item表内存。
     */
    private static Map<String, ItemVo> itemMap = new HashMap<>();

    /**
     * 地区表内存。
     */
    private static Map<String, Map<String, String>> areaMap = new HashMap<>();

    /**
     * 获取数据字段name值。
     *
     * @param value    code
     * @param category 类型
     * @return name
     */
    public static String getName(String value, String category) {
        String reName = null;
        if (category != null) {
            if ("AREAP".equals(category) || "AREAC".equals(category)) {
                reName = getAreaName(value, category);
            } else {
                reName = getItemName(value, category);
            }
        }
        return reName;
    }

    /**
     * 将code转为name。
     *
     * @param value    数据字段code
     * @param category 数据字段类型
     * @return 数据字段name，转换不了时返回null
     */
    public static String getAreaName(String value, String category) {
        String reName = value;
        if (value != null && !"".equals(value) && category != null && !"".equals(category)) {
            if (areaMap.containsKey(category)) {
                Map<String, String> aream = areaMap.get(category);
                if (aream != null) {
                    reName = aream.get(value);
                }
            }
        }
        return reName;
    }

    /**
     * 获取数据字段name值。
     *
     * @param value    name值
     * @param category code值
     * @return name值
     */
    public static String getItemName(String value, String category) {
        String reName = value;
        if (value != null && !"".equals(value) && category != null && !"".equals(category)) {
            if (itemMap.containsKey(category)) {
                ItemVo itemDTO = itemMap.get(category);
                if (itemDTO != null) {
                    List<ItemDetail> itemDetails = itemDTO.getItemDetails();
                    if (itemDetails != null && itemDetails.size() > 0) {
                        for (ItemDetail bean : itemDetails) {
                            if (value.equals(bean.getCode())) {
                                reName = bean.getName();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return reName;
    }

    /**
     * 更新数据字典内存对象。
     *
     * @param items 集合
     */
    public static void putItem(List<ItemVo> items) {
        if (items != null && items.size() > 0) {
            for (ItemVo item : items) {
                itemMap.put(item.getCategory(), item);
            }
        }
    }

    /**
     * 更新数据字典内存对象。
     *
     * @param item 集合
     */
    public static void putItem(ItemVo item) {
        if (item != null) {
            itemMap.put(item.getCategory(), item);
        }
    }

    /**
     * 移除数据字典内存对象。
     *
     * @param items 集合
     */
    public static void removeItem(List<ItemVo> items) {
        if (items != null && items.size() > 0) {
            for (ItemVo item : items) {
                itemMap.remove(item.getCategory(), item);
            }
        }
    }

    /**
     * 移除数据字典内存对象。
     *
     * @param item 集合
     */
    public static void removeItem(ItemVo item) {
        if (item != null) {
            itemMap.remove(item.getCategory(), item);
        }
    }

}
