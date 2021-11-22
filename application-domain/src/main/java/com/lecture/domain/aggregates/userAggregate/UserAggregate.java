package com.lecture.domain.aggregates.userAggregate;

import com.lecture.component.utils.BeanUtils;
import com.lecture.domain.eneities.StudentDO;
import com.lecture.domain.eneities.TeacherDO;

import com.lecture.domain.eneities.UserDO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @className: UserAggregate
 * @description: 用户聚合根
 * @author: Carl Tong
 * @date: 2021/11/20 19:10
 */
@Data
@NoArgsConstructor
public class UserAggregate {

    private UserRepository userRepository = BeanUtils.getBean(UserRepository.class);

    private UserDO userDO;

    private StudentDO studentDO;

    private TeacherDO teacherDO;

    public void registerUser() {
        Optional.ofNullable(getStudentDO())
                .ifPresentOrElse(studentDO -> userRepository.registerStudent(getUserDO(), studentDO),
                        () -> userRepository.registerTeacher(getUserDO(), getTeacherDO()));
    }


    public UserAggregate updateUser(UserAggregate userAggregate) {
        return null;
    }

}
