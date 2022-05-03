package com.example.demo.model.type;

public enum Goal {
    UP_0_25(1.1),
    UP_0_50(1.2),
    SUPPORT(1),
    DOWN_0_25(0.9),
    DOWN_0_50(0.8);

    private final double i;

    public double getI() {
        return i;
    }

    Goal(double i) {
        this.i = i;
    }

    public static Goal valueOfIgnoreCase(String name) {
        for (Goal value : values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Неизвесный тип хранилища");
    }
}
