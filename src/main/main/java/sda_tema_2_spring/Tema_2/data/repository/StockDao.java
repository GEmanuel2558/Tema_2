package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;

@Repository
@Indexed
public interface StockDao extends CrudRepository<StockEntity, Integer> {
}
