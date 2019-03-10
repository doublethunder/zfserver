package com.dt.miniapp.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * @author chenlei
 * @description
 * @date 2019/3/9 11:07 PM
 */
@Data
@ToString
@Builder
public class Person {
    @Id
    private String id;

    private String firstName;
    private String lastName;
}
