package org.hengsir.icma.dao;

import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.entity.XiBie;
import org.hengsir.icma.utils.pageHelper.Page;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 上午11:43
 */
public interface SXCDao {

    Page<School> findSchool(School school,Page<School> page);

    Page<XiBie> findXiBie(XiBie xiBie,Page<XiBie> page);

    Page<Class> findClass(Class c,Page<Class> page);

    List<School> findAllSchool();

    List<School> findSchool(School school);

    List<XiBie> findXiBieByXi(XiBie xiBie);

    List<XiBie> findAllXiBie();

    List<Class> findClassByC(Class c);

    List<Class> findAllClass();

    Class findClassById(int id);

    XiBie findXiBieById(int id);

    School findSchoolById(int id);

    List<Class> findByIds(List<String> list);
}
