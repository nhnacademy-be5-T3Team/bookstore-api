package com.t3t.bookstoreapi.common.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.common.wrapper.RequestWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Book Server API로 들어오는 JWT 토큰을 파싱하는 필터
 * request에 있는 HttpHeaders.AUTHORIZATION을 response header에 추가 해준다 (토큰 재발급의 경우 때문)
 * JWT token의 claim을 request header에 담아준다
 * @author joohyun1996(이주현)
 */
@Component
public class GlobalAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION).trim();
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length == 3){
                String payload = tokenParts[1];
                byte[] decodedPayload = Base64.getDecoder().decode(payload);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = objectMapper.readValue(new String(decodedPayload),new TypeReference<Map<String, Object>>(){});
                String userId = (String) map.get("username");
                String role = (String) map.get("role");

                Map<String, String> header = new HashMap<>();

                header.put("memberId", userId);
                header.put("role", role);

                RequestWrapper wrapper = new RequestWrapper(request,header);
                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                chain.doFilter(wrapper, servletResponse);
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
