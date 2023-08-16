package wanted.preonboarding.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.preonboarding.entity.User;
import wanted.preonboarding.jwt.JwtTokenProvider;
import wanted.preonboarding.jwt.TokenInfo;
import wanted.preonboarding.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
//    private final MyBCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public User signup(User user){

        if (isEmailExist(user.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User singupUser = User.builder()
                .email(user.getEmail())
//                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        userRepository.save(singupUser);
        return singupUser;
    }

    public boolean isEmailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }


    @Transactional
    public TokenInfo login(User user){

        log.info("login 메서드 시작");

        if(!user.getEmail().contains("@")){
            throw new IllegalArgumentException("로그인 이메일은 @이 포함되어야 합니다.");
        } else if (user.getPassword().length() < 8){
            throw new IllegalArgumentException("로그인 비밀번호는 8자 이상이어야 합니다.");
        }

        String password = user.getPassword();
        log.info("입력한 password : {}", password);

        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        log.info("authentication_Token 정보 : {}", authenticationToken);

        // 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("authentication 정보 : {}", authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        log.info("tokenInfo 정보 : {}", tokenInfo);

        log.info("login 메서드 종료");

        return tokenInfo;
    }


}
