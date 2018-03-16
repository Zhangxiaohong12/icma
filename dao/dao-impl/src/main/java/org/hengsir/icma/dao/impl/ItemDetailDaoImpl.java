package org.hengsir.icma.dao.impl;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.dao.ItemDetailWriteDao;
import org.hengsir.icma.dao.mapper.ItemDetailMapper;
import org.hengsir.icma.entity.ItemDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据字典数据接口实现类。
 *
 * @author hengsir 2017年5月20日
 */
@Service
public class ItemDetailDaoImpl implements ItemDetailWriteDao {

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    private final Logger logger = LoggerFactory.getLogger(ItemDetailDaoImpl.class);//日志

    @Override
    public ItemDetail findItemDetailById(@Param("id") int id) {
        return itemDetailMapper.findItemDetailById(id);
    }

    @Override
    public ItemDetail findItemDetailByCategoryAndOrderId(
        ItemDetail itemDetail) {
        return itemDetailMapper.findItemDetailByCategoryAndOrderId(itemDetail);
    }

    @Override
    public ItemDetail findItemDetailByCategoryAndCode(ItemDetail itemDetail) {
        return itemDetailMapper.findItemDetailByCategoryAndCode(itemDetail);
    }

    @Override
    public ItemDetail addItemDetail(ItemDetail itemDetail) {
        itemDetailMapper.addItemDetail(itemDetail);
        return itemDetailMapper.findItemDetailById(itemDetail.getId());
    }


    @Override
    public Boolean updateItemDetailParam(ItemDetail itemDetail) {
        try {
            itemDetailMapper.updateItemDetailParam(itemDetail);
            return true;
        } catch (Exception exception) {
            logger.error("修改数据字典详情失败", exception);
            return false;
        }
    }

    @Override
    public Boolean deleteItemDetailById(int id) {
        try {
            itemDetailMapper.deleteItemDetailById(id);
            return true;
        } catch (Exception exception) {
            logger.error("通过id删除数据字典详情失败", exception);
            return false;
        }
    }

    @Override
    public Boolean deleteByCategory(String category) {
        try {
            itemDetailMapper.deleteByCategory(category);
            return true;
        } catch (Exception exception) {
            logger.error("通过类型删除数据字典详情失败", exception);
            return false;
        }
    }

    @Override
    public ItemDetail findItemDetailByCategoryAndDescription(ItemDetail itemDetail) {
        return itemDetailMapper.findItemDetailByCategoryAndDescription(itemDetail);
    }
}
