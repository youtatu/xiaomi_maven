package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/12 22:11
 */
public interface AddressService {

    List<Address> quaryByUid(int uid);

    void add(Address address);

    void delete(int id);

    void update(Address address);

    void defaultAddress(int id, int uid);
}
