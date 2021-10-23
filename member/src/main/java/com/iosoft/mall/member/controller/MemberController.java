package com.iosoft.mall.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iosoft.mall.common.exception.BizCodeEnum;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.member.exception.PhoneNumExistException;
import com.iosoft.mall.member.exception.UserExistException;
import com.iosoft.mall.member.feign.CouponFeignService;
import com.iosoft.mall.member.pojo.Member;
import com.iosoft.mall.member.service.MemberService;
import com.iosoft.mall.member.vo.MemberLoginVo;
import com.iosoft.mall.member.vo.MemberRegisterVo;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/member/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    @RequestMapping("/coupons")
    public R test() {
        Member member = new Member();
        member.setNickname("张三");

        return R.ok().put("member", member).put("coupons", couponFeignService.memberCoupons().get("coupons"));

    }

    /**
     * 注册会员
     * @return
     */
    @RequestMapping("/register")
    public R register(@RequestBody MemberRegisterVo registerVo) {
        try {
            memberService.register(registerVo);
        } catch (UserExistException userException) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        } catch (PhoneNumExistException phoneException) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @RequestMapping("/login")
    public R login(@RequestBody MemberLoginVo loginVo) {
        Member entity=memberService.login(loginVo);
        if (entity!=null){
            return R.ok().put("memberEntity",entity);
        }else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(), BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
        }
    }
}
