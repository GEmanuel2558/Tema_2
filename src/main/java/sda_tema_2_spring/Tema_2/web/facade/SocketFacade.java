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
import org.springframework.stereotype.Service;
import sda_tema_2_spring.Tema_2.business.service.IStockService;
import sda_tema_2_spring.Tema_2.data.entity.StockDetailsEntity;
import sda_tema_2_spring.Tema_2.data.entity.StockEntity;
import sda_tema_2_spring.Tema_2.exceptions.custom.ConstraintViolationException;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDbException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@Scope(value = "singleton")
public class SocketFacade implements ISocketFacade {

    private final CircuitBreakerRegistry circuitController;

    private final IStockService stockService;

    @Autowired
    public SocketFacade(CircuitBreakerConfig circuitBreakerConfig, @Qualifier("ThisOne") IStockService stockService) {
        this.stockService = stockService;
        this.circuitController = CircuitBreakerRegistry.of(circuitBreakerConfig, new RegistryEventConsumer<CircuitBreaker>() {
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
        });
    }

    @Override
    public StockEntity getSpecificStockWithItsDetails(@NotNull @Min(value = 0) Integer stockId) {
        Supplier<StockEntity> dateSupplier = circuitController
                .circuitBreaker("Emy_" + ThreadLocalRandom.current().nextInt())
                .decorateSupplier(() -> stockService.getAllStocksWithStockDetails(stockId));
        Try<StockEntity> result = Try.ofSupplier(dateSupplier);
        if (result.isFailure()) {
            System.err.println(result.getCause());
        }
        return result.getOrNull();
    }

    @Override
    public StockEntity insertNewStockInDb(@NotNull StockEntity newSocket, @NotNull StockDetailsEntity newStockDetails) {
        newSocket.setStockDetailsEntity(newStockDetails);
        newStockDetails.setStockEntity(newSocket);
        Supplier<StockEntity> dateSupplier = circuitController
                .circuitBreaker("Emy_" + ThreadLocalRandom.current().nextInt())
                .decorateSupplier(() -> stockService.insertNewStockInDb(newSocket));
        Try<StockEntity> result = Try.ofSupplier(dateSupplier);
        return result.onFailure(throwable -> {
            System.err.println(throwable.getCause()+throwable.getCause().getMessage());
            throw new ConstraintViolationException(throwable.getCause().getLocalizedMessage());
        })
                .map(stockEntity -> {
                    stockEntity.setStockName(null);
                    stockEntity.setStockDetailsEntity(null);
                    return stockEntity;
                }).getOrNull();
    }

    @Override
    public StockEntity updateExistingStockInDb(@NotNull StockEntity newSocket, @NotNull StockDetailsEntity newStockDetails) {
        newSocket.setStockDetailsEntity(newStockDetails);
        newStockDetails.setStockEntity(newSocket);
        Supplier<StockEntity> dateSupplier = circuitController
                .circuitBreaker("Emy_" + ThreadLocalRandom.current().nextInt())
                .decorateSupplier(() -> stockService.updateExistingStockInDb(newSocket));
        Try<StockEntity> result = Try.ofSupplier(dateSupplier);
        return result.onFailure(throwable -> {
            System.err.println(throwable.getCause()+throwable.getCause().getMessage());
            throw new ConstraintViolationException(throwable.getCause().getLocalizedMessage());
        })
                .map(stockEntity -> {
                    stockEntity.setStockName(null);
                    stockEntity.setStockDetailsEntity(null);
                    return stockEntity;
                }).getOrNull();
    }

}
