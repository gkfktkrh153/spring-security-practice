package com.example.demo.oauth;

import com.example.demo.auth.PrincipalDetail;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 ->  AccessToken요청
        // UserRequest정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 추출 -> 회원가입 -> OAuth2User -> Authentication
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId; // 214124115151_google user만의 고유한 ID
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        // 회원정보가 없는경우에만 회원가입
        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password("1234")
                    .email(email)
                    .providerId(providerId)
                    .provider(provider)
                    .role(role)
                    .build();
            userRepository.save(userEntity);
        }


        return new PrincipalDetail(userEntity, oAuth2User.getAttributes());
    }
}