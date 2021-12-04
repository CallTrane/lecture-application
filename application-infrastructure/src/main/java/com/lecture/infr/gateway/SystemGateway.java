package com.lecture.infr.gateway;


import com.lecture.domain.entities.CollegeMajorDO;

import java.util.Map;

/**
 * @className: SystemGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/20 18:21
 */
public interface SystemGateway {
    Map<Integer, Map<Integer, CollegeMajorDO>> getCollegeMajorMap();

    Map<Integer, String> getCollegeMap();

    Map<Integer, String> campusMap();

    /**
     * 清除所有数据，并数据预热
     *
     * @param prefixKey
     */
    void preheatLessonNumber(String prefixKey);
}
