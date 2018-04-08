package org.hengsir.icma.handler.response.body;

import org.hengsir.icma.entity.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/4 下午12:00
 */
public class ClassResponseBody {
    private Integer classId;

    private String className;

    private List<Class> classList = new ArrayList<>();

    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
