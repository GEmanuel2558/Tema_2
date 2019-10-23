package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
@Indexed
public interface StockDetailsDao extends CrudRepository<StockDetailsEntity, Integer> {

    Optional<List<StockDetailsEntity>> findAllByStockEntity(@NotNull @Min(value = 0) Integer stockId);

}
