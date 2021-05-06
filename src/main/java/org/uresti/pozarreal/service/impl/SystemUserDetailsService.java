package org.uresti.pozarreal.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uresti.pozarreal.model.Login;
import org.uresti.pozarreal.model.User;
import org.uresti.pozarreal.repository.LoginRepository;
import org.uresti.pozarreal.repository.RolesRepository;
import org.uresti.pozarreal.repository.UserRepository;

@Service
@Transactional
public class SystemUserDetailsService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public SystemUserDetailsService(LoginRepository loginRepository,
                                    UserRepository userRepository,
                                    RolesRepository rolesRepository) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                    loadUserInfo(mappedAuthorities, oidcUserAuthority.getAttributes());

                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

                    loadUserInfo(mappedAuthorities, oauth2UserAuthority.getAttributes());
                }
            });

            return mappedAuthorities;
        };
    }

    private void loadUserInfo(Set<GrantedAuthority> mappedAuthorities, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");
        String name = (String) attributes.get("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> registerUser(email, picture, name));

        rolesRepository.findRolesByUser(user.getId()).forEach(role ->
                mappedAuthorities.add(new SimpleGrantedAuthority(role)));
    }

    private User registerUser(String email, String picture, String name) {
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setPicture(picture);

        userRepository.save(user);

        Login login = new Login();

        login.setUserId(user.getId());
        login.setEmail(email);
        login.setId(UUID.randomUUID().toString());

        loginRepository.save(login);

        return user;
    }
}
