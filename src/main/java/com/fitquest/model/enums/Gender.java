package com.fitquest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    M("Муж."),
    W("Жен."),
    ;

    private final String name;
}