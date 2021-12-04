package com.lecture.app.assembler;

import com.lecture.component.utils.DataUtils;
import com.lecture.infr.query.LessonQuery;

/**
 * @className: LessonAssembler
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 1:03
 */
public class LessonAssembler {

    public static final String LESSON_PREFIX_KEY = "lesson:";

    public static String generateLessonListKey(LessonQuery lessonQuery) {
        String key = LESSON_PREFIX_KEY;
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
        String key = LESSON_PREFIX_KEY + "student_lesson";
        return key + stuId;
    }

    public static String generateLessonNumberKey(Integer lessonId) {
        String key = LESSON_PREFIX_KEY + "lesson_remain";
        return key + lessonId;
    }

    public static String generateLessonKey(Integer lessonId) {
        String key = LESSON_PREFIX_KEY + "id";
        return key + lessonId;
    }

}
