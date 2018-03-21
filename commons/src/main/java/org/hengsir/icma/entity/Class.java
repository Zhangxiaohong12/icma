package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/3/19 上午10:20
 */
public class Class {
    private Integer id;
    private String className;
    private Integer xiBieId;

    private XiBie xiBie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getXiBieId() {
        return xiBieId;
    }

    public void setXiBieId(Integer xiBieId) {
        this.xiBieId = xiBieId;
    }

    public XiBie getXiBie() {
        return xiBie;
    }

    public void setXiBie(XiBie xiBie) {
        this.xiBie = xiBie;
    }
}
