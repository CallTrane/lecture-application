package com.lecture.infr.repository;

import com.lecture.domain.aggregates.user.UserRepository;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.domain.entities.UserDO;
import com.lecture.infr.gateway.StudentGateway;
import com.lecture.infr.gateway.TeacherGateway;
import com.lecture.infr.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @classname: UserRepositoryImpl
 * @description: TODO
 * @date: 2021/11/27 11:31
 * @author: Carl
 */
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserGateway userGateway;

    @Autowired
    StudentGateway studentGateway;

    @Autowired
    TeacherGateway teacherGateway;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerStudent(UserDO userDO, StudentDO studentDO) {
        studentDO.setUid(registerUser(userDO));
        studentGateway.registerStudent(studentDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerTeacher(UserDO userDO, TeacherDO teacherDO) {
        teacherDO.setUid(registerUser(userDO));
        teacherGateway.registerTeacher(teacherDO);
    }

    private Integer registerUser(UserDO userDO) {
        return userGateway.registerUser(userDO);
    }

    @Override
    public UserDO userLogin(String account, String password) {
        return userGateway.userLogin(account, password);
    }
}
