package com.pwc.sdc.sg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pwc.sdc.sg.model.UserStatement;
import com.pwc.sdc.sg.service.UserStatementService;
import com.pwc.sdc.sg.mapper.UserStatementMapper;
import org.springframework.stereotype.Service;

/**
* @author Xinhua X Yang
* @description 针对表【sg_user_statement(用户流水表)】的数据库操作Service实现
* @createDate 2025-06-04 10:30:50
*/
@Service
public class UserStatementServiceImpl extends ServiceImpl<UserStatementMapper, UserStatement>
    implements UserStatementService{

}




