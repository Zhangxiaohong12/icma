package org.hengsir.icma.dao;

import org.hengsir.icma.entity.IdentyRecord;
import org.hengsir.icma.utils.pageHelper.Page;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/5/8 上午11:14
 */
public interface IdentifyDao {
    boolean record(IdentyRecord identyRecord);

    Page<IdentyRecord> findRecord(IdentyRecord identyRecord,Page<IdentyRecord> page);

    List<IdentyRecord> findAll(IdentyRecord identyRecord);

    IdentyRecord findById(Integer id);

    IdentyRecord findByClass(Integer classId);
}
