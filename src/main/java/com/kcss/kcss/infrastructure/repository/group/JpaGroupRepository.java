package com.kcss.kcss.infrastructure.repository.group;

import com.kcss.kcss.infrastructure.entity.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGroupRepository extends JpaRepository<GroupEntity, Long> { }
