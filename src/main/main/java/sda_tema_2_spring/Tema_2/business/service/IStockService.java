package sda_tema_2_spring.Tema_2.business.service;

import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDb;
import sda_tema_2_spring.Tema_2.web.dto.StockDetailsDto;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface IStockService {

    StockEntity getAllStocksWithStockDetails(@NotNull @Min(value = 0) Integer stockId) throws NoInformationInTheDb;

    StockEntity insertNewStockInDb(@NotNull StockEntity requestStock);

    Optional<StockDetailsEntity> getAllStockDetails(@NotNull @Min(value = 0) Integer stockDetailsId);

}
