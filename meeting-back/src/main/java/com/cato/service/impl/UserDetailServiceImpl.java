package com.cato.service.impl;

import com.cato.dao.DepartmentDao;
import com.cato.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/27
 * @Description:
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final DepartmentDao departmentDao;

	private static Map<String, GrantedAuthority> authorities = new HashMap<>();

	static {
		authorities.put("admin", new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.put("user", new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Autowired
	public UserDetailServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("经过userdetails");
		String admin = "admin";
		// 判断
		if (admin.equals(username)) {
			System.out.println("username: " + username + "ROLE: " + authorities.get("admin"));
			return new User(username, "", Collections.singleton(authorities.get("admin")));
		} else {

			Department department = departmentDao.getByUsername(username);
			if (department == null) {
				throw new UsernameNotFoundException("用户不存在");
			}
			System.out.println("username: " + username + "ROLE: " + authorities.get("user"));
			return new User(username, department.getDepPassword(), Collections.singleton(authorities.get("user")));

		}
	}

}
