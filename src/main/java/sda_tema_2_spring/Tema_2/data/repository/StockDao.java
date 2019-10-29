package sda_tema_2_spring.Tema_2.data.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;

@Repository
@Indexed
public interface StockDao extends CrudRepository<StockEntity, Integer> {

    //https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
    @Modifying
    @Query("update StockEntity u set u.stockName = ?1 where u.stockId = ?2")
    void updateStock(String stockName, Integer stockId);

}
