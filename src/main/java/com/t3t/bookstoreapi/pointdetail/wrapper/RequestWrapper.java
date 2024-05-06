package com.t3t.bookstoreapi.pointdetail.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * request에 header를 추가하기 위해 HttpServletRequestWrapper 클래스 상속받아 구현
 * request는 set,add 메소드가 없고 get()만 있다
 * @author joohyun1996(이주현)
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeader;

    public RequestWrapper(HttpServletRequest request, Map<String, String> customHeader) {
        super(request);
        this.customHeader = customHeader;
    }

    @Override
    public String getHeader(String name) {
        if(customHeader.containsKey(name)){
            return customHeader.get(name);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(customHeader.keySet());
        return Collections.enumeration(names);
    }
}