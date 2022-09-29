package com.kcss.kcss.application.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kcss.kcss.application.dto.StatisticsDTO;
import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import com.kcss.kcss.domain.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StatisticsController.class)
@ActiveProfiles("test")
class StatisticsControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("그룹 통계 조회 API 호출 테스트")
    void 그룹_통계_조회_API_호출_테스트() throws Exception {
        Statistics statistics = Statistics.builder()
                .group(Group.builder().id(1L).build())
                .count(10L)
                .totalAmount(1000.0)
                .avgAmount(250.0)
                .maxAmount(600.0)
                .minAmount(100.0)
                .build();

        given(groupService.statisticsOf(any()))
                .willReturn(statistics);

        String response = objectMapper.writeValueAsString(StatisticsDTO.of(statistics));
        mockMvc.perform(get("/statistics")
                        .param("groupId", "1")
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response))
        ;
    }
}