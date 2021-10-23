package com.iosoft.mall.order.service.impl;

import com.iosoft.mall.order.pojo.OrderItem;
import com.iosoft.mall.order.mapper.OrderItemMapper;
import com.iosoft.mall.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
