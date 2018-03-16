package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.Item;
import org.hengsir.icma.entity.ItemVo;

import java.util.List;


/**
 * 基础数据-->选录数据--mybatis接口。
 *
 * @author hengsir
 */
public interface ItemMapper {

    /**
     * 获取所有数据--单表。
     *
     * @return 单表数据集合
     */
    List<Item> read();

    /**
     * 根据id获取数据 -- 单表。
     *
     * @param id 主键
     * @return 单表数据
     */
    Item read(@Param("id") int id);

    /**
     * 根据条件获取数据 -- 混合数据。
     *
     * @param em 对象
     * @return 混合数据集合。
     */
    List<ItemVo> findByPage(@Param("item") ItemVo em);

    /**
     * 通过id查看信息详情。
     *
     * @param id 主键
     * @return 对象
     */
    ItemVo findById(@Param("id") int id);

    /**
     * 通过category获取数据。
     *
     * @param category 类型
     * @return 集合
     */
    List<ItemVo> findByCategory(@Param("category") String category);

    /**
     * 新增数据字典数据。
     *
     * @param item 数据字典参数
     */
    void addItem(Item item);

    /**
     * 修改数据字典。
     *
     * @param item 数据字典参数
     */
    void updateItem(Item item);

    /**
     * 删除数据字典通过id。
     * @param id 数据字典id
     */
    void deleteItemById(@Param("id") int id);

    /**
     * 查询类型是否存在
     * @param category 类型
     * @return 数据字典集合
     */
    List<Item> findItemNameExit(@Param("category") String category);
}
