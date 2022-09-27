package com.kcss.kcss.domain.model.group;

import com.kcss.kcss.domain.model.group.vo.Condition;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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
