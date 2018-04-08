package org.hengsir.icma.dao;

import org.hengsir.icma.entity.User;

/**
 * 负责新增，修改，删除
 */
public interface UserWriteDao extends UserDao {

    Boolean create(User user);

    Boolean delete(int userId);

    Boolean update(User user);

    Boolean bindPerson(User user);

    Boolean deBindPerson(int userId);

}
