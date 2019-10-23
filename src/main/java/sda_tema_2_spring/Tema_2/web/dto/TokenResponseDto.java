package sda_tema_2_spring.Tema_2.web.dto;

import java.io.Serializable;
import java.util.Objects;

public class TokenResponseDto implements Serializable {

    private final String token;

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenResponseDto that = (TokenResponseDto) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
