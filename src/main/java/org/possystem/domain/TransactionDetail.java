package org.possystem.domain;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionDetail {
    private Long id;
    private Long transactionId;
    private Long variantId;
    private Double unitPrice;
    private Long quantitySold;
    private Double subTotal;
    private Double costPrice;
    private Double profit;
}
