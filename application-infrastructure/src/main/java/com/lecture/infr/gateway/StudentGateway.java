package com.lecture.infr.gateway;

import com.lecture.domain.entities.StudentDO;

import java.util.List;

/**
 * @classname: StudentGateway
 * @description: TODO
 * @date: 2021/11/26 17:46
 * @author: Carl
 */

public interface StudentGateway {

    void registerStudent(StudentDO studentDO);

    StudentDO getStudentByUid(Integer uid);
}
