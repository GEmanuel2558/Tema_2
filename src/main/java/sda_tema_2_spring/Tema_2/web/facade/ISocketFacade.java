package sda_tema_2_spring.Tema_2.web.facade;

import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ISocketFacade {

    StockEntity getSpecificStockWithItsDetails(@NotNull @Min(value = 0) Integer stockId);

    StockEntity insertNewStockInDb(@NotNull StockEntity newSocket, @NotNull StockDetailsEntity newStockDetails);

    StockEntity updateExistingStockInDb(@NotNull StockEntity newSocket, @NotNull StockDetailsEntity newStockDetails);

    boolean deleteExistingStockInDb(@NotNull StockEntity newSocket, @NotNull StockDetailsEntity newStockDetails);

}
