package com.pwc.sdc.sg.common.config;

import com.pwc.sdc.sg.common.enums.StatusEnum;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author Xinhua X Yang
 */
@Configuration
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler { ;

	@Override
	public void insertFill(MetaObject metaObject) {
		Date now = new Date();
		metaObject.setValue("gmtCreate", now);
		metaObject.setValue("gmtModified", now);
		metaObject.setValue("deleted", StatusEnum.DISABLE.value());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		Date now = new Date();
		metaObject.setValue("gmtModified", now);
	}

}
