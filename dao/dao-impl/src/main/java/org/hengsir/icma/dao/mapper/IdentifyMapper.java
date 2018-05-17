package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.IdentyRecord;
import org.hengsir.icma.utils.pageHelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/5/8 上午11:27
 */
@Component
public interface IdentifyMapper {
    void record(IdentyRecord identyRecord);

    List<IdentyRecord> findRecord(IdentyRecord identyRecord);

    List<IdentyRecord> findHistRecord(IdentyRecord identyRecord);

    List<IdentyRecord> findAll(IdentyRecord identyRecord);

    IdentyRecord findById(@Param("id") Integer id);

    IdentyRecord findByClass(@Param("classId") Integer classId);

    IdentyRecord findHistById(@Param("id") Integer id);

    void clearRecord();

    void toHist();
}
