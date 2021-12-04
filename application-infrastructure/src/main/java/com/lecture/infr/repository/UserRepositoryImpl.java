package com.lecture.infr.repository;

import com.lecture.domain.aggregates.user.UserAggregate;
import com.lecture.domain.aggregates.user.UserRepository;
import com.lecture.domain.entities.CollegeMajorDO;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.domain.entities.UserDO;
import com.lecture.infr.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @classname: UserRepositoryImpl
 * @description: TODO
 * @date: 2021/11/27 11:31
 * @author: Carl
 */
@Service
public class UserRepositoryImpl implements UserRepository {

    private static final ReentrantLock REDIS_LOCK = new ReentrantLock();

    @Autowired
    UserGateway userGateway;

    @Autowired
    SystemGateway systemGateway;

    @Autowired
    StudentGateway studentGateway;

    @Autowired
    TeacherGateway teacherGateway;

    @Autowired
    RedisGateway redisGateway;

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

    @Override
    public Map<Integer, Map<Integer, CollegeMajorDO>> getCollegeMajorMap() {
        return systemGateway.getCollegeMajorMap();
    }

    @Override
    public StudentDO getStudentByUid(Integer uid) {
        return studentGateway.getStudentByUid(uid);
    }

    @Override
    public TeacherDO getTeacherByUid(Integer uid) {
        return teacherGateway.getTeacherByUid(uid);
    }

    @Override
    public UserAggregate getUserByRedis(String userKey) {
        return redisGateway.get(userKey);
    }

    @Override
    public void saveUserInRedis(String userKey, UserAggregate userAggregate) {
        redisGateway.set(userKey, userAggregate, 86200L);
    }
}
