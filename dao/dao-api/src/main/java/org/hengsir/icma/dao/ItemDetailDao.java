package org.hengsir.icma.dao;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.ItemDetail;

/**
 * 数字字典只读接口。
 *
 * @author hengsir 2017年5月20日
 */
public interface ItemDetailDao {

    /**
     * 根据id获取数据 -- 单表。
     *
     * @param id 主键
     * @return 单表数据
     */
    ItemDetail findItemDetailById(@Param("id") int id);

    /**
     * 根据查询类型和订单状态查询数据字典。
     *
     * @param itemDetail 数据字典详情对象
     * @return 数据字典详情对象
     */
    ItemDetail findItemDetailByCategoryAndOrderId(ItemDetail itemDetail);

    /**
     * 根据查询类型和编号查询数据字典。
     *
     * @param itemDetail 数据字典详情对象
     * @return 数据字典详情对象
     */
    ItemDetail findItemDetailByCategoryAndCode(ItemDetail itemDetail);

    ItemDetail findItemDetailByCategoryAndDescription(ItemDetail itemDetail);
}
