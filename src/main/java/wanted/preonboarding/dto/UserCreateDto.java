package wanted.preonboarding.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import wanted.preonboarding.entity.User;

@Data
@Builder
public class UserCreateDto {

    private Long id;
    private String email;
    private String password;

    public static UserCreateDto from(User user){
        return UserCreateDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
