package com.github.monosoul.fortuneteller.web.model;

import java.util.Objects;

public final class FortuneRequestJSON {
    private final String name;
    private final String zodiacSign;
    private final Short age;

    public FortuneRequestJSON(String name, String zodiacSign, Short age) {
        this.name = name;
        this.zodiacSign = zodiacSign;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public Short getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FortuneRequestJSON that = (FortuneRequestJSON) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(zodiacSign, that.zodiacSign) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, zodiacSign, age);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FortuneRequestJSON{");
        sb.append("name='").append(name).append('\'');
        sb.append(", zodiacSign='").append(zodiacSign).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
