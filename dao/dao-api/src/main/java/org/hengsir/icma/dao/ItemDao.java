package org.hengsir.icma.dao;


import org.hengsir.icma.entity.Item;
import org.hengsir.icma.entity.ItemVo;
import org.hengsir.icma.utils.pageHelper.Page;

import java.util.List;

/**
 * 数字字典只读接口。
 *
 * @author hengsir 2017年5月20日
 */
public interface ItemDao {

    /**
     * 获取单表所有数据。
     *
     * @return 单表数据集合
     */
    List<Item> read();

    /**
     * 根据id获取单表数据。
     *
     * @param id id号
     * @return 单表数据集合
     */
    Item read(int id);

    /**
     * 根据条件获取混合数据。
     *
     * @param itemVo 条件
     * @return 混合数据集合
     */
    Page<ItemVo> findByPage(ItemVo itemVo, Page<ItemVo> page);

    /**
     * 通过ID查看信息详情。
     *
     * @param id 主键
     * @return 对象
     */
    ItemVo findById(int id);

    /**
     * 通过code获取详细信息列表。
     *
     * @param category 类型
     * @return 集合
     */
    List<ItemVo> findByCategory(String category);

    /**
     * 查询类型是否存在。
     *
     * @param category 类型
     * @return 数据字典集合
     */
    List<Item> findItemNameExit(String category);
}
