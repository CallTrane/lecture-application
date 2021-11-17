package com.lecture.gateway;

import java.util.List;
import java.util.Optional;

/**
 * @className: StudentGateway
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 13:10
 */
public interface StudentGateway {

    /**
     * 获取学生所选课程
     *
     * @param stuId 学生学号
     * @return
     */
    Optional<List<Integer>> getLessonIdsByStuId(Long stuId);
}
