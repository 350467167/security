package com.ltkj.template.core.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ltkj.template.mapper.UserManMapper;
import com.ltkj.template.model.User;

/**
 * 实现获取用户权限和角色
 * @author zwy
 *
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UserManMapper userMapper;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	List<String> roles = userMapper.findRolesByUserID(user.getId());
            return JwtUserFactory.create(user , roles);
        }
    }
}
