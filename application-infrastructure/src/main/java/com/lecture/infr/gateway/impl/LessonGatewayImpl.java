package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.LessonGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @className: LessonGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:26
 */
@Service
public class LessonGatewayImpl implements LessonGateway {

    @Autowired
    LessonDAO lessonDAO;

    @Override
    public Map<Integer, List<LessonDO>> getAllLesson() {
        return null;
    }

    @Override
    public List<LessonDO> getLessonsByMajorId(Integer majorId) {
        LambdaQueryWrapper<LessonDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LessonDO::getMajorId, majorId);
        return lessonDAO.selectList(wrapper);
    }

    @Override
    public List<LessonDO> getLessonsByCollegeId(Integer collegeId) {
        LambdaQueryWrapper<LessonDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LessonDO::getCollegeId, collegeId);
        return lessonDAO.selectList(wrapper);
    }
}
