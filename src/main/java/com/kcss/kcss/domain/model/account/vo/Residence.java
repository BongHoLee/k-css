package com.kcss.kcss.domain.model.account.vo;


import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import java.util.Arrays;

public enum Residence {
    SEOUL("서울"), BUSAN("부산"), DAEGU("대구"), INCHEON("인천"), KWANGJU("광주"),
    DAEJEON("대전"), ULSAN("울산"), SEJONG("세종"), GYEONGGI("경기"), KANGWON("강원"),
    CHUNGBUK("충북"), CHUNGNAM("충남"), JEONBUK("전북"), JEONNAM("전남"), KYEONGBUK("경북"),
    KYEONGNAM("경남"), JEJU("제주")
    ;

    private final String residenceName;

    Residence(String residenceName) {
        this.residenceName = residenceName;
    }

    public String residenceName() {
        return this.residenceName;
    }

    public static Residence of(String residenceName) {
        return Arrays.stream(Residence.values())
                .filter(residence -> residence.residenceName().equals(residenceName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support residence : " + residenceName, DomainErrorCode.NOT_SUPPORT_RESIDENCE));
    }
}
