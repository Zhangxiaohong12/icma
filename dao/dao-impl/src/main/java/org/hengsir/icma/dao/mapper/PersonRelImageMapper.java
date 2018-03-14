package org.hengsir.icma.dao.mapper;

import org.hengsir.icma.entity.PersonImageRelation;
import org.springframework.stereotype.Component;

/**
 * @author hengsir
 * @date 2018/1/5 下午4:09
 */
@Component
public interface PersonRelImageMapper {

    /**
     * 个体修改：增加人脸图片
     *
     * @return
     */
    public int addFace(PersonImageRelation personImageRelation);

    /**
     * 个体修改：删除人脸图片
     *
     * @return
     */
    public int deleteFace(PersonImageRelation personImageRelation);
}
