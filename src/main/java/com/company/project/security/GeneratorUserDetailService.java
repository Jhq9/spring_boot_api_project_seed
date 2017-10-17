package com.company.project.security;

import com.company.project.model.Role;
import com.company.project.model.User;
import com.company.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jinhuaquan
 * @create 2017-10-16 下午1:50
 * @desc spring security中的UserDetailService实现类
 **/
@Service
public class GeneratorUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByPhone(userName) == null ? userService.findByEmail(userName)
                        : userService.findByPhone(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }

        Role role = user.getRole();
        SecurityUser securityUser = new SecurityUser(user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));

        return securityUser;
    }
}
