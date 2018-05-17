package org.hengsir.icma.entity;

/**
 * @author hengsir
 * @date 2018/5/17 下午3:27
 */
public class IdentyRecordVo  extends IdentyRecord{
    private String createTimeBegin;

    private String createTimeEnd;

    public String getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(String createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
