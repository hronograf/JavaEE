package com.bigbook.app.auth.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer>, JpaSpecificationExecutor<PermissionEntity> {}
