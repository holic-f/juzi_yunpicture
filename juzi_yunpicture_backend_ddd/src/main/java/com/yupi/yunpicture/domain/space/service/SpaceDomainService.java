package com.yupi.yunpicture.domain.space.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicture.domain.space.entity.Space;
import com.yupi.yunpicture.domain.user.entity.User;
import com.yupi.yunpicture.interfaces.dto.space.SpaceAddRequest;
import com.yupi.yunpicture.interfaces.dto.space.SpaceQueryRequest;
import com.yupi.yunpicture.interfaces.vo.space.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 35188
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2026-08-11 14:45:47
*/
public interface SpaceDomainService {

    /**
     * 获取查询对象
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 根据空间级别填充空间对象
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 校验空间权限
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);

}
