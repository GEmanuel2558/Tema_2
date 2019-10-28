package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sda_tema_2_spring.Tema_2.data.entity.PermissionEntity;
import sda_tema_2_spring.Tema_2.data.entity.UserPermissionEntity;

import java.util.List;
import java.util.Optional;

@Repository
@Indexed
public interface UserPermissionDao extends PagingAndSortingRepository<UserPermissionEntity, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "select new sda_tema_2_spring.Tema_2.data.entity.PermissionEntity(p.name) " +
            "from UserPermissionEntity up join PermissionEntity p on up.permissionsId = p.id where up.userId = :userId")
    List<PermissionEntity> returnAllPermissionForUser(Integer userId);

}
