package com.company.project.config;

import com.company.project.dao.UserMapper;
import com.company.project.security.GeneratorUserDetailService;
import com.company.project.security.JwtAuthenticationEntryPoint;
import com.company.project.security.JwtAuthenticationTokenFilter;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 整个Security的配置中心
 * Created by jinhuaquan on 2017/7/11.
 */
@Configuration
/**
 * 开启security方法上的权限控制的注解
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserMapper userMapper;

    public static final String ENVIRONMENT_DEV = "dev";

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 装载BCrypt密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public GeneratorUserDetailService generatorUserDetailService(){
        return new GeneratorUserDetailService(userMapper);
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .userDetailsService(generatorUserDetailService())
            .passwordEncoder(passwordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //"dev"环境下的所有接口都开发
        if (ENVIRONMENT_DEV.equals(env)) {
            http.cors().and().authorizeRequests().antMatchers("**").permitAll();
        } else {
            // 由于使用的是JWT，我们这里不需要csrf
            http.csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            //允许所有用户访问"/"
            http.cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests().antMatchers("/", "/static/", "/api/users/actions/register,/api/users/actions/login")
                .permitAll()
                .antMatchers("/auth/**")
                // 对于获取token的rest api要允许匿名访问
                .permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and().formLogin()
                //指定登录页是"/login"
                .loginPage("/api/users/actions/login").permitAll()
                //登录成功后默认跳转到"/hello"
                .defaultSuccessUrl("/users/hello").permitAll().and().logout()
                //退出登录后的默认url是"/home"
                .logoutSuccessUrl("/users/actions/logout").permitAll();
            // 禁用缓存
            http.headers().cacheControl();
            //自定义的过滤器，对TOKEN进行校验
            http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        }

    }

    /**
     * 为了可以让前端可以进行跨域请求
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
            "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * TOKEN校验的过滤器
     * @return
     * @throws Exception
     */
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }


}
