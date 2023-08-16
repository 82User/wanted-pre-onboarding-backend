package wanted.preonboarding.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.preonboarding.entity.User;
import wanted.preonboarding.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername 메서드 시작");
        return userRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow( () -> new UsernameNotFoundException(username + "는 존재하지 않는 유저입니다."));
    }

    // 해당하는 User의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(User user){

        log.info("createUserDetails 메서드 시작");
        log.info("user 파라미터의 password : {}", user.getPassword());

        User useDetails = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();

        log.info("암호화 후 password : {}", useDetails.getPassword());

        log.info("createUserDetails 메서드 종료");

        return useDetails;
    }
}
