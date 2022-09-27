package com.kcss.kcss.infrastructure.entity.account;

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
public class Residence {
    private static final Set<String> RESIDENCES = new HashSet<>(
            Arrays.asList(
                    "서울", "부산", "대구", "인천", "광주",
                    "대전", "울산", "세종", "경기", "강원",
                    "충북", "충남", "전북", "전남", "경북",
                    "경남", "제주"
            ));

    private String residence;
    public Residence(String residence) {
        validation(residence);
        this.residence = residence;
    }

    private void validation(String residence) {
        if (residence == null) {
            log.error("residence cannot be null");
            throw new IllegalArgumentException("residence cannot be null");
        }

        if (!RESIDENCES.contains(residence)) {
            log.error("not support residence");
            throw new IllegalArgumentException("not support residence : " + residence);
        }
    }
}
