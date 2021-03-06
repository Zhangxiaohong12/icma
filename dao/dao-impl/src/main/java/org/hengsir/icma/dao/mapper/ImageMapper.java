package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.Image;
import org.hengsir.icma.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/1/5 下午4:22
 */
@Component
public interface ImageMapper {

    /**
     * 新增一张人脸图片
     *
     * @param image
     * @return
     */
    public int addImg(Image image);

    /**
     * 删除人脸图片
     *
     * @param faceId
     * @return
     */
    public int deleteImg(@Param("faceId") String faceId);

    /**
     * 根据图片id查找图片
     *
     * @param imageId
     * @return
     */
    public Image findById(@Param("imageId") int imageId);

    /**
     * 根据条件查询全部图片
     *
     * @param image
     * @return
     */
    public List<Image> findAll(Image image);

    public List<Image> findByPerson(Person person);

    void deleteImagesByPersonId(@Param("personId") String personId);

    Image findByFaceId(@Param("faceId") String faceId);

    Image find(Image image);
}
