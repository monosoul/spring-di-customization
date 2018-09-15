package com.github.monosoul.fortuneteller.web.model;

import java.util.Objects;

public final class FortuneResponseJSON {
    private final String message;

    public FortuneResponseJSON(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FortuneResponseJSON that = (FortuneResponseJSON) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FortuneResponseJSON{");
        sb.append("message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
