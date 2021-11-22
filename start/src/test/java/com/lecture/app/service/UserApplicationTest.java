package com.lecture.app.service;

import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.StartApplicationTest;
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
        userRegisterDTO.setAccount("11111");
        userRegisterDTO.setPassword("12321321");
        userRegisterDTO.setCollegeId(1);
        userRegisterDTO.setMajorId(1);
        userRegisterDTO.setPhone("1111111111");
        userRegisterDTO.setName("q2321312312");
        userRegisterDTO.setEmail("asdasdas@qq.com");
        userRegisterDTO.setCampusId(1);
        userRegisterDTO.setYear("2018");
        userApplicationService.register(userRegisterDTO);
    }
}
