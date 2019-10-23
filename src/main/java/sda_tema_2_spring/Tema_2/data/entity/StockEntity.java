package sda_tema_2_spring.Tema_2.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stocks", schema = "sda_proiect_comun1", uniqueConstraints = {@UniqueConstraint(columnNames = "stock_name")})
public class StockEntity implements Serializable {

    @Id
    @Column(name = "id", insertable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stockId;
    @Column(name = "stock_name", unique = true, nullable = false)
    private String stockName;
    @OneToOne(mappedBy = "stockEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private StockDetailsEntity stockDetailsEntity;

    public StockEntity() {
    }

    public StockEntity(String stockName) {
        this.stockName = stockName;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public StockDetailsEntity getStockDetailsEntity() {
        return stockDetailsEntity;
    }

    public void setStockDetailsEntity(StockDetailsEntity stockDetailsEntity) {
        this.stockDetailsEntity = stockDetailsEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity stockEntity = (StockEntity) o;
        return Objects.equals(stockId, stockEntity.stockId) &&
                Objects.equals(stockName, stockEntity.stockName) &&
                Objects.equals(stockDetailsEntity, stockEntity.stockDetailsEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, stockName, stockDetailsEntity);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", stockDetails=" + stockDetailsEntity +
                '}';
    }
}
