package com.example.ShanruanShopping.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.ShanruanShopping.entity.Administrator;
import com.example.ShanruanShopping.service.AdministratorService;
import com.example.ShanruanShopping.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员(Administrator)表控制层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@RestController
@RequestMapping("admin")
public class AdministratorController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private AdministratorService administratorService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.administratorService.getById(id));
    }


    /**
     * 新增数据
     *
     * @param administrator 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Administrator administrator) {
        return success(this.administratorService.save(administrator));
    }

    @PostMapping("login")
    public R login(@RequestBody Administrator administrator) {
        if ((administrator = administratorService.login(administrator.getName(), administrator.getPassword())) != null) {
            String token = JwtUtil.createToken(administrator);
            Map<String, Object> result = new HashMap<>();
            result.put("admin", administrator);
            result.put("token", token);
            return success(result);
        }
        return failed("用户名或密码错误");
    }
}