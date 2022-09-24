package com.kcss.kcss.infrastructure.entity.payment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    private static final Set<String> REGIONS = new HashSet<>(
            Arrays.asList(
                    "서울", "부산", "대구", "인천", "광주",
                    "대전", "울산", "세종", "경기", "강원",
                    "충북", "충남", "전북", "전남", "경북",
                    "경남", "제주"
            ));


    private String region;

    public Region(String region) {
        validation(region);
        this.region = region;
    }

    private void validation(String region) {
        if (region == null) {
            log.error("region cannot be null");
            throw new IllegalArgumentException("region cannot be null");
        }

        if (!REGIONS.contains(region)) {
            log.error("not support region");
            throw new IllegalArgumentException("not support region : " + region);
        }
    }
}
