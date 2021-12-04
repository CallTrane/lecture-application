package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lecture.domain.entities.StudentDO;
import com.lecture.infr.dao.StudentDAO;
import com.lecture.infr.gateway.StudentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @classname: StudentGatewayImpl
 * @description: TODO
 * @date: 2021/11/26 17:47
 * @author: Carl
 */
@Service
public class StudentGatewayImpl implements StudentGateway {

    @Autowired
    StudentDAO studentDAO;

    @Override
    public void registerStudent(StudentDO studentDO) {
        studentDAO.insert(studentDO);
    }

    @Override
    public StudentDO getStudentByUid(Integer uid) {
        LambdaQueryWrapper<StudentDO> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StudentDO::getUid, uid);
        return studentDAO.selectOne(wrapper);
    }
}
