package com.iosoft.mall.order.service.impl;

import com.iosoft.mall.order.pojo.OrderOperateHistory;
import com.iosoft.mall.order.mapper.OrderOperateHistoryMapper;
import com.iosoft.mall.order.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
