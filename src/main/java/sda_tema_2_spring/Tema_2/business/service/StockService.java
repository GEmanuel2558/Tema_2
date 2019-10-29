package sda_tema_2_spring.Tema_2.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.data.repository.StockDao;
import sda_tema_2_spring.Tema_2.data.repository.StockDetailsDao;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDbException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Scope(value = "singleton")
@Qualifier("ThisOne")
public class StockService implements IStockService {

    private final StockDetailsDao stockDetailsRepository;
    private final StockDao stockRepository;
    private final ModelMapper mapper;

    public StockService(ModelMapper mapper, StockDao stockRepository, StockDetailsDao stockDetailsRepository) {
        this.mapper = mapper;
        this.stockRepository = stockRepository;
        this.stockDetailsRepository = stockDetailsRepository;
    }

    @Transactional(readOnly = true)
    public StockEntity getAllStocksWithStockDetails(@NotNull @Min(value = 0) Integer stockId) throws NoInformationInTheDbException {
        Optional<StockEntity> stock = stockRepository.findById(stockId);
        return stock.orElseThrow(NoInformationInTheDbException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StockEntity insertNewStockInDb(@NotNull StockEntity requestStock) {
        return stockRepository.save(requestStock);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StockEntity updateExistingStockInDb(@NotNull StockEntity requestStock) {
        stockRepository.updateStock(requestStock.getStockName(), requestStock.getStockId());
        stockDetailsRepository.updateStock(requestStock.getStockDetailsEntity().getStockDetails(), requestStock.getStockDetailsEntity().getStockDetailsId());
        return requestStock;
    }

    @Transactional(readOnly = true)
    public Optional<StockDetailsEntity> getAllStockDetails(@NotNull @Min(value = 0) Integer stockDetailsId) {
        return stockDetailsRepository.findById(stockDetailsId);
    }

}
