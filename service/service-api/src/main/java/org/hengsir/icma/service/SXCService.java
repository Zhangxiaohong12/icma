package org.hengsir.icma.service;


import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.entity.XiBie;

/**
 * 学校，系别，班级业务操作
 *
 * @author hengsir
 * @date 2018/3/19 上午11:39
 */
public interface SXCService {

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

    Boolean deleteSchool(int schoolId);


    Boolean createXiBie(XiBie xiBie);

    Boolean updateXiBie(XiBie xiBie);

    Boolean deleteXiBie(int xiBieId);

    Boolean createClass(Class c);

    Boolean updateClass(Class c);

    Boolean deleteClass(int classId);
}
