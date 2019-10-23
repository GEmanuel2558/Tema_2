package sda_tema_2_spring.Tema_2.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stock_details", schema = "sda_proiect_comun1")
public class StockDetailsEntity implements Serializable {

    @Id
    @Column(name = "id", insertable = false, updatable = false,unique = true, nullable = false)
    //@GenericGenerator(name = "generator", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "stock"))
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stockDetailsId;
    @Column(name = "details", nullable = false)
    private String stockDetails;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private StockEntity stockEntity;

    public StockDetailsEntity() {
    }

    public StockDetailsEntity(String stockDetails, StockEntity stockEntity) {
        this.stockDetails = stockDetails;
        this.stockEntity = stockEntity;
    }

    public Integer getStockDetailsId() {
        return stockDetailsId;
    }

    public void setStockDetailsId(Integer stockDetailsId) {
        this.stockDetailsId = stockDetailsId;
    }

    public String getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(String stockDetails) {
        this.stockDetails = stockDetails;
    }

    public StockEntity getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(StockEntity stockEntity) {
        this.stockEntity = stockEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDetailsEntity that = (StockDetailsEntity) o;
        return Objects.equals(stockDetailsId, that.stockDetailsId) &&
                Objects.equals(stockDetails, that.stockDetails) &&
                Objects.equals(stockEntity, that.stockEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockDetailsId, stockDetails, stockEntity);
    }

    @Override
    public String toString() {
        return "StockDetails{" +
                "id=" + stockDetailsId +
                ", stockDetails='" + stockDetails + '\'' +
                '}';
    }
}
