package com.kcss.kcss.application.service.group;

import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import com.kcss.kcss.domain.repository.group.GroupRepository;
import com.kcss.kcss.domain.service.group.GroupService;
import com.kcss.kcss.global.error.BusinessException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Group register(Group group) {
        return repository.save(group);
    }

    @Override
    public Group findGroupOf(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ApplicationErrorCode.NO_CONTENT));
    }

    @Override
    public void remove(Long id) {
        repository.removeFor(id);
    }

    @Override
    public List<Group> findAllRegistered() {
        List<Group> groupList = repository.findAll();
        if (groupList.isEmpty()) {
            throw new BusinessException(ApplicationErrorCode.NO_CONTENT);
        }
        return groupList;
    }

    @Override
    public Statistics statisticsOf(Long id) {
        return repository.statisticsOf(id);
    }

}
