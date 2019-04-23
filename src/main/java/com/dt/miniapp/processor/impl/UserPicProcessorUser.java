package com.dt.miniapp.processor.impl;

import com.dt.miniapp.model.UserHomepage;
import com.dt.miniapp.processor.IUserHomepageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenlei
 * @date 2019-04-23
 */
@Slf4j
@Component
public class UserPicProcessorUser implements IUserHomepageProcessor {

    @Override
    public UserHomepage processor(UserHomepage userHomepage) {
        log.info("进入用户头像渲染，开始等待");
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userHomepage.setPic("xxxxxxxxxxxxxxx");
        return userHomepage;
    }
}
