package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lecture.domain.entities.CampusDO;
import com.lecture.domain.entities.CollegeMajorDO;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.CampusDAO;
import com.lecture.infr.dao.CollegeMajorDAO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.SystemGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @className: SystemGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/20 18:21
 */
@Service
public class SystemGatewayImpl implements SystemGateway {

    @Autowired
    CampusDAO campusDAO;

    @Autowired
    CollegeMajorDAO collegeMajorDAO;

    @Autowired
    RedisGateway redisGateway;

    @Autowired
    LessonGateway lessonGateway;

    @Override
    public Map<Integer, Map<Integer, CollegeMajorDO>> getCollegeMajorMap() {
        QueryWrapper<CollegeMajorDO> wrapper = new QueryWrapper<>();
        return collegeMajorDAO.selectList(wrapper).stream()
                .collect(Collectors.groupingBy(CollegeMajorDO::getCollegeId,
                        Collectors.toMap(CollegeMajorDO::getMajorId, Function.identity())));
    }

    @Override
    public Map<Integer, String> getCollegeMap() {
        QueryWrapper<CollegeMajorDO> wrapper = new QueryWrapper<>();
        return collegeMajorDAO.selectList(wrapper).stream()
                .collect(Collectors.toMap(CollegeMajorDO::getCollegeId, CollegeMajorDO::getCollegeName));
    }

    @Override
    public Map<Integer, String> campusMap() {
        QueryWrapper<CampusDO> wrapper= new QueryWrapper<>();
        return campusDAO.selectList(wrapper).stream().collect(Collectors.toMap(CampusDO::getCampusId, CampusDO::getName));
    }

    @Override
    public void preheatLessonNumber(String prefixKey) {
        redisGateway.removeKeyByPrefix("");
        List<LessonDO> allLesson = lessonGateway.getAllLesson();
        allLesson.stream().filter(l -> l.getClosed().equals(0)).forEach(lessonDO -> {
            // ????????????
            redisGateway.set("lesson:id:" + lessonDO.getLId(), lessonDO);
            redisGateway.set(prefixKey + lessonDO.getLId(), lessonDO.getRemainPeople());
        });
    }
}
