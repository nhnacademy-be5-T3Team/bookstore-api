package com.t3t.bookstoreapi.pointdetail.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.pointdetail.wrapper.RequestWrapper;
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
 * Book Server API의 요청을 처리하기 전에 JWT 토큰을 파싱하고 관리자 권한을 확인하는 필터
 * GlobalAuthFilter에서 관리자 인증 로직을 추가한 클래스
 *
 * 요청의 'Authorization' 헤더에서 JWT 토큰을 추출하여 파싱한다.
 * 토큰이 유효하고, 사용자가 관리자 권한을 가지고 있을 경우에만 요청을 처리한다.
 * 관리자 권한이 없는 사용자의 요청은 403 Forbidden 오류와 함께 거부된다.
 *
 * @author hydrationn(박수화)
 * @see Filter
 */
@Component
public class PointDetailAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(Objects.isNull(authHeader)){
            chain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(!authHeader.startsWith("Bearer")){
            chain.doFilter(servletRequest,servletResponse);
            return;
        }

        try {
            String token = authHeader.trim().split(" ")[1];
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length == 3) {
                String payload = tokenParts[1];
                byte[] decodedPayload = Base64.getDecoder().decode(payload);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = objectMapper.readValue(new String(decodedPayload), new TypeReference<Map<String, Object>>() {
                });
                String userId = (String) map.get("username");
                String role = (String) map.get("role");

                // 관리자가 아닌 경우 여기에서 중단
                if (!"ADMIN".equals(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 필요합니다.");
                    return;
                }

                Map<String, String> header = new HashMap<>();

                header.put("memberId", userId);
                header.put("role", role);

                RequestWrapper wrapper = new RequestWrapper(request, header);

                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                chain.doFilter(wrapper, servletResponse);
            }else{
                chain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            chain.doFilter(servletRequest, servletResponse);
        }
    }
}