package com.example.nutrition.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;

    @NotBlank(message = "성별이 비어있습니다.")
    private String gender;

    @NotBlank(message = "체중이 비어있습니다.")
    private int weight;

    public User toEntity() {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println(hashedPassword);
        return User.builder()
                .loginId(this.loginId)
                .password(hashedPassword)
                .gender(this.gender)
                .weight(this.weight)
                .build();
    }
}
