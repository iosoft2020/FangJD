package com.iosoft.mall.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iosoft.mall.auth.constant.AuthServerConstant;
import com.iosoft.mall.auth.feign.MemberFeignService;
import com.iosoft.mall.auth.vo.UserLoginVo;
import com.iosoft.mall.auth.vo.UserRegisterVo;
import com.iosoft.mall.common.utils.R;
import com.iosoft.mall.common.vo.MemberResponseVo;

@Controller
public class LoginController {

    //    @Autowired
    //    private ThirdPartFeignService thirdPartFeignService;
    //
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;

    @RequestMapping("/login.html")
    public String loginPage(HttpSession session) {
        if (session.getAttribute(AuthServerConstant.LOGIN_USER) != null) {
            return "redirect:http://iosoftmall.com/";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session) {
        R r = memberFeignService.login(vo);
        if (r.getCode() == 0) {
            String jsonString = JSON.toJSONString(r.get("memberEntity"));
            MemberResponseVo memberResponseVo = JSON.parseObject(jsonString, new TypeReference<MemberResponseVo>() {
            });
            session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
            return "redirect:http://iosoftmall.com/";
        } else {
            String msg = (String) r.get("msg");
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", msg);
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.iosoftmall.com/login.html";
        }
    }

    //    @GetMapping("/sms/sendCode")
    //    @ResponseBody
    //    public R sendCode(@RequestParam("phone") String phone) {
    //        //接口防刷,在redis中缓存phone-code
    //        ValueOperations<String, String> ops = redisTemplate.opsForValue();
    //        String prePhone = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
    //        String v = ops.get(prePhone);
    //        if (!StringUtils.isEmpty(v)) {
    //            long pre = Long.parseLong(v.split("_")[1]);
    //            //如果存储的时间小于60s，说明60s内发送过验证码
    //            if (System.currentTimeMillis() - pre < 60000) {
    //                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
    //            }
    //        }
    //        //如果存在的话，删除之前的验证码
    //        redisTemplate.delete(prePhone);
    //        //获取到6位数字的验证码
    //        String code = String.valueOf((int) ((Math.random() + 1) * 100000));
    //        //在redis中进行存储并设置过期时间
    //        ops.set(prePhone, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
    //        thirdPartFeignService.sendCode(phone, code);
    //        return R.ok();
    //    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo registerVo, BindingResult result, RedirectAttributes attributes) {
        Map<String, String> errors = new HashMap<>();
        //1.判断校验是否通过
        if (result.hasErrors()) {
            //1.1 如果校验不通过，则封装校验结果
            errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField,
                            FieldError::getDefaultMessage));
            //1.2 将错误信息封装到session中
            attributes.addFlashAttribute("errors", errors);
            //1.2 重定向到注册页
            return "redirect:http://auth.iosoftmall.com/register.html";
        } else {
            //            //2.若JSR303校验通过
            //            //判断验证码是否正确
            //            String code = redisTemplate.opsForValue()
            //                    .get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
            //2.1 如果对应手机的验证码不为空且与提交上的相等-》验证码正确
            //            if (!StringUtils.isEmpty(code) && registerVo.getCode().equals(code.split("_")[0])) {
            //2.1.1 使得验证后的验证码失效
            //                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());

            //2.1.2 远程调用会员服务注册
            R r = memberFeignService.register(registerVo);
            if (r.getCode() == 0) {
                //调用成功，重定向登录页
                return "redirect:http://auth.iosoftmall.com/login.html";
            } else {
                //调用失败，返回注册页并显示错误信息
                String msg = (String) r.get("msg");
                errors.put("msg", msg);
                attributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.iosoftmall.com/register.html";
            }
            //            } else {
            //                //2.2 验证码错误
            //                errors.put("code", "验证码错误");
            //                attributes.addFlashAttribute("errors", errors);
            //                return "redirect:http://auth.gulimall.com/reg.html";
            //            }
        }
    }
}
