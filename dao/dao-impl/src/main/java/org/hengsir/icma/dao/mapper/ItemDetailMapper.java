package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.ItemDetail;


/**
 * 基础数据-->选录数据--mybatis接口。
 *
 * @author hengsir
 */
public interface ItemDetailMapper {
    /**
     * 根据id获取数据 -- 单表。
     *
     * @param id 主键
     * @return 单表数据
     */
    ItemDetail findItemDetailById(@Param("id") int id);

    /**
     * 根据查询类型和订单状态查询数据字典
     * @param itemDetail 数据字典详情对象
     * @return 数据字典详情对象
     */
    ItemDetail findItemDetailByCategoryAndOrderId(ItemDetail itemDetail);


    /**
     * 根据查询类型和编号查询数据字典
     * @param itemDetail 数据字典详情对象
     * @return 数据字典详情对象
     */
    ItemDetail findItemDetailByCategoryAndCode(ItemDetail itemDetail);

    /**
     * 新增数据字典详情数据。
     * @param itemDetail 数据字典详情参数
     */
    void addItemDetail(ItemDetail itemDetail);

    /**
     * 修改数据字典。
     * @param itemDetail 数据字典参数
     */
    void updateItemDetailParam(ItemDetail itemDetail);

    /**
     * 删除数据字典详细。
     * @param id 数据字典详细表id
     */
    void deleteItemDetailById(@Param("id") int id);

    /**
     * 通过类型删除数据字典详情。
     * @param category 类型。
     */
    void deleteByCategory(@Param("category") String category);

    ItemDetail findItemDetailByCategoryAndDescription(ItemDetail itemDetail);
}
