package com.qf.dao;

import com.qf.entity.Address;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/12 22:16
 */
public interface AddressDao {
    List<Address> selectByUid(int uid);

    void insert(Address address);

    void delete(int id);

    void update(Address address);

    void defaultAddress(int id, int uid);
}
