package com.yupi.yun_picture.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRoleEnum {

    USER("用户", "user"),
    ADMIN("管理员", "admin");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value){
        if (ObjUtil.isAllEmpty( value)){
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()){
            if (anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

}
