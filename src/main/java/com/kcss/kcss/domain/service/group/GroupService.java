package com.kcss.kcss.domain.service.group;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import java.util.List;

public interface GroupService {
    Group register(Group group);
    Group findGroupOf(Long id);

    void remove(Long id);
    List<Group> findAllRegistered();
    Statistics statisticsOf(Long id);
}
