package com.example.demo.model.type;

public enum Lifestyle {
    SEDENTARY(1.2),
    INACTIVE(1.375),
    MOBILE(1.55),
    SUPER_MOBILE(1.7);

    private final double v;

    Lifestyle(double v) {
        this.v=v;
    }

    public static Lifestyle valueOfIgnoreCase(String name){
        for (Lifestyle value : values()) {
            if(value.name().equalsIgnoreCase(name)){
                return value;
            }
        }
        throw new IllegalArgumentException("Неизвесный тип хранилища");
    }

    public double getV() {
        return v;
    }
}
