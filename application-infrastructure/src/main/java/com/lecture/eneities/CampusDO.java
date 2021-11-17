package com.lecture.eneities;

import lombok.Data;

/**
 * @className: CampusDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:55
 */
@Data
public class CampusDO {

    /**
     * 校区id
     */
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
