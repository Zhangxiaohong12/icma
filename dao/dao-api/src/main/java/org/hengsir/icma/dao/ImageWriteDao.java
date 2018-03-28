package org.hengsir.icma.dao;

import org.hengsir.icma.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/1/10 下午12:00
 */
public interface ImageWriteDao extends ImageDao {

    /**
     * 新增一张人脸图片
     *
     * @param image
     * @return
     */
    int addImg(Image image);

    /**
     * 删除一张人脸图片
     *
     * @param faceId
     * @return
     */
    int deleteImg(String faceId);

    void deleteByPersonId(String personId);
}
