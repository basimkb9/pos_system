package org.possystem.domain;

import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transaction {
    private Long id;
    private LocalDate transactionDate;
    private Double amountPaid;
    private String paymentMethod;
}
