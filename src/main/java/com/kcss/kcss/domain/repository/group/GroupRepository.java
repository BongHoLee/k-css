package com.kcss.kcss.domain.repository.group;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Group save(Group group);
    void removeFor(Long id);
    Optional<Group> findById(Long id);
    List<Group> findAll();
    Statistics statisticsOf(Group group);
}
