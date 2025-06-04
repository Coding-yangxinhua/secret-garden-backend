package com.pwc.sdc.sg.model.dto;

import com.pwc.sdc.sg.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xinhua X Yang
 */
@Getter
@Setter
public class UserDto extends User {
    private String ip;

}
