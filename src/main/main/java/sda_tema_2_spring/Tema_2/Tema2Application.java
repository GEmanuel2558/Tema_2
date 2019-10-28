package sda_tema_2_spring.Tema_2;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sda_tema_2_spring.Tema_2.data.entity.*;
import sda_tema_2_spring.Tema_2.data.repository.*;
import sda_tema_2_spring.Tema_2.utils.EncryptionHelper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"sda_tema_2_spring.Tema_2"})
public class Tema2Application implements CommandLineRunner {

    @Autowired
    private UserDao userRepository;
    @Autowired
    private PermissionDao permissionRepository;
    @Autowired
    private UserPermissionDao upRepository;
    @Autowired
    private StockDao stockRepository;
    @Autowired
    private StockDetailsDao stockDetailsRepository;

    public static void main(String[] args) {
        SpringApplication.run(Tema2Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CircuitBreakerConfig getCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .waitDurationInOpenState(Duration.ofMillis(120000))
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .failureRateThreshold(1)
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        EncryptionHelper encryptionHelper=EncryptionHelper.getInstance();

        UserEntity user1 = new UserEntity("emanuel", encryptionHelper.plainTextToHash("emanuel"), "gresanu.emanuel.vasile@gmail.com");
        UserEntity user2 = new UserEntity("andrei", encryptionHelper.plainTextToHash("andrei"), "andrei@gmail.com");
        UserEntity user3 = new UserEntity("maria", encryptionHelper.plainTextToHash("maria"), "maria@gmail.com");
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        PermissionEntity permissionCreate = new PermissionEntity("CREATE");
        PermissionEntity permissionRead = new PermissionEntity("READ");
        PermissionEntity permissionUpdate = new PermissionEntity("UPDATE");
        PermissionEntity permissionDelete = new PermissionEntity("DELETE");
        permissionRepository.saveAll(Arrays.asList(permissionCreate, permissionRead, permissionUpdate, permissionDelete));

        UserPermissionEntity user1Permission1 = new UserPermissionEntity(user1.getId(), permissionCreate);
        UserPermissionEntity user1Permission2 = new UserPermissionEntity(user1.getId(), permissionRead);
        UserPermissionEntity user1Permission3 = new UserPermissionEntity(user1.getId(), permissionUpdate);
        UserPermissionEntity user1Permission4 = new UserPermissionEntity(user1.getId(), permissionDelete);
        upRepository.saveAll(Arrays.asList(user1Permission1, user1Permission2, user1Permission3, user1Permission4));

        UserPermissionEntity user2Permission1 = new UserPermissionEntity(user2.getId(), permissionCreate);
        UserPermissionEntity user2Permission2 = new UserPermissionEntity(user2.getId(), permissionRead);
        UserPermissionEntity user2Permission3 = new UserPermissionEntity(user2.getId(), permissionUpdate);
        upRepository.saveAll(Arrays.asList(user2Permission1, user2Permission2, user2Permission3));

        UserPermissionEntity user3Permission1 = new UserPermissionEntity(user3.getId(), permissionCreate);
        UserPermissionEntity user3Permission2 = new UserPermissionEntity(user3.getId(), permissionRead);
        upRepository.saveAll(Arrays.asList(user3Permission1, user3Permission2));

        StockEntity stockEntity1 = new StockEntity("Stock no. 1");
        StockDetailsEntity stockDetailsEntity1 = new StockDetailsEntity("This are the details of stock no. 1", stockEntity1);
        stockEntity1.setStockDetailsEntity(stockDetailsEntity1);
        stockRepository.save(stockEntity1);

        StockEntity stockEntity2 = new StockEntity("Stock no. 2");
        StockDetailsEntity stockDetailsEntity2 = new StockDetailsEntity("This are the details of stock no. 2", stockEntity2);
        stockEntity2.setStockDetailsEntity(stockDetailsEntity2);
        stockRepository.save(stockEntity2);

        StockEntity stockEntity3 = new StockEntity("Stock no. 3");
        StockDetailsEntity stockDetailsEntity3 = new StockDetailsEntity("This are the details of stock no. 3", stockEntity3);
        stockEntity3.setStockDetailsEntity(stockDetailsEntity3);
        stockRepository.save(stockEntity3);
    }
}
