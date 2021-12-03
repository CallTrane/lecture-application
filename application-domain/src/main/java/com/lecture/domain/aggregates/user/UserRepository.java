package com.lecture.domain.aggregates.user;

import com.lecture.domain.entities.CollegeMajorDO;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.domain.entities.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @className: UserRepository
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 13:47
 */
@Repository
public interface UserRepository {

    void registerStudent(UserDO userDO, StudentDO studentDO);

    void registerTeacher(UserDO userDO, TeacherDO teacherDO);

    UserDO userLogin(String account, String password);

    Map<Integer, Map<Integer, CollegeMajorDO>> getCollegeMajorMap();

    StudentDO getStudentByUid(Integer uid);

    TeacherDO getTeacherByUid(Integer uid);
}
