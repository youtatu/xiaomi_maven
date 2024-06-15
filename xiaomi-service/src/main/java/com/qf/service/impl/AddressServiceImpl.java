package com.qf.service.impl;

import com.qf.dao.AddressDao;
import com.qf.dao.impl.AddressDaoImpl;
import com.qf.entity.Address;
import com.qf.service.AddressService;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/12 22:12
 */
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> quaryByUid(int uid) {
        return addressDao.selectByUid(uid);
    }

    @Override
    public void add(Address address) {
        addressDao.insert(address);
    }

    @Override
    public void delete(int id) {
        addressDao.delete(id);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void defaultAddress(int id, int uid) {
        addressDao.defaultAddress(id,uid);
    }
}
