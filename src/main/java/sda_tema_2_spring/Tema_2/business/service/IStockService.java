package sda_tema_2_spring.Tema_2.business.service;

import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDbException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface IStockService {

    StockEntity getAllStocksWithStockDetails(@NotNull @Min(value = 0) Integer stockId) throws NoInformationInTheDbException;

    StockEntity insertNewStockInDb(@NotNull StockEntity requestStock);

    StockEntity updateExistingStockInDb(@NotNull StockEntity requestStock);

    boolean deleteExistingStockInDb(@NotNull StockEntity requestStock);

    Optional<StockDetailsEntity> getAllStockDetails(@NotNull @Min(value = 0) Integer stockDetailsId);

}
