package org.possystem.domain;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Category {
    private Long id;
    private String name;
}
