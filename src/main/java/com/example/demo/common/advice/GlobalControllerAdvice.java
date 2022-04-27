package com.example.demo.common.advice;

import com.example.demo.common.util.LogUtils;
import com.example.demo.common.exception.BadRequestException;
import com.example.demo.common.exception.BizException;
import com.example.demo.common.exception.Message;
import com.example.demo.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("unchecked")
public class GlobalControllerAdvice<T> extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<T> {

    /**
     * 일반적인 서버 오류는 9999 로 제공한다.
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(HttpServletRequest request, Exception e) {
        LogUtils.logStackTrace(e);

        Response response = new Response(Errors.E9999);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * SystemException 의 경우 해당 exception 의 code 와 message 를 사용한다.
     * HttpStatus 는 INTERNAL_SERVER_ERROR (500) 을 사용한다.
     */
    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity handleSystemException(HttpServletRequest request, SystemException e) {
        LogUtils.logStackTrace(e);

        Response response = new Response();
        response.setError(e.getCode());
        response.setMessage(e.getMsg());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * BadRequestException 의 경우 해당 exception 의 code 와 message 를 사용한다.
     * HttpStatus 는 BAD_REQUEST (400) 을 사용한다.
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity handleBadRequestException(HttpServletRequest request, BadRequestException e) {
        LogUtils.logStackTrace(e);

        Response response = new Response();
        response.setError(e.getCode());
        response.setMessage(e.getMsg());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * BizException 의 경우 해당 exception 의 code 와 message 를 사용한다.
     * HttpStatus 는 OK (200) 을 사용한다.
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseEntity handleBizException(HttpServletRequest request, BizException e) {
        LogUtils.logStackTrace(e);

        Response response = new Response();
        response.setError(e.getCode());
        response.setMessage(e.getMsg());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * beforeBodyWrite 적용 여부를 리턴한다. (true: beforeBodyWrite 적용)
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Controller 에서 Object 로 리턴한 것을 Response 로 Wrapping 한 형태로 바꿔서 응답한다.
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return
     */
    @Override
    public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String path = request.getURI().getPath();
        log.debug(">>>>>>>>>>>>>> path: {}", path);
        log.debug(body.toString());
        if (body instanceof Response) {
            return body;
        }
        else if (body instanceof String) {
            return body;
        }
        else if (path.contains("/api-docs/") || path.contains("/swagger-ui/")) {
            return body;
        }
        else{
            Response<T> responseObject = new Response<>();
            responseObject.setError(Errors.E0000.getCode());
            responseObject.setMessage(Message.builder()
                    .title(Errors.E0000.getMessage().getTitle())
                    .content(Errors.E0000.getMessage().getContent())
                    .build());
            responseObject.setData(body);
            return (T) responseObject;
        }
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LogUtils.logStackTrace(ex);

        Response response = new Response(Errors.BAD_REQUST_RAPAM);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LogUtils.logStackTrace(ex);

        Response response = new Response(Errors.BAD_REQUST_RAPAM);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
