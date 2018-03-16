package org.hengsir.icma.dao.impl;

import org.hengsir.icma.dao.UserWriteDao;
import org.hengsir.icma.entity.Right;
import org.hengsir.icma.entity.Role;
import org.hengsir.icma.entity.User;
import org.hengsir.icma.dao.mapper.*;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoImpl implements UserWriteDao {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User selectUserByAccount(String userAccount) {
        User user = userMapper.findUserByAccount(userAccount);
        if (user != null && "1".equals(user.getUserStatus())) {
            return user;
        }
        return null;
    }

    @Override
    public User selectUserById(int userId) {
        User user = userMapper.findUserById(userId);
        if (user != null && "1".equals(user.getUserStatus())) {
            return user;
        }
        return null;
    }

    @Override
    public List<Role> selectRolesByAccount(String userAccount) {
        return userMapper.getRolesByAccount(userAccount);
    }

    @Override
    public List<Right> selectPermissionsByAccount(String userAccount) {
        return userMapper.getPermissionsByAccount(userAccount);
    }

    @Override
    public Page<User> findByUser(User user, Page<User> page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        userMapper.findByUser(user);
        return PageHelper.endPage();
    }

    @Override
    public List<User> findByUser(User user) {
        return userMapper.findByUser(user);
    }


    @Override
    public Boolean create(User user) {
        try {
            userMapper.create(user);
            return true;
        } catch (Exception ex) {
            logger.error("create User find error!", ex);
            return false;
        }
    }

    @Override

    public Boolean delete(int userId) {
        try {
            userMapper.delete(userId);
            return true;
        } catch (Exception ex) {
            logger.error("delete user find error!", ex);
            return false;
        }
    }

    @Override
    public Boolean update(User user) {
        try {
            userMapper.update(user);
            return true;
        } catch (Exception ex) {
            logger.error("update User find error!", ex);
            return false;
        }
    }
}
