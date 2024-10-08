package com.example.ShanruanShopping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ShanruanShopping.dao.KindDao;
import com.example.ShanruanShopping.entity.Kind;
import com.example.ShanruanShopping.service.KindService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * 类别(Kind)表服务实现类
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@Service("kindService")
public class KindServiceImpl extends ServiceImpl<KindDao, Kind> implements KindService {

}