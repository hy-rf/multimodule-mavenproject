package com.backend.common;

import java.util.List;

public class JwtData {
    private Long userId;
    private List<Long> roleIds;

    public JwtData(Long userId, List<Long> roleIds) {
        this.userId = userId;
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
