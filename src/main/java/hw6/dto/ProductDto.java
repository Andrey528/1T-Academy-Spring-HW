package hw6.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String accNumber;

    private BigDecimal balance;

    private String type;

    public ProductDto(String accNumber, BigDecimal balance, String type) {
        this.accNumber = accNumber;
        this.balance = balance;
        this.type = type;
    }
}
