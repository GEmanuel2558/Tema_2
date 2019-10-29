package sda_tema_2_spring.Tema_2.web.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sda_tema_2_spring.Tema_2.business.service.IStockService;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.web.dto.StockDetailsDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(value = "/stock.details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StockDetailsController {

    private final ModelMapper mapper;
    private final IStockService stockService;

    @Autowired
    public StockDetailsController(ModelMapper mapper, IStockService stockService) {
        this.mapper = mapper;
        this.stockService = stockService;
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @GetMapping("/{stockDetailsId}")
    public ResponseEntity<StockDetailsDto> getSpecificStockDetailskWithItsDetails(@PathVariable("stockDetailsId") @Valid @NotNull @Min(value = 0) Integer stockDetailsId) {
        Optional<StockDetailsEntity> searchedStockDetails = stockService.getAllStockDetails(stockDetailsId);
        return searchedStockDetails.map(stockDetailsEntity -> ResponseEntity.ok(mapper.map(stockDetailsEntity, StockDetailsDto.class)))
                .orElseGet(() -> (ResponseEntity<StockDetailsDto>) ResponseEntity.notFound());
    }

}
