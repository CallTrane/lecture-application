package com.lecture.infr.gateway;

import com.lecture.domain.entities.TeacherDO;

import java.util.List;
import java.util.Optional;

/**
 * @classname: TeacherGateway
 * @description: TODO
 * @date: 2021/11/27 11:34
 * @author: Carl
 */
public interface TeacherGateway {

    /**
     * 注册新教师用户
     *
     * @param teacherDO
     */
    void registerTeacher(TeacherDO teacherDO);

    Optional<List<TeacherDO>> getTeachersByIds(List<Long> ids);
}
