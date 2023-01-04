package com.dima.demo.oauth2;

import java.util.Collection;
import java.util.Map;

import com.dima.demo.authentication.Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private Provider provider;
    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User oauth2User, Provider provider) {
        this.oauth2User = oauth2User;
        this.provider = provider;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }

    public String getFirstName() {
        //TODO de vazut pentru facebook pentru ca e diferit
        return oauth2User.<String>getAttribute("given_name");
    }
    public String getLastName() {
        return oauth2User.<String>getAttribute("family_name");
    }

    public Provider getOauth2ClientName() {
        return provider;
    }
}