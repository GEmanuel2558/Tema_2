package sda_tema_2_spring.Tema_2.web.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TokenRequestDto extends TokenRequestWithOutUserNameDto implements Serializable {

    @NotNull(message = "The field username is missing")
    @Length(min = 1, message = "The field is empty")
    private String username;
    @NotNull
    @NotEmpty
    private List<Integer> listOfPermissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getListOfPermissions() {
        return listOfPermissions;
    }

    public void setListOfPermissions(List<Integer> listOfPermissions) {
        this.listOfPermissions = listOfPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TokenRequestDto that = (TokenRequestDto) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(listOfPermissions, that.listOfPermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, listOfPermissions);
    }

    @Override
    public String toString() {
        return "TokenRequestDto{" +
                "username='" + username + '\'' +
                ", listOfPermissions=" + listOfPermissions +
                '}';
    }
}
