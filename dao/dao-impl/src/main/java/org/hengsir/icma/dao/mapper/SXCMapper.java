package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.entity.XiBie;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/3/19 下午12:00
 */
@Component
public interface SXCMapper {

    /**
     * 创建学校
     * @param school
     * @return
     */
    Boolean createSchool(School school);

    /**
     * 修改学校信息
     * @param school
     * @return
     */
    Boolean updateSchool(School school);

    Boolean deleteSchool(@Param("schoolId") int schoolId);


    Boolean createXiBie(XiBie xiBie);

    Boolean updateXiBie(XiBie xiBie);

    Boolean deleteXiBie(@Param("xiBieId")int xiBieId);

    Boolean createClass(Class c);

    Boolean updateClass(Class c);

    Boolean deleteClass(@Param("classId")int classId);

    List<School> findSchool(School school);

    List<XiBie> findXiBie(XiBie xiBie);

    List<Class> findClass(Class c);

    List<School> findAllSchool();

    List<XiBie> findXiBieByXi(XiBie xiBie);

    List<XiBie> findAllXiBie();

    List<Class> findClassByC(Class c);

    List<Class> findAllClass();

    School findSchoolById(@Param("id")int id);

    XiBie findXiBieById(@Param("id")int id);

    Class findClassById(@Param("id")int id);

    List<Class> findByIds(List<String> list);

}
