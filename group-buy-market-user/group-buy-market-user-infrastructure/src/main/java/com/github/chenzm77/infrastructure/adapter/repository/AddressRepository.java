package com.github.chenzm77.infrastructure.adapter.repository;

import com.github.chenzm77.domain.user.adapter.repository.IAddressRepository;
import com.github.chenzm77.domain.user.model.entity.AddressEntity;
import com.github.chenzm77.infrastructure.dao.IAddressDao;
import com.github.chenzm77.infrastructure.dao.po.Address;
import com.github.chenzm77.infrastructure.util.SnowflakeIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AddressRepository implements IAddressRepository {

    @Resource
    private IAddressDao addressDao;

    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public List<AddressEntity> queryByUserId(Long userId) {
        List<Address> addresses = addressDao.selectByUserId(userId);
        return addresses.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public AddressEntity queryById(Long addressId) {
        Address address = addressDao.selectById(addressId);
        return toEntity(address);
    }

    @Override
    public boolean save(AddressEntity addressEntity) {
        if (addressEntity.getId() == null) {
            addressEntity.setId(snowflakeIdGenerator.nextId());
        }
        Address address = new Address();
        BeanUtils.copyProperties(addressEntity, address);
        return addressDao.insert(address) > 0;
    }

    @Override
    public boolean update(AddressEntity addressEntity) {
        Address address = new Address();
        BeanUtils.copyProperties(addressEntity, address);
        return addressDao.updateById(address) > 0;
    }

    @Override
    public boolean delete(Long addressId, Long userId) {
        return addressDao.deleteById(addressId, userId) > 0;
    }

    @Override
    public boolean clearDefault(Long userId) {
        addressDao.clearDefaultByUserId(userId);
        return true;
    }

    @Override
    public boolean setDefault(Long addressId, Long userId) {
        return addressDao.setDefaultById(addressId, userId) > 0;
    }

    private AddressEntity toEntity(Address address) {
        if (address == null) {
            return null;
        }
        AddressEntity entity = new AddressEntity();
        BeanUtils.copyProperties(address, entity);
        return entity;
    }
}