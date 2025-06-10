package com.pwc.sdc.sg.model.dto;

import com.pwc.sdc.sg.model.CardCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xinhua X Yang
 */
@Getter
@Setter
public class CardCodeDto extends CardCode {
    private Long userId;
}
