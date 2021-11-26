package com.lecture.domain.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: CampusDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:55
 */
@TableName("campus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusDO {

    /**
     * 校区id
     */
    @TableId(value = "campus_id", type = IdType.AUTO)
    private Integer campusId;

    /**
     * 校区名字
     */
    private String name;

    /**
     * 校区地址
     */
    private String address;
}
