package com.iosoft.mall.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iosoft.mall.auth.feign.fallback.MemberFallbackService;
import com.iosoft.mall.auth.vo.SocialUser;
import com.iosoft.mall.auth.vo.UserLoginVo;
import com.iosoft.mall.auth.vo.UserRegisterVo;
import com.iosoft.mall.common.utils.R;

@FeignClient(value = "member", fallback = MemberFallbackService.class)
public interface MemberFeignService {

    @RequestMapping("member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @RequestMapping("member/member/login")
    R login(@RequestBody UserLoginVo loginVo);

    @RequestMapping("member/member/oauth2/login")
    R login(@RequestBody SocialUser socialUser);
}
