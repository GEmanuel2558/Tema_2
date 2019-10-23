package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
@Indexed
public interface UserDao extends PagingAndSortingRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserEntityByPassword(@NotNull String token);

    Optional<UserEntity> findUserEntitiesByEmail(@NotNull String email);

}
