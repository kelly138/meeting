package com.cato.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/28
 * @Description:
 */
@Component
public class InMemoryAuthenticationProvider implements AuthenticationProvider {

	/** 用户名 */
	private static final String USERNAME = "admin";

	/** 密码 */
	private static final String PASSWORD = "admin";
	
	/** 管理员用户所拥有的权限 */
	private final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));

	/**
	 * 验证过程
	 * @param authentication 认证
	 * @return 如果返回null，AuthenticationManager会交给下一个支持authentication类型的authenticationProvider处理
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (isMatch(authentication)){
			User user = new User(authentication.getName(), authentication.getCredentials().toString(), authorities);
			return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
		}
		return null;
	}

	private boolean isMatch(Authentication authentication) {
		return authentication.getName().equals(USERNAME) && authentication.getCredentials().equals(PASSWORD);
	}

	/**
	 * 检查authentication的类型是不是这个AuthenticationProvider支持的
	 * @param authentication 认证
	 * @return
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
