package com.yupi.yunpicture.domain.space.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicture.domain.space.entity.SpaceUser;
import com.yupi.yunpicture.interfaces.dto.spaceuser.SpaceUserAddRequest;
import com.yupi.yunpicture.interfaces.dto.spaceuser.SpaceUserQueryRequest;
import com.yupi.yunpicture.interfaces.vo.space.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 35188
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2026-08-22 15:56:01
*/
public interface SpaceUserDomainService {

    /**
     * 获取查询对象
     * @param spaceUserQueryRequest
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

}
