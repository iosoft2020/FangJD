package com.iosoft.mall.auth.feign.fallback;

import org.springframework.stereotype.Service;

import com.iosoft.mall.auth.constant.BizCodeEnum;
import com.iosoft.mall.auth.feign.MemberFeignService;
import com.iosoft.mall.auth.vo.SocialUser;
import com.iosoft.mall.auth.vo.UserLoginVo;
import com.iosoft.mall.auth.vo.UserRegisterVo;
import com.iosoft.mall.common.utils.R;

@Service
public class MemberFallbackService implements MemberFeignService {
    @Override
    public R register(UserRegisterVo registerVo) {
        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
    }

    @Override
    public R login(UserLoginVo loginVo) {
        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
    }

    @Override
    public R login(SocialUser socialUser) {
        return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
    }
}
