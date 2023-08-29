package org.possystem.domain;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
