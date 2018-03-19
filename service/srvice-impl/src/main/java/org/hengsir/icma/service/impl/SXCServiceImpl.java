package org.hengsir.icma.service.impl;

import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.dao.SXCWriteDao;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.entity.XiBie;
import org.hengsir.icma.service.SXCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hengsir
 * @date 2018/3/19 上午11:56
 */
@Service
public class SXCServiceImpl implements SXCService {

    @Autowired
    SXCWriteDao sxcWriteDao;

    @Override
    public Boolean createSchool(School school) {
        return sxcWriteDao.createSchool(school);
    }

    @Override
    public Boolean updateSchool(School school) {
        return sxcWriteDao.updateSchool(school);
    }

    @Override
    public Boolean deleteSchool(int schoolId) {
        return sxcWriteDao.deleteSchool(schoolId);
    }

    @Override
    public Boolean createXiBie(XiBie xiBie) {
        return sxcWriteDao.createXiBie(xiBie);
    }

    @Override
    public Boolean updateXiBie(XiBie xiBie) {
        return sxcWriteDao.updateXiBie(xiBie);
    }

    @Override
    public Boolean deleteXiBie(int xiBieId) {
        return sxcWriteDao.deleteXiBie(xiBieId);
    }

    @Override
    public Boolean createClass(Class c) {
        return sxcWriteDao.createClass(c);
    }

    @Override
    public Boolean updateClass(Class c) {
        return sxcWriteDao.updateClass(c);
    }

    @Override
    public Boolean deleteClass(int classId) {
        return sxcWriteDao.deleteClass(classId);
    }
}
