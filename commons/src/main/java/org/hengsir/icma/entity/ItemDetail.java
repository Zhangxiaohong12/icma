package org.hengsir.icma.entity;


import org.hengsir.icma.model.Model;

/**
 * 数字字典详情。
 *
 * @author hengsir 2017年5月20日
 * @version 1.0.0
 */
public class ItemDetail implements Model<Integer> {
    private static final long serialVersionUID = 1L;
    /**
     * 主键。
     */
    private Integer id;

    /**
     * 代码。
     */
    private String code;

    /**
     * 父代码。
     */
    private String supperCode;

    /**
     * 名称。
     */
    private String name;
    /**
     * 类型。
     */
    private String category;
    /**
     * 描述。
     */
    private String description;
    /**
     * 状态。
     */
    private String   status;
    /**
     * 排序。
     */
    private int   orderId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupperCode() {
        return supperCode;
    }

    public void setSupperCode(String supperCode) {
        this.supperCode = supperCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public Integer getKey() {
        return this.id;
    }
}
