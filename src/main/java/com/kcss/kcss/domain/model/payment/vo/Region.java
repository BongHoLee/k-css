package com.kcss.kcss.domain.model.payment.vo;


import java.util.Arrays;

public enum Region {

    SEOUL("서울"), BUSAN("부산"), DAEGU("대구"), INCHEON("인천"), KWANGJU("광주"),
    DAEJEON("대전"), ULSAN("울산"), SEJONG("세종"), GYEONGGI("경기"), KANGWON("강원"),
    CHUNGBUK("충북"), CHUNGNAM("충남"), JEONBUK("전북"), JEONNAM("전남"), KYEONGBUK("경북"),
    KYEONGNAM("경남"), JEJU("제주")
    ;

    private final String regionName;

    Region(String regionName) {
        this.regionName = regionName;
    }

    public String regionName() {
        return this.regionName;
    }

    public static Region of(String regionName) {
        return Arrays.stream(Region.values())
                .filter(region -> region.regionName().equals(regionName))
                .findFirst()
                .orElseThrow();
    }
}
