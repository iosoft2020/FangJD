package com.iosoft.mall.order.service.impl;

import com.iosoft.mall.order.pojo.Order;
import com.iosoft.mall.order.mapper.OrderMapper;
import com.iosoft.mall.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
