package com.cato.config;

import com.cato.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/27
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;

    private final InMemoryAuthenticationProvider inMemoryAuthenticationProvider;


    @Autowired
    public SecurityConfig(UserDetailServiceImpl userDetailService, InMemoryAuthenticationProvider inMemoryAuthenticationProvider) {
        this.userDetailService = userDetailService;
        this.inMemoryAuthenticationProvider = inMemoryAuthenticationProvider;
    }

    /**
     * 注入daoAuthenticationProvider
     * @return
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailService;
    }

    /**
     * 拦截器链
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginProcessingUrl("/login/name")
                .successForwardUrl("/login/getRole")
                .failureForwardUrl("/login/logError")
            .and()
            .logout()
            .and()
            .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
            .rememberMe()
                .alwaysRemember(true)
                .tokenValiditySeconds(86400)
                .and()
            .cors()
                .and()
            .csrf()
                .disable();
    }

    /**
     * 验证方式
     * @param auth 授权管理建造者
     *
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(inMemoryAuthenticationProvider);
        auth.authenticationProvider(daoAuthenticationProvider());
        // auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager providerManager = new ProviderManager(Arrays.asList(inMemoryAuthenticationProvider, daoAuthenticationProvider()));
        // 不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
