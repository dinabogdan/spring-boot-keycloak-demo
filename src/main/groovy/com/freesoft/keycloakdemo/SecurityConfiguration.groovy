package com.freesoft.keycloakdemo

import org.keycloak.KeycloakPrincipal
import org.keycloak.KeycloakSecurityContext
import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import java.security.Principal

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper())
        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider)
    }

    @Bean
    KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver()
    }

    @Bean
    @Primary
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl())
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http)
        http.authorizeRequests()
                .antMatchers("/customers/*")
                .hasRole("user")
                .anyRequest()
                .permitAll()
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    KeycloakSecurityContext provideKeycloakSecurityContext() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        Principal principal = attributes.getRequest().getUserPrincipal()
        if (principal == null) {
            null
        }

        if (principal instanceof KeycloakAuthenticationToken) {
            principal = Principal.class.cast(KeycloakAuthenticationToken.class.cast(principal).getPrincipal())
        }

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal.class.cast(principal).getKeycloakSecurityContext()
        }
        null
    }
}
