package com.kcss.kcss.application.controller;


import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.GroupDTO;
import com.kcss.kcss.application.dto.GroupDTO.ConditionDTO;
import com.kcss.kcss.application.dto.GroupDTO.Info;
import com.kcss.kcss.application.dto.GroupDTO.RegisterRequest;
import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import com.kcss.kcss.domain.service.group.GroupService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(GroupController.class)
@ActiveProfiles("test")
class GroupControllerTest {
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
    @DisplayName("그룹 생성 API 호출 테스트")
    void Group_생성_API_호출_테스트() throws Exception {
        GroupDTO.RegisterRequest registerRequest = RegisterRequest.builder()
                .groupId(1L)
                .condition(Arrays.asList(
                        ConditionDTO.builder().key("region").operator("equals").value("제주").build(),
                        ConditionDTO.builder().key("amount").operator("between").value("[1000, 2000]").build()))
                .description("제주지역에서 1000 ~ 2000 사이 결제")
                .build();

        String requestContents = objectMapper.writeValueAsString(registerRequest);
        String responseContents = objectMapper.writeValueAsString(BaseResponse.success());

        mockMvc.perform(post("/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestContents))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseContents))
        ;
    }

    @Test
    @DisplayName("그룹 생성 데이터 누락 시 API 호출 예외 테스트")
    void Group_생성_API_예외_테스트() throws Exception {
        GroupDTO.RegisterRequest registerRequest = RegisterRequest.builder()
                .groupId(1L)
                .condition(Arrays.asList(ConditionDTO.builder().key("not_valid").operator("equals").value("제주").build()))
                .description("제주지역에서 결제")
                .build();

        String requestContents = objectMapper.writeValueAsString(registerRequest);
        String responseContents = objectMapper.writeValueAsString(BaseResponse.errorOf(DomainErrorCode.NOT_SUPPORT_KEY));

        mockMvc.perform(post("/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestContents))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(responseContents))
        ;
    }


    @Test
    @DisplayName("등록된 특정 그룹 조회 API 호출 테스트")
    void Group_조회_API_호출_테스트() throws Exception {
        Group group = Group.builder()
                .id(1L)
                .conditions(Arrays.asList(
                        Condition.builder().key(Key.REGION).operator(Operator.EQUALS).value(new Value("제주")).build(),
                        Condition.builder().key(Key.AMOUNT).operator(Operator.BETWEEN).value(new Value("[10000, 30000]")).build()
                )).description("제주 지역에서 10000 ~ 30000 사이 결제")
                .build();

        given(groupService.findGroupOf(any()))
                .willReturn(group);


        String response = objectMapper.writeValueAsString(Arrays.asList(GroupDTO.Info.of(group)));
        mockMvc.perform(get("/group")
                        .param("id", "1")
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response))
        ;
    }

    @Test
    @DisplayName("등록된 모든 그룹 조회 API 호출 테스트")
    void 모든GROUP_조회_API_호출_테스트() throws Exception {
        Group group1 = Group.builder().id(1L).conditions(Arrays.asList(
                        Condition.builder().key(Key.REGION).operator(Operator.EQUALS).value(new Value("제주")).build(),
                        Condition.builder().key(Key.AMOUNT).operator(Operator.BETWEEN).value(new Value("[10000, 30000]"))
                                .build())).description("제주 지역에서 10000 ~ 30000 사이 결제").build();

        Group group2 = Group.builder().id(2L).conditions(
                Arrays.asList(Condition.builder().key(Key.REGION).operator(Operator.EQUALS).value(new Value("제주"))
                        .build())).description("제주 지역 결제").build();

        List<Group> groups = Arrays.asList(group1, group2);

        given(groupService.findAllRegistered())
                .willReturn(groups);


        String response = objectMapper.writeValueAsString(groups.stream().map(Info::of).collect(toList()));
        mockMvc.perform(get("/group")
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response))
        ;
    }

}