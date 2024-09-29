package com.example.ShanruanShopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ShanruanShopping.dao.AdministratorDao;
import com.example.ShanruanShopping.entity.Administrator;
import com.example.ShanruanShopping.service.AdministratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员(Administrator)表服务实现类
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@Service("administratorService")
public class AdministratorServiceImpl extends ServiceImpl<AdministratorDao, Administrator> implements AdministratorService {
    @Override
    public Administrator login(Serializable name, Serializable password) {
        Map<SFunction<Administrator, ?>, Serializable> map = new HashMap<>();
        map.put(Administrator::getName, name);
        map.put(Administrator::getPassword, password);
        return getOne(new LambdaQueryWrapper<Administrator>().allEq(map));
    }
}