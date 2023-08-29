package org.possystem.domain;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    private Long id;
    private String name;
    private Long categoryId;
    private String variantname;
    private Double variantPrice;


}
