package com.lecture.app.service;

import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.StartApplicationTest;
import com.lecture.domain.entities.UserDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: UserApplicationTest
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 18:06
 */

public class UserApplicationTest extends StartApplicationTest {

    @Autowired
    UserApplicationService userApplicationService;

    @Test
    public void testRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setType(1);
        userRegisterDTO.setAccount("huyuxi");
        userRegisterDTO.setPassword("huyuxi");
        userRegisterDTO.setCollegeId(1);
        userRegisterDTO.setMajorId(1);
        userRegisterDTO.setPhone("110");
        userRegisterDTO.setName("胡宇曦");
        userRegisterDTO.setEmail("huyuxi@qq.com");
        userRegisterDTO.setCampusId(1);
        userRegisterDTO.setYear("2018");
        userApplicationService.register(userRegisterDTO);
    }

    @Test
    public void testLogin() {
        UserDO user = userApplicationService.login("admin", "admin");
        System.out.println(user);
    }
}
