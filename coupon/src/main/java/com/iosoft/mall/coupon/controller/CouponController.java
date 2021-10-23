package com.iosoft.mall.coupon.controller;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.coupon.common.utils.R;
import com.iosoft.mall.coupon.pojo.Coupon;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RefreshScope
@RestController
@RequestMapping("/coupon/coupon")
public class CouponController {

	@RequestMapping("/member/list")
	public R membercoupons() {
		Coupon coupon = new Coupon();
		coupon.setCouponName("满一百减十");
		return R.ok().put("coupons", Arrays.asList(coupon));
	}

	@Value("${coupon.user.name}")
	private String name;
	@RequestMapping("/test")
	public R test() {
		return R.ok().put("name",name);
	}
}

