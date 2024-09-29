package com.example.ShanruanShopping.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ShanruanShopping.entity.Rate;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Rate)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-05-14 14:14:12
 */
@Mapper
public interface RateDao extends BaseMapper<Rate> {

}