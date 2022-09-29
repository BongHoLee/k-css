package com.kcss.kcss.domain.model.group;

import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.global.error.BusinessException;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@EqualsAndHashCode
@Slf4j
public class Group {
    private final Long id;
    private final List<Condition> conditions;
    private final String description;

    @Builder
    public Group(Long id, List<Condition> conditions, String description) {
        this.id = id;
        this.conditions = conditions;
        this.description = description;
    }
}
