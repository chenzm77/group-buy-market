package com.github.chenzm77.infrastructure.dao;

import com.github.chenzm77.infrastructure.dao.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {

    User selectById(Long id);

    User selectByPhone(String phone);

    int insert(User user);

    int updateById(User user);

    List<User> selectByIds(@Param("ids") List<Long> ids);
}