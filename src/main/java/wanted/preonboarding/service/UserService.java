package wanted.preonboarding.service;

import wanted.preonboarding.entity.User;
import wanted.preonboarding.jwt.TokenInfo;

public interface UserService {

    User signup(User user);

    TokenInfo login(User user);
}
