package sda_tema_2_spring.Tema_2.web.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class StockDetailsDto implements Serializable {

    private Integer id;
    @NotNull(message = "Please set value stockDetails before sending the request")
    private String stockDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(String stockDetails) {
        this.stockDetails = stockDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDetailsDto that = (StockDetailsDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(stockDetails, that.stockDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockDetails);
    }

    @Override
    public String toString() {
        return "StockDetailsDto{" +
                "id=" + id +
                ", stockDetails='" + stockDetails + '\'' +
                '}';
    }
}
