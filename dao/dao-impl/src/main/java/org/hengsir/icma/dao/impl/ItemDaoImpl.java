package org.hengsir.icma.dao.impl;

import org.hengsir.icma.dao.ItemWriteDao;
import org.hengsir.icma.dao.mapper.ItemMapper;
import org.hengsir.icma.entity.Item;
import org.hengsir.icma.entity.ItemVo;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典数据接口实现类。
 *
 * @author hengsir 2017年5月20日
 */
@Service
public class ItemDaoImpl implements ItemWriteDao {

    @Autowired
    private ItemMapper itemMapper;

    private final Logger logger = LoggerFactory.getLogger(ItemDaoImpl.class);//日志

    @Override
    public List<Item> read() {
        return itemMapper.read();
    }

    @Override
    public Item read(int id) {
        return itemMapper.read(id);
    }

    @Override
    public Page<ItemVo> findByPage(ItemVo em, Page<ItemVo> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        itemMapper.findByPage(em);
        return PageHelper.endPage();
    }

    @Override
    public ItemVo findById(int id) {
        return itemMapper.findById(id);
    }

    @Override
    public List<ItemVo> findByCategory(String category) {
        return itemMapper.findByCategory(category);
    }

    @Override
    public List<Item> findItemNameExit(String category) {
        return itemMapper.findItemNameExit(category);
    }

    @Override
    public Item addItem(Item item) {
        itemMapper.addItem(item);
        return itemMapper.getByGate(item.getCategory());
    }

    @Override
    public Boolean updateItem(Item item) {
        try {
            itemMapper.updateItem(item);
            return true;
        } catch (Exception exception) {
            logger.error("", exception);
            return false;
        }
    }

    @Override
    public Boolean deleteItemById(int id) {
        try {
            itemMapper.deleteItemById(id);
            return true;
        } catch (Exception exception) {
            logger.error("", exception);
            return false;
        }
    }
}
