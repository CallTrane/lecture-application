package com.lecture.infr.gateway.impl;

import com.lecture.domain.eneities.StudentDO;
import com.lecture.domain.eneities.TeacherDO;
import com.lecture.domain.eneities.UserDO;
import com.lecture.infr.dao.StudentDAO;
import com.lecture.infr.dao.TeacherDAO;
import com.lecture.infr.dao.UserDAO;
import com.lecture.infr.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: UserGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 14:19
 */
@Service
public class UserGatewayImpl implements UserGateway {

    @Autowired
    UserDAO userDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    TeacherDAO teacherDAO;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerStudent(UserDO userDO, StudentDO studentDO) {
        studentDO.setUid(registerUser(userDO));
        studentDAO.insert(studentDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerTeacher(UserDO userDO, TeacherDO teacherDO) {
        teacherDO.setUid(registerUser(userDO));
        teacherDAO.insert(teacherDO);
    }

    private Integer registerUser(UserDO userDO) {
        userDAO.insert(userDO);
        return userDO.getUid();
    }
}
