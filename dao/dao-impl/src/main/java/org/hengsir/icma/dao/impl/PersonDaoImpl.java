package org.hengsir.icma.dao.impl;

import org.hengsir.icma.dao.PersonWriteDao;
import org.hengsir.icma.entity.Person;
import org.hengsir.icma.dao.mapper.*;
import org.hengsir.icma.utils.pageHelper.Page;
import org.hengsir.icma.utils.pageHelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/1/10 上午11:55
 */
@Service
public class PersonDaoImpl implements PersonWriteDao {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public Boolean create(Person person) {
        try{
            personMapper.create(person);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Boolean delete(String personId) {
        try{
            personMapper.delete(personId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Person> findAll(Page<Person> page, Person person) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        personMapper.findAll(person);
        return PageHelper.endPage();
    }

    @Override
    public List<Person> findAll(Person person) {
        return personMapper.findAll(person);
    }

    @Override
    public Person findByUserId(int userId) {
        return personMapper.findByUserId(userId);
    }

    @Override
    public Person findById(String personId) {
        return personMapper.findById(personId);
    }
}
