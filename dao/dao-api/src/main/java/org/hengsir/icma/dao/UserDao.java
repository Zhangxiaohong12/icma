package org.hengsir.icma.dao;


import org.hengsir.icma.entity.Right;
import org.hengsir.icma.entity.Role;
import org.hengsir.icma.entity.User;

import java.util.List;

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
}
