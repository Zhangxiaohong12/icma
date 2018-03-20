package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:20
 */
public class Class {
    private int id;
    private String className;
    private int xiBieId;

    private XiBie xiBie;

    public XiBie getXiBie() {
        return xiBie;
    }

    public void setXiBie(XiBie xiBie) {
        this.xiBie = xiBie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getXiBieId() {
        return xiBieId;
    }

    public void setXiBieId(int xieBieId) {
        this.xiBieId = xieBieId;
    }
}
