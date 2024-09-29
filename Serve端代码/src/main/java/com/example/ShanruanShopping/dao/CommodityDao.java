package com.example.ShanruanShopping.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ShanruanShopping.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 商品(Commodity)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface CommodityDao extends BaseMapper<Commodity> {

    @Select("SELECT COUNT(*) AS total_commodities FROM commodity;")
    long getTotalCommodities();
}