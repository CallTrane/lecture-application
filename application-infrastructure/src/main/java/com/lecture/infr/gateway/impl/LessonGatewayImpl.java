package com.lecture.infr.gateway.impl;

import com.lecture.component.utils.DataUtils;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.LessonGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @classname: LessonGatewayImpl
 * @description: TODO
 * @date: 2021/11/26 17:54
 * @author: Carl
 */
@Service
public class LessonGatewayImpl implements LessonGateway {

    @Autowired
    LessonDAO lessonDAO;

    @Override
    public Optional<List<LessonDO>> getLessonsByIds(List<Integer> ids) {
        if (DataUtils.isEmpty(ids)) {
            return Optional.empty();
        }
        return Optional.ofNullable(lessonDAO.selectBatchIds(ids));
    }
}
