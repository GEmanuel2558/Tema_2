package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import sda_tema_2_spring.Tema_2.data.entity.PermissionEntity;

@Repository
@Indexed
public interface PermissionDao extends PagingAndSortingRepository<PermissionEntity, Integer> {



}
