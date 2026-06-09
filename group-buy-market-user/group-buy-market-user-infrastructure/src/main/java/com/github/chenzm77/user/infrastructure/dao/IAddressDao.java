package com.github.chenzm77.user.infrastructure.dao;

import com.github.chenzm77.user.infrastructure.dao.po.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAddressDao {

    List<Address> selectByUserId(Long userId);

    Address selectById(Long id);

    int insert(Address address);

    int updateById(Address address);

    int deleteById(@Param("id") Long id, @Param("userId") Long userId);

    int clearDefaultByUserId(Long userId);

    int setDefaultById(@Param("id") Long id, @Param("userId") Long userId);
}