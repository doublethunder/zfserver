package com.dt.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author chenlei
 * @date 2019-04-23
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserHomepage {
    private String username;
    private String age;
    private String pic;
}
