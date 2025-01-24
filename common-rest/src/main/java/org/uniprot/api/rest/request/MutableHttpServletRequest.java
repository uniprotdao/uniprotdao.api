package org.uniprot.api.rest.request;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.servlet.HandlerMapping;

/**
 * Represents a mutable {@link HttpServletRequest}. Override methods of super class {@link
 * HttpServletRequestWrapper} here to get your chosen behaviour.
 *
 * <p>Created 03/12/2019
 *
 * @author Edd
 */
public class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;
    private String uri;
    private StringBuffer url;
    private String servletPath;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
        this.uri = request.getRequestURI();
        this.url = request.getRequestURL();
        this.servletPath = request.getServletPath();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAttribute(String name, Object value) {
        if (name.equals(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)) {
            Map<String, String> newUriVariables = (Map<String, String>) value;
            Map<String, String> uriVariables =
                    (Map<String, String>)
                            super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (uriVariables == null) {
                super.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, value);
            } else {
                for (Map.Entry<String, String> newUriVariable : newUriVariables.entrySet()) {
                    uriVariables.putIfAbsent(newUriVariable.getKey(), newUriVariable.getValue());
                }
            }
        } else {
            super.setAttribute(name, value);
        }
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    @Override
    public String getRequestURI() {
        return uri;
    }

    public void setRequestURI(String uri) {
        this.uri = uri;
    }

    @Override
    public StringBuffer getRequestURL() {
        return url;
    }

    public void setRequestURL(String url) {
        this.url = new StringBuffer(url);
    }

    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);

        if (!Objects.isNull(headerValue)) {
            return headerValue;
        }
        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);

        if (!Objects.isNull(headerValue)) {
            return Collections.enumeration(Collections.singletonList(headerValue));
        }

        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeaders(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }
}
