package com.lecture.domain.aggregates.user;

import com.lecture.component.utils.BeanUtils;

import com.lecture.domain.entities.CollegeMajorDO;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.domain.entities.UserDO;
import com.lecture.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public static Map<Integer, Map<Integer, CollegeMajorDO>> collegeMajorMap = new HashMap<>();

    public static Map<Integer, CollegeMajorDO> collegeMap = new HashMap<>();

    private static UserRepository userRepository = BeanUtils.getBean(UserRepository.class);

    private static final String USER_KEY = "user";

    private UserTypeEnum userTypeEnum;

    private CollegeMajorDO collegeMajorDO;

    private UserDO userDO;

    private StudentDO studentDO;

    private TeacherDO teacherDO;

    public void registerUser() {
        Optional.ofNullable(getStudentDO())
                .ifPresentOrElse(studentDO -> userRepository.registerStudent(getUserDO(), studentDO),
                        () -> userRepository.registerTeacher(getUserDO(), getTeacherDO()));
    }

    public UserAggregate userLogin(String account, String password) {
        String userKey = USER_KEY + account;
        return Optional.ofNullable(userRepository.getUserByRedis(userKey)).orElseGet(() -> {
            this.userDO = userRepository.userLogin(account, password);
            if (Objects.equals((this.userTypeEnum = UserTypeEnum.parse(userDO.getType())), UserTypeEnum.STUDENT)) {
                this.studentDO = userRepository.getStudentByUid(userDO.getUid());
                this.collegeMajorDO = collegeMajorMap.get(studentDO.getCollegeId()).get(studentDO.getMajorId());
            } else if (Objects.equals(userTypeEnum, UserTypeEnum.TEACHER)) {
                this.teacherDO = userRepository.getTeacherByUid(userDO.getUid());
            }
            userRepository.saveUserInRedis(userKey, this);
            return this;
        });
    }

    public UserAggregate updateUser(UserAggregate userAggregate) {
        return null;
    }

}
