package com.iosoft.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iosoft.mall.member.pojo.Member;
import com.iosoft.mall.member.vo.MemberLoginVo;
import com.iosoft.mall.member.vo.MemberRegisterVo;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
public interface MemberService extends IService<Member> {

    void register(MemberRegisterVo registerVo);

    Member login(MemberLoginVo loginVo);

}
