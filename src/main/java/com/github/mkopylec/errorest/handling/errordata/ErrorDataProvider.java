package com.github.mkopylec.errorest.handling.errordata;

import com.github.mkopylec.errorest.configuration.ErrorestProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.github.mkopylec.errorest.handling.RequestAttributeSettingFilter.REQUEST_HEADERS_ERROR_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.springframework.web.context.request.WebRequest.SCOPE_REQUEST;

public abstract class ErrorDataProvider<T extends Throwable> {

    public static final String REQUEST_URI_ERROR_ATTRIBUTE = "path";
    public static final String NOT_AVAILABLE_DATA = "[N/A]";

    protected final ErrorestProperties errorestProperties;

    protected ErrorDataProvider(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    public abstract ErrorData getErrorData(T ex, HttpServletRequest request);

    public abstract ErrorData getErrorData(T ex, HttpServletRequest request, HttpStatus defaultResponseStatus, ErrorAttributes errorAttributes, WebRequest webRequest);

    protected String getRequestUri(ErrorAttributes errorAttributes, WebRequest webRequest) {
        return errorAttributes.getErrorAttributes(webRequest, false).getOrDefault(REQUEST_URI_ERROR_ATTRIBUTE, NOT_AVAILABLE_DATA).toString();
    }

    protected String getRequestHeaders(WebRequest webRequest) {
        return getAttribute(REQUEST_HEADERS_ERROR_ATTRIBUTE, webRequest);
    }

    protected String getAttribute(String name, WebRequest webRequest) {
        return defaultIfBlank((String) webRequest.getAttribute(name, SCOPE_REQUEST), NOT_AVAILABLE_DATA);
    }
}
