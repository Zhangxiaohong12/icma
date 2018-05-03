package org.hengsir.icma.dao.impl;

import org.hengsir.icma.dao.SXCWriteDao;
import org.hengsir.icma.dao.mapper.SXCMapper;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.entity.XiBie;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 上午11:59
 */
@Service
public class SXCDaoImpl implements SXCWriteDao{

    @Autowired
    SXCMapper sxcMapper;

    Logger logger = LoggerFactory.getLogger(SXCDaoImpl.class);

    @Override
    public Boolean createSchool(School school) {
        try{
            sxcMapper.createSchool(school);
            return true;
        }catch (Exception e){
            logger.error("创建学校失败",e);
            return false;
        }

    }

    @Override
    public Boolean updateSchool(School school) {
        try{
            sxcMapper.updateSchool(school);
            return true;
        }catch (Exception e){
            logger.error("更新学校信息失败",e);
            return false;
        }
    }

    @Override
    public Boolean deleteSchool(int schoolId) {
        try{
            sxcMapper.deleteSchool(schoolId);
            return true;
        }catch (Exception e){
            logger.error("删除学校失败");
            return false;
        }
    }

    @Override
    public Boolean createXiBie(XiBie xiBie) {
        try{
            sxcMapper.createXiBie(xiBie);
            return true;
        }catch (Exception e){
            logger.error("创建系别失败",e);
            return false;
        }
    }

    @Override
    public Boolean updateXiBie(XiBie xiBie) {
        try{
            sxcMapper.updateXiBie(xiBie);
            return true;
        }catch (Exception e){
            logger.error("更新系别失败",e);
            return false;
        }
    }

    @Override
    public Boolean deleteXiBie(int xiBieId) {
        try{
            sxcMapper.deleteXiBie(xiBieId);
            return true;
        }catch (Exception e){
            logger.error("删除系别失败");
            return false;
        }
    }

    @Override
    public Boolean createClass(Class c) {
        try{
            sxcMapper.createClass(c);
            return true;
        }catch (Exception e){
            logger.error("创建班级失败",e);
            return false;
        }
    }

    @Override
    public Boolean updateClass(Class c) {
        try{
            sxcMapper.updateClass(c);
            return true;
        }catch (Exception e){
            logger.error("更新班级失败",e);
            return false;
        }
    }

    @Override
    public Boolean deleteClass(int classId) {
        try{
            sxcMapper.deleteClass(classId);
            return true;
        }catch (Exception e){
            logger.error("删除班级失败",e);
            return false;
        }
    }

    @Override
    public Page<School> findSchool(School school, Page<School> page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        sxcMapper.findSchool(school);
        return PageHelper.endPage();
    }

    @Override
    public Page<XiBie> findXiBie(XiBie xiBie, Page<XiBie> page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        sxcMapper.findXiBie(xiBie);
        return PageHelper.endPage();
    }

    @Override
    public Page<Class> findClass(Class c, Page<Class> page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        sxcMapper.findClass(c);
        return PageHelper.endPage();
    }

    @Override
    public List<School> findAllSchool() {
        return sxcMapper.findAllSchool();
    }

    @Override
    public List<School> findSchool(School school) {
        return sxcMapper.findSchool(school);
    }

    @Override
    public List<XiBie> findXiBieByXi(XiBie xiBie) {
        return sxcMapper.findXiBie(xiBie);
    }

    @Override
    public List<XiBie> findAllXiBie() {
        return sxcMapper.findAllXiBie();
    }

    @Override
    public List<Class> findClassByC(Class c) {
        return sxcMapper.findClass(c);
    }

    @Override
    public List<Class> findAllClass() {
        return sxcMapper.findAllClass();
    }

    @Override
    public Class findClassById(int id) {
        return sxcMapper.findClassById(id);
    }

    @Override
    public XiBie findXiBieById(int id) {
        return sxcMapper.findXiBieById(id);
    }

    @Override
    public School findSchoolById(int id) {
        return sxcMapper.findSchoolById(id);
    }

    @Override
    public List<Class> findByIds(List<String> list) {
        return sxcMapper.findByIds(list);
    }
}
