package com.company.project.security;

import com.company.project.model.Role;
import com.company.project.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jinhuaquan
 * @create 2017-10-16 下午5:18
 * @desc springSecurity的安全user实体类
 **/
public class SecurityUser extends User implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        Role role = this.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        authorities.add(authority);

        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getPhone();
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public SecurityUser(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setCreateTime(user.getCreateTime());
            this.setName(user.getName());
            this.setEmail(user.getEmail());
            this.setLastPasswordResetDate(user.getLastPasswordResetDate());
            this.setPhone(user.getPhone());
            this.setPassword(user.getPassword());
            this.setRole(user.getRole());
        }
    }
}
