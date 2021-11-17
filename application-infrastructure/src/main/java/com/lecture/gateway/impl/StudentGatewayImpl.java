package com.lecture.gateway.impl;

import com.lecture.dao.StudentDAO;
import com.lecture.gateway.StudentGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @className: StudentGatewayImpl
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 13:11
 */
public class StudentGatewayImpl implements StudentGateway {

    @Autowired
    StudentDAO studentDAO;

    public Optional<List<Integer>> getLessonIdsByStuId(Long stuId) {
        return Optional.ofNullable(studentDAO.getLessonIdsByStuId(stuId));
    }
}
