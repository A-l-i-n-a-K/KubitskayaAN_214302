package com.fitquest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum SignStatus {
    WAITING("Ожидание"),
    PASSED("Пройдено"),
    NOT_PASSED("Не пройдено"),
    ;

    private final String name;
}

