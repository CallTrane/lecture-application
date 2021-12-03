package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lecture.component.utils.DataUtils;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.infr.dao.TeacherDAO;
import com.lecture.infr.gateway.TeacherGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @classname: TeacherGatewayImpl
 * @description: TODO
 * @date: 2021/11/27 11:35
 * @author: Carl
 */
@Service
public class TeacherGatewayImpl implements TeacherGateway {

    @Autowired
    TeacherDAO teacherDAO;

    @Override
    public void registerTeacher(TeacherDO teacherDO) {
        teacherDAO.insert(teacherDO);
    }

    @Override
    public Optional<List<TeacherDO>> getTeachersByIds(List<Long> ids) {
        if (DataUtils.isEmpty(ids)) {
            return Optional.empty();
        }
        return Optional.ofNullable(teacherDAO.selectBatchIds(ids));
    }

    @Override
    public TeacherDO getTeacherByUid(Integer uid) {
        LambdaQueryWrapper<TeacherDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherDO::getUid, uid);
        return teacherDAO.selectOne(wrapper);
    }
}
