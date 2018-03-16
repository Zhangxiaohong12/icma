package org.hengsir.icma.dao;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.ItemDetail;

/**
 * 数字字典读写接口。
 *
 * @author hengsir 2017年5月20日
 * @version 1.0.0
 */
public interface ItemDetailWriteDao extends ItemDetailDao {
    /**
     * 新增数据字典详情数据。
     *
     * @param itemDetail 数据字典详情参数
     */
    ItemDetail addItemDetail(ItemDetail itemDetail);

    /**
     * 修改数据字典。
     *
     * @param itemDetail 数据字典参数
     */
    Boolean updateItemDetailParam(ItemDetail itemDetail);

    /**
     * 删除数据字典详细。
     *
     * @param id 数据字典详细表id
     */
    Boolean deleteItemDetailById(int id);

    /**
     * 通过类型删除数据字典详情。
     *
     * @param category 类型。
     */
    Boolean deleteByCategory(@Param("category") String category);
}
