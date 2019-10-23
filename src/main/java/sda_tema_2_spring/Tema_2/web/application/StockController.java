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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sda_tema_2_spring.Tema_2.business.service.IStockService;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.web.dto.StockDetailsDto;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;

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

    private final IStockService stockService;

    private final ModelMapper mapper;

    private final CircuitBreakerConfig circuitBreakerConfig;

    public StockController(@Qualifier("ThisOne") IStockService stockService, ModelMapper mapper, CircuitBreakerConfig circuitBreakerConfig) {
        this.stockService = stockService;
        this.mapper = mapper;
        this.circuitBreakerConfig = circuitBreakerConfig;
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @GetMapping("/{stockId}")
    public ResponseEntity<StockDto> getSpecificStockWithItsDetails(@PathVariable("stockId") @NotNull @Min(value = 0) Integer stockId) {
        Supplier<StockDto> dateSupplier = CircuitBreakerRegistry.of(circuitBreakerConfig, new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                System.out.println("onEntryAddedEvent");
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                System.out.println("onEntryRemovedEvent");
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                System.out.println("onEntryReplacedEvent");
            }
        }).circuitBreaker("Emy").decorateSupplier(() -> stockService.getAllStocksWithStockDetails(stockId));
        Try<StockDto> result= Try.ofSupplier(dateSupplier);
        if(result.isSuccess()) {
            StockDto returnObject = result.get();
            Link stockDetailsLink = linkTo(StockDetailsController.class).slash(returnObject.getStockDetailsDto().getId()).withRel("stockDetails");
            returnObject.setStockDetailsDto(null);
            returnObject.add(stockDetailsLink);
            return ResponseEntity.ok(returnObject);
        } else {
            return (ResponseEntity<StockDto>) ResponseEntity.notFound();
        }
    }

    @PreAuthorize("hasRole('ROLE_CREATE')")
    @PostMapping("/")
    public ResponseEntity<StockDto> insertNewStockInDb(@NotNull StockDto requestStock) {
        StockEntity newStock = mapper.map(requestStock, StockEntity.class);
        StockDetailsEntity newStockDetails = mapper.map(requestStock.getStockDetailsDto(), StockDetailsEntity.class);
        newStock.setStockDetailsEntity(newStockDetails);
        newStockDetails.setStockEntity(newStock);
        newStock = stockService.insertNewStockInDb(newStock);
        StockDto response = mapper.map(newStock, StockDto.class);

        Link stockDetailsLink = linkTo(StockDetailsController.class).slash(newStock.getStockId()).withSelfRel();
        response.add(stockDetailsLink);
        return ResponseEntity.created(URI.create(stockDetailsLink.getHref())).build();
    }
}
