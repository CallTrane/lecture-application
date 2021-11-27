package com.lecture.domain.aggregates.user;

import com.lecture.component.utils.BeanUtils;

import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.domain.entities.UserDO;
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

    public UserAggregate userLogin(String account, String password) {
        this.userDO = userRepository.userLogin(account, password);
        return this;
    }

    public UserAggregate updateUser(UserAggregate userAggregate) {
        return null;
    }

}
