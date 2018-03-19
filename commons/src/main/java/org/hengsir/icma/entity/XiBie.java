package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:21
 */
public class XiBie {

    private int id;
    private String xiBieName;
    private int schoolId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXiBieName() {
        return xiBieName;
    }

    public void setXiBieName(String xieBieName) {
        this.xiBieName = xieBieName;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
