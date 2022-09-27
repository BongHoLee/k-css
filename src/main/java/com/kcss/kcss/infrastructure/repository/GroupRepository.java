package com.kcss.kcss.infrastructure.repository;

import com.kcss.kcss.infrastructure.entity.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
