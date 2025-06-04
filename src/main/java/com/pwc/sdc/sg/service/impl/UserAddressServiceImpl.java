package com.pwc.sdc.sg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.model.UserAddress;
import com.pwc.sdc.sg.service.UserAddressService;
import com.pwc.sdc.sg.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_address(用户地址表)】的数据库操作Service实现
* @createDate 2025-06-04 10:30:50
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{

}




