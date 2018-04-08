package org.hengsir.icma.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.icma.entity.Right;
import org.hengsir.icma.entity.Role;
import org.hengsir.icma.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {

    /**
     * 根据帐号查询用户。
     * @param userAccount 用户账号
     */
    User findUserByAccount(@Param("userAccount") String userAccount);

    /**
     * 根据id查询用户。
     * @param userId 用户id
     */
    User findUserById(@Param("userId") int userId);

    /**
     * 根据帐号得出用户角色。
     * @param userAccount 用户账号
     */
    List<Role> getRolesByAccount(@Param("userAccount") String userAccount);


    /**
     * 根据帐号得出用户所有的权限。
     * @param userAccount 用户账号
     */
    List<Right> getPermissionsByAccount(@Param("userAccount") String userAccount);

    /**
     * 根据多条件来查询
     * @param user
     * @return
     */
    List<User> findByUser(User user);

    /**
     * 创建用户
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据用户id删除用户
     * @param userId
     * @return
     */
    int delete(@Param("userId") int userId);

    void bindPerson(User user);

    void deBindPerson(@Param("userId") int userId);

    User find(User user);

    List<User> identify(Map<String,Object> map);

    int studentCounts(@Param("classId") int classId);
}
