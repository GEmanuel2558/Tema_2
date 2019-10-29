package sda_tema_2_spring.Tema_2.web.application;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.vavr.control.Try;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sda_tema_2_spring.Tema_2.business.service.IStockService;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.web.dto.StockDetailsDto;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;
import sda_tema_2_spring.Tema_2.web.facade.ISocketFacade;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@EnableHypermediaSupport(type = HAL)
public class StockController {

    private final ISocketFacade socketFacade;

    private final ModelMapper mapper;

    public StockController(ModelMapper mapper, ISocketFacade socketFacade) {
        this.mapper = mapper;
        this.socketFacade = socketFacade;
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @GetMapping("/{stockId}")
    public ResponseEntity<StockDto> getSpecificStockWithItsDetails(@PathVariable("stockId") @Valid @NotNull @Min(value = 0) Integer stockId) {
        StockEntity stockEntity = socketFacade.getSpecificStockWithItsDetails(stockId);
        return stockWrapper(stockEntity);
    }

    @PreAuthorize("hasRole('ROLE_CREATE')")
    @PostMapping("/")
    public ResponseEntity<StockDto> insertNewStockInDb(@RequestBody @Valid @NotNull StockDto requestStock, BindingResult result, Model model) {
        StockEntity newStock = mapper.map(requestStock, StockEntity.class);
        if (null == newStock.getStockName() || newStock.getStockName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } else {
            StockDetailsEntity newStockDetails = mapper.map(requestStock.getStockDetailsDto(), StockDetailsEntity.class);
            StockEntity stockEntity = socketFacade.insertNewStockInDb(newStock, newStockDetails);
            Link selfRef = linkTo(StockController.class).slash(stockEntity.getStockId()).withSelfRel();
            return ResponseEntity.created(URI.create(selfRef.getHref())).body(mapper.map(stockEntity, StockDto.class));
        }
    }

    @PreAuthorize("hasRole('ROLE_UPDATE')")
    @PutMapping("/")
    public ResponseEntity<StockDto> updateExistingStockInDb(@RequestBody @Valid @NotNull StockDto requestStock) {
        StockEntity newStock = mapper.map(requestStock, StockEntity.class);
        if (null == newStock.getStockName() || newStock.getStockName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } else {
            StockDetailsEntity newStockDetails = mapper.map(requestStock.getStockDetailsDto(), StockDetailsEntity.class);
            socketFacade.updateExistingStockInDb(newStock, newStockDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private ResponseEntity<StockDto> stockWrapper(StockEntity stockEntity) {
        if (null != stockEntity) {
            StockDto stockDto = mapper.map(stockEntity, StockDto.class);
            Link stockDetailsLink = linkTo(StockDetailsController.class).slash(stockEntity.getStockDetailsEntity().getStockDetailsId()).withRel("stockDetails");
            stockDto.add(stockDetailsLink);
            return ResponseEntity.ok(stockDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
