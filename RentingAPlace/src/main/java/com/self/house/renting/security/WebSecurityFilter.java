package com.self.house.renting.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.security.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class WebSecurityFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(WebSecurityFilter.class);
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = "";
        String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (isValidTokenHeader(header)) {
        token = header.substring(7);
        if (tokenUtil.validateToken(token)) {
            try {
                Claims claims = tokenUtil.getClaims(token);
                UserDetails details = getUsersDetailsFromClaims(claims);
                if (details != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            details, null, details.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.INVALID_JWT_TOKEN);
            }

        }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isValidAuthorizationInfo(HttpServletRequest request) {
        String idHeader = request.getHeader(Constants.ID_HEADER);
        String username = request.getHeader(Constants.USERNAME_HEADER);
        String role = request.getHeader(Constants.ROLE_HEADER);
        return idHeader != null && !idHeader.isEmpty() && username != null && !username.isEmpty() && role != null && !role.isEmpty();
    }

    private boolean isValidTokenHeader(String header) {
        return header != null && header.startsWith(Constants.BEARER_TEXT);
    }

    public UserDetailsImpl getUsersDetailsFromClaims(Claims claims) {
        UserDetailsImpl details = new UserDetailsImpl();
        if (claims == null) {
            return null;
        }
        String strId = getValueFromClaims(claims, Constants.DETAILS_ID);
        String strRole = getValueFromClaims(claims, Constants.DETAILS_ROLE);
        String username = getValueFromClaims(claims, Constants.DETAILS_USERNAME);
        if (!isValidUserDetails(strId, strRole, username)) {
            return null;
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add((GrantedAuthority) () -> strRole);
        details.setAuthorities(authorityList);
        details.setId(Integer.parseInt(strId));
        details.setUsername(username);
        return details;
    }

    private String getValueFromClaims(Claims claims, String key) {
        return claims.get(key) != null ? String.valueOf(claims.get(key)) : null;
    }

    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidUserDetails(String strId, String strRole, String username) {
       return strId != null && !strId.isEmpty() && isNumber(strId) && strRole != null && !strRole.isEmpty() && username != null && !username.isEmpty();
    }


}
