package com.bigbook.app.auth.permission;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
@Data
public class PermissionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", unique = true)
    private Permission permission;

    public static PermissionEntity from(Permission permission) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setPermission(permission);
        return permissionEntity;
    }

}
