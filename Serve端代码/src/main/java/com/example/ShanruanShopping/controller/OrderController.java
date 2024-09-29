package com.example.ShanruanShopping.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ShanruanShopping.entity.*;
import com.example.ShanruanShopping.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ShanruanShopping.dao.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单(Order)表控制层
 *
 * @author bwmgd
 * @since 2021-04-25 09:50:11
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private MiddleCountService middleCountService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private SellService sellService;
    @Resource
    private RateDao rateDao;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.orderService.getById(id));
    }

    @GetMapping("user/{userId}")
    public R selectByUserID(@RequestParam("page") long page, @PathVariable Serializable userId) {
        return success(this.orderService.page(new Page<>(page, 10),
                new LambdaQueryWrapper<Orders>()
                        .eq(Orders::getUserId, userId)
                        .orderByDesc(Orders::getId)
        ));
    }

    @GetMapping("shop/{shopId}")
    public R selectByShopID(@RequestParam("page") long page, @PathVariable Serializable shopId) {
        return success(this.orderService.page(new Page<>(page, 10),
                new LambdaQueryWrapper<Orders>().eq(Orders::getShopId, shopId).between(Orders::getStatus,2,8).orderByDesc(Orders::getId)));
    }

    @GetMapping
    public R selectAll(@RequestParam("page") long page){
        return success(this.orderService.pageByStatus(new Page<>(page, 10),8));
    }

    /**
     * 修改数据
     *
     * @param orders 实体对象
     * @return 修改结果
     */
    @PostMapping("update")
    public R update(@RequestBody Orders orders) {
        if (orders.getCanReturn() == 1) {
            if (orders.getStatus() == 5 || orders.getStatus() == 6) {
                return failed("支付超过24h,无法退货");
            }
        }
        else if (orders.getStatus() == 6) {  // 如果是退货成功，需要恢复用户的钱
            MiddleCount middleCount = middleCountService.getMiddleCountByOrderId(orders.getId());
            User user = userService.getById(orders.getUserId());
            user.setWallet(user.getWallet() + middleCount.getAmount());
            userService.updateById(user);
            middleCountService.removeById(middleCount.getId());
        }
        if (orders.getStatus() == 1 || orders.getStatus() == 6) { // 取消订单和退货成功，恢复订单数量
            Commodity commodity = commodityService.getById(orders.getCommodityId());
            commodity.setInventory(commodity.getInventory() + orders.getQuantity());
            commodityService.updateById(commodity);
        }
        if(orders.getStatus() == 9 ){ // 完成订单，状态是9，将钱转移到商家账户
            User user = userService.getById(orders.getUserId());
            MiddleCount middleCount = middleCountService.getMiddleCountByOrderId(orders.getId());
            user.setPoints(user.getPoints() + middleCount.getAmount());
            userService.updateById(user);
            Sell sell = sellService.getById(middleCount.getSellerId());
            Integer level = sell.getLevel();
            sell.setWallet(sell.getWallet() + middleCount.getAmount() * (1-rateDao.selectById(level).getRate()));
            sellService.updateById(sell);
            middleCountService.removeById(middleCount.getId());
            System.out.println("运行成功");
        }
        this.orderService.updateEndTimeById(orders.getId(),orders.getEndTime());
        return success(this.orderService.updateStatusById(orders.getId(),orders.getStatus()));
    }

    @PostMapping("pay")
    public R payOrders(@RequestBody List<Orders> orders) {
        User user = userService.getById(orders.get(0).getUserId());
        double sum = orders.stream().mapToDouble(Orders::getAmount).sum();
        if (user.getWallet() < sum) {
            return failed("余额不足");
        }
        for (Orders order : orders) {
            MiddleCount middleCount = new MiddleCount();
            middleCount.setOrderId(order.getId());
            middleCount.setUserId(user.getId());
            middleCount.setAmount(order.getAmount());
            middleCount.setSellerId(shopService.getById(order.getShopId()).getSellerId());
            middleCountService.save(middleCount);
            user.setWallet(user.getWallet() - order.getAmount());
            this.orderService.updateStatusById(order.getId(), 2);
            this.orderService.updateCanReturnById(order.getId(), 0);
            this.orderService.updateCreTimeById(order.getId(), order.getCreatTime());

        }
        return success(userService.updateById(user));
    }

    @PostMapping("delete")
    public R delete(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        return success(this.orderService.deleteOrderById(id));
    }

    @GetMapping("Money")
    public R money() {
        return success(this.orderService.getMoneyByOrder());
    }

    @GetMapping("orderNum")
    public R orderNum() {
        return success(this.orderService.getNumByOrder());
    }

    @GetMapping("shopMoney")
    public R getShopMoney() {
        return success(this.orderService.getShopMoney());
    }

    @GetMapping("shopOrderNum")
    public R getShopOrder() {
        return success(this.orderService.getOrderShopNum());
    }

    @GetMapping("TimeOrder")
    public R getTimeOrder() {
        return success(this.orderService.getTimeOrder());
    }
}