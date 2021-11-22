package com.lecture.domain.enums;

import com.lecture.component.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @className: UserTypeEnum
 * @description: TODO
 * @author: Carl Tong
 */

@AllArgsConstructor
public enum UserTypeEnum {

    /**
     * 用户类型
     */
    STUDENT(1, "学生"),
    TEACHER(2, "教师"),
    ADMIN(3, "管理员");

    @Getter
    private final Integer type;
    private final String name;
    private static final Map<Integer, UserTypeEnum> USER_TYPE_ENUM_MAP = Arrays.stream(UserTypeEnum.values())
            .collect(Collectors.toMap(UserTypeEnum::getType, Function.identity()));

    UserTypeEnum parse(Integer type) {
        return USER_TYPE_ENUM_MAP.get(type);
    }
}
