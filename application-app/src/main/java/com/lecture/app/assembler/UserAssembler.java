package com.lecture.app.assembler;

import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.component.exception.SystemException;
import com.lecture.domain.aggregates.userAggregate.UserAggregate;
import com.lecture.domain.eneities.StudentDO;
import com.lecture.domain.eneities.TeacherDO;
import com.lecture.domain.eneities.UserDO;
import com.lecture.domain.enums.UserTypeEnum;

import java.util.Objects;

/**
 * @className: UserAssembler
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 2:16
 */
public class UserAssembler {

    /**
     * 验证是否为用户
     *
     * @param type
     */
    public static boolean validateUser(Integer type) {
        if (Objects.equals(UserTypeEnum.STUDENT.getType(), type) || Objects.equals(UserTypeEnum.TEACHER.getType(), type)) {
            return true;
        }
        throw new SystemException("非法用户, 请重新操作");
    }

    public static boolean isStudent(Integer type) {
        if (Objects.equals(UserTypeEnum.STUDENT.getType(), type)) {
            return true;
        }
        throw new SystemException("你不是学生用户");
    }

    public static boolean isTeacher(Integer type) {
        if (Objects.equals(UserTypeEnum.TEACHER.getType(), type)) {
            return true;
        }
        throw new SystemException("你不是教师用户");
    }

    /**
     * 将 userRegisterDTO 转成 聚合根
     *
     * @param userRegisterDTO
     * @return 聚合根 UserAggregate
     */
    public static UserAggregate toAggregate(UserRegisterDTO userRegisterDTO) {
        UserAggregate userAggregate = new UserAggregate();
        UserDO userDO = new UserDO();
        userDO.setAccount(userRegisterDTO.getAccount());
        userDO.setPassword(userRegisterDTO.getPassword());
        userDO.setPhone(userRegisterDTO.getPhone());
        userDO.setEmail(userRegisterDTO.getEmail());
        userDO.setType(userRegisterDTO.getType());
        userDO.setYear(userRegisterDTO.getYear());
        userAggregate.setUserDO(userDO);
        if (isStudent(userRegisterDTO.getType())) {
            StudentDO studentDO = new StudentDO();
            studentDO.setName(userRegisterDTO.getName());
            studentDO.setEnrollment(Integer.valueOf(userRegisterDTO.getYear()));
            studentDO.setGraduation(studentDO.getEnrollment() + 4);
            studentDO.setCampusId(userRegisterDTO.getCampusId());
            studentDO.setCollegeId(userRegisterDTO.getCollegeId());
            studentDO.setMajorId(userRegisterDTO.getMajorId());
            userAggregate.setStudentDO(studentDO);
        } else if (isTeacher(userRegisterDTO.getType())){
            TeacherDO teacherDO = new TeacherDO();
            teacherDO.setName(userRegisterDTO.getName());
            teacherDO.setCollegeId(userRegisterDTO.getCollegeId());
            userAggregate.setTeacherDO(teacherDO);
        }
        return userAggregate;
    }
}
