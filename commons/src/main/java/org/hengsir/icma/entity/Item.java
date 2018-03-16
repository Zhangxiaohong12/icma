package org.hengsir.icma.entity;


import org.hengsir.icma.model.Model;

import java.util.List;

/**
 * 数字字典bean。
 *
 * @author hengsir 2017年5月20日
 * @version 1.0.1
 */
public class Item implements Model<Integer> {
    private static final long serialVersionUID = 1L;
    /**
     * 主键。
     */
    private int id;

    /**
     * 类型。
     */
    private String category;

    /**
     * 名称。
     */
    private String name;
    /**
     * 描述。
     */
    private String description;
    /**
     * 状态。
     */
    private String status;

    /**
     * 排序。
     */
    private int orderId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public Integer getKey() {
        return this.id;
    }
}
