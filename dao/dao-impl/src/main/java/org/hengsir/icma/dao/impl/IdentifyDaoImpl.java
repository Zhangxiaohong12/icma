package org.hengsir.icma.dao.impl;

import org.hengsir.icma.dao.IdentifyDao;
import org.hengsir.icma.dao.mapper.IdentifyMapper;
import org.hengsir.icma.entity.IdentyRecord;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/5/8 下午3:51
 */
@Service
public class IdentifyDaoImpl implements IdentifyDao {

    @Autowired
    IdentifyMapper identifyMapper;

    @Override
    public boolean record(IdentyRecord identyRecord) {
        try{
            identifyMapper.record(identyRecord);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<IdentyRecord> findRecord(IdentyRecord identyRecord,Page<IdentyRecord> page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        identifyMapper.findRecord(identyRecord);
        return PageHelper.endPage();
    }

    @Override
    public List<IdentyRecord> findAll(IdentyRecord identyRecord) {
        return identifyMapper.findAll(identyRecord);
    }

    @Override
    public IdentyRecord findById(Integer id) {
        return identifyMapper.findById(id);
    }

    @Override
    public IdentyRecord findByClass(Integer classId) {
        return identifyMapper.findByClass(classId);
    }
}
