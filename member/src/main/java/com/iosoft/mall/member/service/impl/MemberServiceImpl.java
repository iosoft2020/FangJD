package com.iosoft.mall.member.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iosoft.mall.member.exception.PhoneNumExistException;
import com.iosoft.mall.member.exception.UserExistException;
import com.iosoft.mall.member.mapper.MemberMapper;
import com.iosoft.mall.member.pojo.Member;
import com.iosoft.mall.member.service.MemberLevelService;
import com.iosoft.mall.member.service.MemberService;
import com.iosoft.mall.member.vo.MemberLoginVo;
import com.iosoft.mall.member.vo.MemberRegisterVo;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public void register(MemberRegisterVo registerVo) {
        //1 检查电话号是否唯一
        checkPhoneUnique(registerVo.getPhone());
        //2 检查用户名是否唯一
        checkUserNameUnique(registerVo.getUsername());
        //3 该用户信息唯一，进行插入
        Member entity = new Member();
        //3.1 保存基本信息
        entity.setUsername(registerVo.getUsername());
        entity.setMobile(registerVo.getPhone());
        entity.setCreateTime(LocalDateTime.now());
        //3.2 使用加密保存密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(registerVo.getPassword());
        entity.setPassword(encodePassword);
        //3.3 设置会员默认等级
        //        //3.3.1 找到会员默认登记
        //        MemberLevel defaultLevel = memberLevelService.getOne(new QueryWrapper<MemberLevel>().eq("default_status", 1));
        //        //3.3.2 设置会员等级为默认
        //        entity.setLevelId(defaultLevel.getId());

        // 4 保存用户信息
        this.save(entity);
    }

    @Override
    public Member login(MemberLoginVo loginVo) {
        String username = loginVo.getUsername();
        //以用户名或电话号登录的进行查询
        Member entity = this
                .getOne(new QueryWrapper<Member>().eq("username", username).or().eq("mobile", username));
        if (entity != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(loginVo.getPassword(), entity.getPassword());
            if (matches) {
                entity.setPassword("");
                return entity;
            }
        }
        return null;
    }

    private void checkPhoneUnique(String phone) {
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", phone));
        if (count > 0) {
            throw new PhoneNumExistException();
        }
    }

    private void checkUserNameUnique(String userName) {
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("username", userName));
        if (count > 0) {
            throw new UserExistException();
        }
    }
}
