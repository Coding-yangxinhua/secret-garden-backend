package com.pwc.sdc.sg.common.bean;

import com.pwc.sdc.sg.model.User;
import com.pwc.sdc.sg.model.dto.UserDto;

/**
 * @author 42268
 */
public class Auth {
    private static final ThreadLocal<UserDto> USER = new ThreadLocal<>();

    public static UserDto user() {
        return USER.get();
    }

    public static void setUser(UserDto user) {
        USER.set(user);
    }

    public static void remove() {
        USER.remove();
    }
}
