package com.pgmacdesign.internal;

import com.google.gson.Gson;
import com.pgmacdesign.internal.datamodels.CustomException;
import com.pgmacdesign.internal.datamodels.ErrorModel;
import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.utils.Constants;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For more info: https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 */
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    /**
     * This will handle all exceptions thrown by me (IE, throws MyException) so I can use this for auth errors
     * and other known, catchable things.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorModel> handleAllExceptions(Exception ex, WebRequest request) {
        try {
            MyErrorLogger.logError(ex);
        } catch (Exception e) { }
        ErrorModel errorDetails = new ErrorModel(request.getDescription(false), ex.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This will handle all exceptions thrown by me (IE, throws MyException) so I can use this for auth errors
     * and other known, catchable things.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ErrorModel> handleCustomExceptions(CustomException ex, WebRequest request) {
        try {
            MyErrorLogger.logError(ex);
        } catch (Exception e) { }
        ErrorModel errorDetails = new ErrorModel(ex.getError(), ex.getMessage(),
                (Utilities.getInt(ex.getErrorCode()) <= 0 ? 400 : ex.getErrorCode()));
        HttpStatus statusCode;
        try {
            statusCode = HttpStatus.valueOf(ex.getErrorCode());
        } catch (IllegalArgumentException iae){
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errorDetails, statusCode);
    }

    /**
     * Handles all exceptions not handled by the global exception handler
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ErrorModel errorDetails = null;
        try {
            MyErrorLogger.logError(ex);
        } catch (Exception e) { }
        if(status != null) {
            int errorValue = status.value();
            switch (errorValue) {
                case 100: //CONTINUE(100, "Continue"),
                case 101: //SWITCHING_PROTOCOLS(101, "Switching Protocols"),
                case 102: //PROCESSING(102, "Processing"),
                case 103: //CHECKPOINT(103, "Checkpoint"),
                    errorDetails = Utilities.buildErrorMessage("An error has occurred",
                            Constants.GENERIC_ERROR_STRING, errorValue);
                    break;
                case 200: //OK(200, "OK"),
                    errorDetails = Utilities.buildErrorMessage("200 response success",
                            "200 response success", errorValue);
                    break;
                case 201: //CREATED(201, "Created"),
                case 202: //ACCEPTED(202, "Accepted"),
                case 203: //NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
                case 204: //NO_CONTENT(204, "No Content"),
                case 205: //RESET_CONTENT(205, "Reset Content"),
                case 206: //PARTIAL_CONTENT(206, "Partial Content"),
                case 207: //MULTI_STATUS(207, "Multi-Status"),
                case 208: //ALREADY_REPORTED(208, "Already Reported"),
                case 226: //IM_USED(226, "IM Used"),
                    errorDetails = Utilities.buildErrorMessage("2xx response success",
                            "2xx response success", errorValue);
                    break;
                case 300: //MULTIPLE_CHOICES(300, "Multiple Choices"),
                case 301: //MOVED_PERMANENTLY(301, "Moved Permanently"),
                case 302: //FOUND(302, "Found"),
                case 303: //SEE_OTHER(303, "See Other"),
                case 304: //NOT_MODIFIED(304, "Not Modified"),
                case 307: //TEMPORARY_REDIRECT(307, "Temporary Redirect"),
                case 308: //PERMANENT_REDIRECT(308, "Permanent Redirect"),
                    errorDetails = Utilities.buildErrorMessage("3xx response success",
                            "3xx response success", errorValue);
                    break;
                case 400: //BAD_REQUEST(400, "Bad Request"),
                    errorDetails = Utilities.buildErrorMessage("Bad Request",
                            "4xx response error", errorValue);
                    break;
                case 401: //UNAUTHORIZED(401, "Unauthorized"),
                    errorDetails = Utilities.buildErrorMessage("Unauthorized",
                            "You are unauthorized to perform this action", errorValue);
                    break;
                case 402: //PAYMENT_REQUIRED(402, "Payment Required"),
                case 403: //FORBIDDEN(403, "Forbidden"),
                    errorDetails = Utilities.buildErrorMessage("4xx response error",
                            "4xx response error", errorValue);
                    break;
                case 404: //NOT_FOUND(404, "Not Found"),
                    errorDetails = Utilities.buildErrorMessage(ex.getHttpMethod() + " request at " +
                            ex.getRequestURL() + " is not defined as a valid endpoint.",
                            Constants.GENERIC_ERROR_STRING, errorValue);
                    break;
                case 405: //METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
                case 406: //NOT_ACCEPTABLE(406, "Not Acceptable"),
                case 407: //PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
                case 408: //REQUEST_TIMEOUT(408, "Request Timeout"),
                case 409: //CONFLICT(409, "Conflict"),
                case 410: //GONE(410, "Gone"),
                case 411: //LENGTH_REQUIRED(411, "Length Required"),
                case 412: //PRECONDITION_FAILED(412, "Precondition Failed"),
                case 413: //PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
                case 414: //URI_TOO_LONG(414, "URI Too Long"),
                case 415: //UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
                case 416: //REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
                case 417: //EXPECTATION_FAILED(417, "Expectation Failed"),
                case 418: //I_AM_A_TEAPOT(418, "I'm a teapot"),
                case 422: //UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
                case 423: //LOCKED(423, "Locked"),
                case 424: //FAILED_DEPENDENCY(424, "Failed Dependency"),
                case 426: //UPGRADE_REQUIRED(426, "Upgrade Required"),
                case 428: //PRECONDITION_REQUIRED(428, "Precondition Required"),
                case 429: //TOO_MANY_REQUESTS(429, "Too Many Requests"),
                case 431: //REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
                case 451: //UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
                    errorDetails = Utilities.buildErrorMessage("4xx response error",
                            "4xx response error", errorValue);
                    break;
                case 500: //INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
                case 501: //NOT_IMPLEMENTED(501, "Not Implemented"),
                case 502: //BAD_GATEWAY(502, "Bad Gateway"),
                case 503: //SERVICE_UNAVAILABLE(503, "Service Unavailable"),
                case 504: //GATEWAY_TIMEOUT(504, "Gateway Timeout"),
                case 505: //HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
                case 506: //VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
                case 507: //INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
                case 508: //LOOP_DETECTED(508, "Loop Detected"),
                case 509: //BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
                case 510: //NOT_EXTENDED(510, "Not Extended"),
                case 511: //NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");
                    errorDetails = Utilities.buildErrorMessage("5xx server response error",
                            "5xx server response error", errorValue);
                    break;
            }
            if(errorDetails != null){
                return new ResponseEntity<>(errorDetails, status);
            }
        }
        errorDetails = new ErrorModel(request.getDescription(false), ex.getMessage(), 500);
        return new ResponseEntity<>(errorDetails, status);
    }


}
