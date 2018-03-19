package org.hengsir.icma.service.impl;

import org.hengsir.icma.dao.ItemDetailWriteDao;
import org.hengsir.icma.entity.ItemDetail;
import org.hengsir.icma.service.ItemDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础数据-->选录数据--业务实现层。
 *
 * @author hengsir 2017年5月20日
 */
@Service
public class ItemDetailServiceimpl implements ItemDetailService {

    private static Logger log = LoggerFactory.getLogger(ItemDetailServiceimpl.class);

    @Autowired
    private ItemDetailWriteDao itemDetailWriteDao;

    @Override
    public Boolean addItemDetail(ItemDetail itemDetail) {
        return itemDetailWriteDao.addItemDetail(itemDetail);
    }

    @Override
    public Boolean updateItemDetailParam(ItemDetail itemDetail) {
        return itemDetailWriteDao.updateItemDetailParam(itemDetail);
    }

    @Override
    public Boolean deleteItemDetailById(int id) {
        return itemDetailWriteDao.deleteItemDetailById(id);
    }

    @Override
    public Boolean deleteByCategory(String category) {
        return itemDetailWriteDao.deleteByCategory(category);
    }
}
