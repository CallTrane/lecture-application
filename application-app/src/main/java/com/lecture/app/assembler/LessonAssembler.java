package com.lecture.app.assembler;

import com.lecture.component.utils.DataUtils;
import com.lecture.infr.query.LessonQuery;

import javax.xml.crypto.Data;

/**
 * @className: LessonAssembler
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 1:03
 */
public class LessonAssembler {
    public static String generateLessonListKey(LessonQuery lessonQuery) {
        String key = "lesson:";
        Integer majorId = lessonQuery.getMajorId();
        Integer campusId = lessonQuery.getCampusId();
        Integer required = lessonQuery.getRequired();
        if (DataUtils.isNotEmpty(majorId)) {
            key += ("major" + majorId);
        }
        if (DataUtils.isNotEmpty(campusId)) {
            key += ("campus" + campusId);
        }
        /*if (DataUtils.isNotEmpty(required)) {
            key += (required);
        }*/
        return key;
    }

    public static String generateStudentLessonKey(Long stuId) {
        return "student_lesson:" + stuId;
    }

    public static String generateLessonNumberKey(Integer lessonId) {
        return "number_of_lesson:" + lessonId;
    }

    public static String generateLessonKey(Integer lessonId) {
        return "lesson:" + lessonId;
    }

}
