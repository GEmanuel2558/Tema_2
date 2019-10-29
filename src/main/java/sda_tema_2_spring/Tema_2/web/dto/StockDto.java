package sda_tema_2_spring.Tema_2.web.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class StockDto extends RepresentationModel implements Serializable {

    private Integer stockId;
    @NotNull(message = "Please set value stockName before sending the request")
    private String stockName;
    @NotNull(message = "Please set value stockDetailsDto before sending the request")
    private StockDetailsDto stockDetailsDto;

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

    public StockDetailsDto getStockDetailsDto() {

        return stockDetailsDto;
    }

    public void setStockDetailsDto(StockDetailsDto stockDetailsDto) {
        this.stockDetailsDto = stockDetailsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDto stockDto = (StockDto) o;
        return Objects.equals(stockId, stockDto.stockId) &&
                Objects.equals(stockName, stockDto.stockName) &&
                Objects.equals(stockDetailsDto, stockDto.stockDetailsDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, stockName, stockDetailsDto);
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "id=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", stockDetailsDto=" + stockDetailsDto +
                '}';
    }
}
