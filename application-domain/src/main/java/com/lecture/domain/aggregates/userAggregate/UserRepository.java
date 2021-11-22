package com.lecture.domain.aggregates.userAggregate;

import com.lecture.domain.eneities.StudentDO;
import com.lecture.domain.eneities.TeacherDO;
import com.lecture.domain.eneities.UserDO;
import org.springframework.stereotype.Repository;

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
}
