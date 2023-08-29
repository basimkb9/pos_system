package org.possystem.domain;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Cart {
    private String productName;
    private String variantName;
    private Double unitPrice;
    private Long quantity;
    private Double subTotal;
    private Double costPrice;

}
