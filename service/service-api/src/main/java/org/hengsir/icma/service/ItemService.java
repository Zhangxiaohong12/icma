package org.hengsir.icma.service;


import org.hengsir.icma.entity.Item;

/**
 * 数据字典。
 *
 * @author hengsir 2017年5月20日
 */
public interface ItemService {
    /**
     * 新增数据字典数据。
     *
     * @param item 数据字典参数
     */
    Item addItem(Item item);

    /**
     * 修改数据字典。
     *
     * @param item 数据字典参数
     */
    Boolean updateItem(Item item);

    /**
     * 删除数据字典。
     *
     * @param id 数据字典id
     * @return true：删除成功，false：删除失败
     */
    Boolean deleteItemById(int id);
}