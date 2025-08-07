package uz.pdp.authuser;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private String  role;

    public enum AuthRole{
        ADMIN,USER
    }
}

