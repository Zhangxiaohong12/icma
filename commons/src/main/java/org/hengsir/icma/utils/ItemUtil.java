package org.hengsir.icma.utils;


/**
 * 将数据字典code转为name工具类。
 *
 * @author hengsir 2017-5-22
 * @version 1.0.1
 */
public class ItemUtil {
    /**
     * 获取数据字段name值。
     *
     * @param value    code
     * @param category 类型
     * @return name
     */
    public String getName(String value, String category) {
        return ItemCenter.getName(value, category);
    }
}
