package org.hengsir.icma.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字字典DTO。
 *
 * @author hengsir
 * @version 1.0.0
 */
public class ItemVo extends Item {
    private static final long serialVersionUID = 1L;

    private List<ItemDetail> itemDetails = new ArrayList<ItemDetail>();

    /**
     * 导出标志（1:导出当页，2:导出全部）。
     */
    private String exportCode;

    private String itemDetailStr;

    private String rCategory;

    public String getrCategory() {
        return rCategory;
    }

    public void setrCategory(String rCategory) {
        this.rCategory = rCategory;
    }

    public String getItemDetailStr() {
        return itemDetailStr;
    }

    public void setItemDetailStr(String itemDetailStr) {
        this.itemDetailStr = itemDetailStr;
    }

    public String getExportCode() {
        return exportCode;
    }

    public void setExportCode(String exportCode) {
        this.exportCode = exportCode;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
