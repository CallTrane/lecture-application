package com.lecture.app.vo;

import com.lecture.domain.entities.LessonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className: LessonVO
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/5 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonVO implements Serializable {
    private List<LessonDO> data;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer pageCount;
}
