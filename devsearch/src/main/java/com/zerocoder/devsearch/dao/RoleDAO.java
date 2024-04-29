package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
