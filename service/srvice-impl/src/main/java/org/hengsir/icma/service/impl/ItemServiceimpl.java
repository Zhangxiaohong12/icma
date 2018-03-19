package org.hengsir.icma.service.impl;


import org.hengsir.icma.dao.ItemWriteDao;
import org.hengsir.icma.entity.Item;
import org.hengsir.icma.service.ItemService;
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
public class ItemServiceimpl implements ItemService {

    private static Logger log = LoggerFactory.getLogger(ItemServiceimpl.class);

    @Autowired
    private ItemWriteDao itemWriteDao;

    @Override
    public Item addItem(Item item) {
        return itemWriteDao.addItem(item);
    }

    @Override
    public Boolean updateItem(Item item) {
        return itemWriteDao.updateItem(item);
    }

    @Override
    public Boolean deleteItemById(int id) {
        return itemWriteDao.deleteItemById(id);
    }
}
