package sda_tema_2_spring.Tema_2.business.service;

import org.hibernate.validator.constraints.Length;
import sda_tema_2_spring.Tema_2.data.entity.PermissionEntity;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;
import sda_tema_2_spring.Tema_2.web.dto.TokenRequestDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ICredentialsService {

    Optional<UserEntity> findUserByToken(@NotNull @Length(min = 1)  String token);

    List<PermissionEntity> returnAllPermissionForUser(@NotNull @Length(min = 1)  Integer userId);

    Optional<UserEntity> findUserEntitiesByEmail(@NotNull @Length(min = 1) String userName);

    UserEntity signup(@NotNull TokenRequestDto authenticationRequest);

}
