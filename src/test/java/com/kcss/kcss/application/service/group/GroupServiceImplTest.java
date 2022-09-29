package com.kcss.kcss.application.service.group;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
class GroupServiceImplTest {

    @Autowired
    GroupServiceImpl groupService;

    @Test
    @DisplayName("1개 Condition을 가진 Group 저장")
    void 단일_조건_그룹_저장_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"))
        ));
        String description = "송금 결제";
        Group group = Group.builder()
                .id(1L)
                .description(description)
                .conditions(conditions)
                .build();

        Group saved = groupService.register(group);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getConditions()).isEqualTo(conditions);
    }


    @Test
    @DisplayName("2개 Condition을 가진 GroupEntity 영속화 테스트")
    void 다중_조건_그룹_영속화_테스트() {
        List<Condition> conditions = new ArrayList<>(Arrays.asList(
                new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금")),
                new Condition(Key.RESIDENCE, Operator.IN, new Value("$region"))
        ));
        String description = "거주지역 외 송금 결제";
        Group group = Group.builder()
                .id(1L)
                .description(description)
                .conditions(conditions)
                .build();

        Group saved = groupService.register(group);

        assertThat(saved.getConditions()).isEqualTo(conditions);
        assertThat(saved.getDescription()).isEqualTo(description);
    }


    @Test
    @DisplayName("모든 그룹 조회 테스트")
    void 모든_그룹_조회_테스트() {

        Group group1 = Group.builder()
                .id(1L)
                .description("송금 결제")
                .conditions( new ArrayList<>(Arrays.asList(
                        new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"))
                ))).build();

        Group group2 = Group.builder()
                .id(2L)
                .description("거주지역 외 송금 결제")
                .conditions(new ArrayList<>(Arrays.asList(
                        new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금")),
                        new Condition(Key.RESIDENCE, Operator.IN, new Value("$region")))))
                .build();

        Group firstSaved = groupService.register(group1);
        Group secondSaved = groupService.register(group2);

        List<Group> all = groupService.findAllRegistered();

        assertThat(all).hasSize(2);
        assertThat(all.get(0)).isEqualTo(firstSaved);
        assertThat(all.get(1)).isEqualTo(secondSaved);
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
        groupService.register(group);
        return groupService.statisticsOf(group.getId());
    }
}