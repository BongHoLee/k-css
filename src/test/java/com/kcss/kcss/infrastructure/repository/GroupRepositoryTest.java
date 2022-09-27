package com.kcss.kcss.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.infrastructure.entity.group.GroupEntity;
import com.kcss.kcss.infrastructure.entity.group.condition.Condition;
import com.kcss.kcss.infrastructure.entity.group.condition.Key;
import com.kcss.kcss.infrastructure.entity.group.condition.Operator;
import com.kcss.kcss.infrastructure.entity.group.condition.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


@Sql("/data/schema.sql")
@Sql("/data/data.sql")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @DisplayName("1개 Condition을 가진 GroupEntity 영속화 테스트")
    void 단일_조건_그룹_영속화_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"))
        ));
        String description = "송금 결제";
        GroupEntity group = GroupEntity.builder()
                .description(description)
                .conditions(conditions)
                .build();

        GroupEntity save = groupRepository.save(group);
        Optional<GroupEntity> foundEntity = groupRepository.findById(save.getId());

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getConditions()).isEqualTo(conditions);
        assertThat(foundEntity.get().getDescription()).isEqualTo(description);
    }

    @DisplayName("2개 Condition을 가진 GroupEntity 영속화 테스트")
    void 다중_조건_그룹_영속화_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금")),
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("$region"))
        ));
        String description = "거주지역 외 송금 결제";
        GroupEntity group = GroupEntity.builder()
                .description(description)
                .conditions(conditions)
                .build();

        GroupEntity save = groupRepository.save(group);
        Optional<GroupEntity> foundEntity = groupRepository.findById(save.getId());

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getConditions()).isEqualTo(conditions);
        assertThat(foundEntity.get().getDescription()).isEqualTo(description);
    }

}