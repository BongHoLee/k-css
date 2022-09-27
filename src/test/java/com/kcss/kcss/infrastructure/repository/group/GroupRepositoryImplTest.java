package com.kcss.kcss.infrastructure.repository.group;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import com.kcss.kcss.infrastructure.entity.group.vo.QslCondition;
import com.kcss.kcss.infrastructure.entity.group.vo.QslKey;
import com.kcss.kcss.infrastructure.entity.group.vo.QslOperator;
import com.kcss.kcss.infrastructure.entity.group.vo.QslValue;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
class GroupRepositoryImplTest {

    @Autowired
    private GroupRepositoryImpl groupRepository;

    @Autowired
    private JPAQueryFactory factory;

    @Test
    @DisplayName("1개 Condition을 가진 GroupEntity 영속화 테스트")
    void 단일_조건_그룹_영속화_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"))
        ));
        String description = "송금 결제";
        Group group = Group.builder()
                .description(description)
                .conditions(conditions)
                .build();

        Group saved = groupRepository.save(group);
        Optional<Group> foundGroup = groupRepository.findById(saved.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getConditions()).isEqualTo(conditions);
        assertThat(foundGroup.get().getDescription()).isEqualTo(description);
    }

    @DisplayName("2개 Condition을 가진 GroupEntity 영속화 테스트")
    @Test
    void 다중_조건_그룹_영속화_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금")),
                new Condition(Key.RESIDENCE, Operator.IN, new Value("$region"))
        ));
        String description = "거주지역 외 송금 결제";
        Group group = Group.builder()
                .description(description)
                .conditions(conditions)
                .build();

        Group saved = groupRepository.save(group);
        Optional<Group> foundGroup = groupRepository.findById(saved.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getConditions()).isEqualTo(conditions);
        assertThat(foundGroup.get().getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("EQUALS and NOT_EQUALS 조건 적용 정상 통계 테스트")
    void 결제자의_거주지역외_송금() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금")),
                new Condition(Key.RESIDENCE, Operator.NOT_EQUALS, new Value("$region"))
        ));
        Statistics statistics = statisticsProcessByConditions(conditions);

        assertThat(statistics.getCount()).isEqualTo(37);
    }

    @Test
    @DisplayName("IN and EQUALS and BETWEEN 조건 적용 정상 통계 처리 테스트")
    void 서울_경기_제주_경남_30대에서_60대_패션() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.REGION, Operator.IN, new Value("[서울, 경기, 제주, 경남]")),
                new Condition(Key.ITEM_CATEGORY, Operator.EQUALS, new Value("패션")),
                new Condition(Key.AGE, Operator.BETWEEN, new Value("[30, 60]"))
        ));

        Statistics statistics = statisticsProcessByConditions(conditions);

        assertThat(statistics.getCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("BETWEEN and EQUALS 조건 적용 정상 통계 처리 테스트")
    void 카드구매_100000이상_999999이하_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.AMOUNT, Operator.BETWEEN, new Value("[100000, 999999]")),
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("카드"))
        ));

        Statistics statistics = statisticsProcessByConditions(conditions);

        assertThat(statistics.getCount()).isEqualTo(46);

    }

    private Statistics statisticsProcessByConditions(List<Condition> conditions) {
        Group group = Group.builder()
                .id(1L)
                .conditions(conditions)
                .description("desc")
                .build();

        return groupRepository.statisticsOf(group);
    }

}

