package com.lecture.infr.gateway;

import java.util.List;

/**
 * @classname: StudentGateway
 * @description: TODO
 * @date: 2021/11/26 17:46
 * @author: Carl
 */

public interface StudentGateway {

    /**
     * 查询所有
     *
     * @param studentId
     * @return
     */
    List<Integer> getLessonIds(Long studentId);
}
