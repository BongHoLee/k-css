package com.kcss.kcss.application.dto;


import static java.util.stream.Collectors.toList;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GroupDTO {

    @Getter
    @Setter
    @Builder
    public static class Info {
        private Long groupId;
        private String description;
        private List<ConditionDTO> condition;

        public static Info of(Group group) {
            return Info.builder()
                    .groupId(group.getId())
                    .description(group.getDescription())
                    .condition(group.getConditions().stream().map(ConditionDTO::of).collect(toList()))
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class RegisterRequest {
        @NotNull
        private Long groupId;
        @NotNull
        private String description;
        @NotNull
        private List<ConditionDTO> condition;

        public Group convert() {
            return Group.builder()
                    .id(groupId)
                    .conditions(condition.stream().map(ConditionDTO::convert).collect(toList()))
                    .description(description)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class ConditionDTO {
        @NotNull
        private String key;
        @NotNull
        private String operator;
        @NotNull
        private String value;

        public static ConditionDTO of(Condition condition) {
            return ConditionDTO.builder()
                    .key(condition.getKey().getKeyName())
                    .value(condition.getValue().getValue())
                    .operator(condition.getOperator().getOperatorName())
                    .build();
        }

        public Condition convert() {
            return Condition.builder()
                    .key(Key.of(key))
                    .value(new Value(value))
                    .operator(Operator.of(operator))
                    .build();
        }
    }
}
