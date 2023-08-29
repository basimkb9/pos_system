package org.possystem.domain;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Variant {
    private Long id;
    private String name;
    private Double price;
    private Long quantityInStock;
    private String barcode;
    private Long productId;
    private Double costPrice;
}
