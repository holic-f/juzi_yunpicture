package com.yupi.yun_picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yun_picture.model.dto.space.SpaceAddRequest;
import com.yupi.yun_picture.model.dto.space.SpaceQueryRequest;
import com.yupi.yun_picture.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yun_picture.model.entity.User;
import com.yupi.yun_picture.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 35188
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2026-08-11 14:45:47
*/
public interface SpaceService extends IService<Space> {

    /**
     * 创建空间
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 校验空间
     * @param space
     */
    void validSpace(Space space, boolean add);

    /**
     * 获取空间包装类（单条）
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间包装类（分页）
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

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
