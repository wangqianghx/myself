package com.tools.todo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述：TOKEN验证
 *
 * @author zhaowen at 2019/2/12 09:44
 * @version 1.0.0
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class HeaderToken {
    private String k;
    private String type;
}
