package com.yupi.yunpicture.domain.space.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yunpicture.application.service.SpaceApplicationService;
import com.yupi.yunpicture.application.service.SpaceUserApplicationService;
import com.yupi.yunpicture.application.service.UserApplicationService;
import com.yupi.yunpicture.domain.space.entity.Space;
import com.yupi.yunpicture.domain.space.entity.SpaceUser;
import com.yupi.yunpicture.domain.space.service.SpaceUserDomainService;
import com.yupi.yunpicture.domain.space.valueobject.SpaceRoleEnum;
import com.yupi.yunpicture.domain.user.entity.User;
import com.yupi.yunpicture.infrastructure.exception.BusinessException;
import com.yupi.yunpicture.infrastructure.exception.ErrorCode;
import com.yupi.yunpicture.infrastructure.exception.ThrowUtils;
import com.yupi.yunpicture.infrastructure.mapper.SpaceUserMapper;
import com.yupi.yunpicture.interfaces.dto.spaceuser.SpaceUserAddRequest;
import com.yupi.yunpicture.interfaces.dto.spaceuser.SpaceUserQueryRequest;
import com.yupi.yunpicture.interfaces.vo.space.SpaceUserVO;
import com.yupi.yunpicture.interfaces.vo.space.SpaceVO;
import com.yupi.yunpicture.interfaces.vo.user.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 35188
* @description 针对表【space_user(空间用户关联)】的数据库操作Service实现
* @createDate 2026-08-22 15:56:01
*/
@Service
public class SpaceUserDomainServiceImpl implements SpaceUserDomainService {

    @Override
    public QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest) {
        QueryWrapper<SpaceUser> queryWrapper = new QueryWrapper<>();
        if (spaceUserQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = spaceUserQueryRequest.getId();
        Long spaceId = spaceUserQueryRequest.getSpaceId();
        Long userId = spaceUserQueryRequest.getUserId();
        String spaceRole = spaceUserQueryRequest.getSpaceRole();
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(spaceId), "spaceId", spaceId);
        queryWrapper.eq(ObjUtil.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjUtil.isNotEmpty(spaceRole), "spaceRole", spaceRole);
        return queryWrapper;
    }
}

