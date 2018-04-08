package org.hengsir.icma.dao;


import org.hengsir.icma.entity.Right;
import org.hengsir.icma.entity.Role;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.utils.pageHelper.Page;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * 根据帐号查询用户。
     * @param userAccount 用户账号
     */
    User selectUserByAccount(String userAccount);

    /**
     * 根据id查询用户。
     * @param userId 用户id
     */
    User selectUserById(int userId);

    /**
     * 根据帐号得出用户角色。
     * @param userAccount 用户账号
     */
    List<Role> selectRolesByAccount(String userAccount);


    /**
     * 根据帐号得出用户所有的权限。
     * @param userAccount 用户账号
     */
    List<Right> selectPermissionsByAccount(String userAccount);

    /**
     * 分页查询所有的用户。
     * @param user 用户信息
     * @param page 分页
     * @return 用户信息列表
     */
    public Page<User> findByUser(User user, Page<User> page);

    /**
     * 根据用户信息获取列表。
     * @param user 用户信息过滤条件
     * @return 用户信息列表
     */
    public List<User> findByUser(User user);

    /**
     * 根据personId和classId
     * @param user
     * @return
     */
    User find(User user);

    List<User> identify(Map<String,Object> map);

    int studentCounts(int classId);
}
