package com.monolith.platform.learning.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionEnum {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    READ("READ"),
    DELETE("DELETE");

    private final String valor;
}
