package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
