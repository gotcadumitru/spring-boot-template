package com.dima.demo.oauth2;

import com.dima.demo.authentication.Provider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User user =  super.loadUser(userRequest);
        Provider provider = Provider.LOCAL;
        if(clientName.equals("Google")){
            provider = Provider.GOOGLE;
        }
        if(clientName.equals("Facebook")){
            provider = Provider.FACEBOOK;
        }

        return new CustomOAuth2User(user, provider);
    }
}