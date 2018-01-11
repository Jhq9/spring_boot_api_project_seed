package com.company.project.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对需要进行权限或角色认证的每个请求进行解析与校验
 * @author jinhuaquan
 * @date 2017/7/31
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private GeneratorUserDetailService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        //获取拼接过的token
        String authHeader = request.getHeader(this.tokenHeader);
        LOGGER.info("authHeader:{}", authHeader);
        //校验是否拼接过
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "cat "
            final String authToken = authHeader.substring(tokenHead.length());

            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            LOGGER.info("checking authentication {}",  username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    //校验TOKEN是否有效
                    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                                                                                        userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                        LOGGER.info("authenticated user {}, setting security context", userDetails);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        chain.doFilter(request, response);

    }
}
