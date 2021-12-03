package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lecture.component.utils.DataUtils;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.query.LessonQuery;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @className: LessonGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:26
 */
@Service
public class LessonGatewayImpl implements LessonGateway {

    @Autowired
    private LessonDAO lessonDAO;

    /**
     * 查询所有课程
     *
     * @return
     */
    private List<LessonDO> getAllLesson() {
        QueryWrapper<LessonDO> wrapper = new QueryWrapper<>();
        return lessonDAO.selectList(wrapper);
    }


    @Override
    public List<LessonDO> getAllLessons() {
        return lessonDAO.selectList(null);
    }

    @Override
    public Map<Integer, List<LessonDO>> getCollegeLessonsMap() {
        return getAllLesson().stream().collect(Collectors.groupingBy(LessonDO::getCollegeId));
    }

    @Override
    public Map<Integer, List<LessonDO>> getMajorLessonsMap() {
        return getAllLesson().stream().collect(Collectors.groupingBy(LessonDO::getMajorId));
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

    @Override
    public List<LessonDO> getLessonsByCondition(LessonQuery lessonQuery) {
        return lessonDAO.getLessonsByCondition(lessonQuery);
    }

    @Override
    public Optional<List<LessonDO>> getLessonsByIds(List<Integer> ids) {
        return Optional.ofNullable(lessonDAO.selectBatchIds(ids));
    }

    @Override
    public List<LessonDO> getLessonsByStuId(Long stuId) {
        List<Integer> lessonIds = lessonDAO.getLessonIdByStudentId(stuId);
        if (DataUtils.isEmpty(lessonIds)) {
            return Collections.emptyList();
        }
        return lessonDAO.selectBatchIds(lessonIds);
    }

    // todo: 选课和退课需要刷新缓存
    @Override
    public void selectLesson(Integer lessonId, Long stuId) {
        lessonDAO.selectLesson(lessonId, stuId);
    }

    @Override
    public void dropLesson(Integer lessonId, Long stuId) {
        lessonDAO.dropLesson(lessonId, stuId);
    }
}
