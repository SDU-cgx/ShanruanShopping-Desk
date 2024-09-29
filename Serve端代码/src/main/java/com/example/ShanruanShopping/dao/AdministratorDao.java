package com.example.ShanruanShopping.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ShanruanShopping.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 管理员(Administrator)表数据库访问层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Mapper
public interface AdministratorDao extends BaseMapper<Administrator> {
}