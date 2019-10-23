package sda_tema_2_spring.Tema_2.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.data.repository.StockDao;
import sda_tema_2_spring.Tema_2.data.repository.StockDetailsDao;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDb;
import sda_tema_2_spring.Tema_2.web.dto.StockDetailsDto;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;

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
    public StockDto getAllStocksWithStockDetails(@NotNull @Min(value = 0) Integer stockId) throws NoInformationInTheDb {
        Optional<StockEntity> stock = stockRepository.findById(stockId);
        StockEntity stockEntity = stock.orElseThrow(NoInformationInTheDb::new);
        StockDto stockDto = mapper.map(stockEntity, StockDto.class);
        StockDetailsDto stockDetailsDto = mapper.map(stockEntity.getStockDetailsEntity(), StockDetailsDto.class);
        //Optional<List<StockDetailsEntity>> stockDetailsEntity = stockDetailsRepository.findAllByStockEntity(stockEntity.getId());
        stockDto.setStockDetailsDto(stockDetailsDto);
        return stockDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StockEntity insertNewStockInDb(@NotNull StockEntity requestStock) {
        return stockRepository.save(requestStock);
    }

    @Transactional(readOnly = true)
    public Optional<StockDetailsEntity> getAllStockDetails(@NotNull @Min(value = 0) Integer stockDetailsId) {
        return stockDetailsRepository.findById(stockDetailsId);
    }

}
