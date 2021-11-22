package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.eneities.CampusDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: CampusDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:58
 */
@Mapper
public interface CampusDAO extends BaseMapper<CampusDO> {
}
