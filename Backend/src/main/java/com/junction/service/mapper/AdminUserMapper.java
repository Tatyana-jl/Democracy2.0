package com.junction.service.mapper;

import com.junction.domain.*;
import com.junction.service.dto.AdminUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminUser} and its DTO {@link AdminUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProblemMapper.class, RoleMapper.class})
public interface AdminUserMapper extends EntityMapper<AdminUserDTO, AdminUser> {

    @Mapping(source = "role.id", target = "roleId")
    AdminUserDTO toDto(AdminUser adminUser);

    @Mapping(target = "removeProblem", ignore = true)
    @Mapping(source = "roleId", target = "role")
    AdminUser toEntity(AdminUserDTO adminUserDTO);

    default AdminUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        return adminUser;
    }
}
