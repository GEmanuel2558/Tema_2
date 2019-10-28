package sda_tema_2_spring.Tema_2.web.facade;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import sda_tema_2_spring.Tema_2.business.service.IStockService;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.web.application.StockDetailsController;
import sda_tema_2_spring.Tema_2.web.dto.StockDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.function.Supplier;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@Scope(value = "singleton")
public class SocketFacade implements ISocketFacade {

    private final CircuitBreakerConfig circuitBreakerConfig;

    private final IStockService stockService;

    @Autowired
    public SocketFacade(CircuitBreakerConfig circuitBreakerConfig, @Qualifier("ThisOne")  IStockService stockService) {
        this.circuitBreakerConfig = circuitBreakerConfig;
        this.stockService = stockService;
    }

    @Override
    public StockEntity getSpecificStockWithItsDetails(@NotNull @Min(value = 0) Integer stockId) {
        Supplier<StockEntity> dateSupplier = CircuitBreakerRegistry.of(circuitBreakerConfig, new RegistryEventConsumer<CircuitBreaker>() {
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
        Try<StockEntity> result= Try.ofSupplier(dateSupplier);
        return result.getOrNull();
    }

}
