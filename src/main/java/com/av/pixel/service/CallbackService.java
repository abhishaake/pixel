package com.av.pixel.service;

import com.av.pixel.helper.TransformUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CallbackService {

    public void handleAdCallback(HttpServletRequest request) {
        log.info("inside handleAdCallback");
        try {
            Map<String, String> headers = captureHeaders(request);

            Map<String, String[]> parameters = captureParameters(request);

            String requestBody = captureRequestBody(request);

            logRequestDetails(headers, parameters, requestBody);

        } catch (Exception e) {
            log.error("handleAdCallback error: {}", e.getMessage(), e);
        }
    }

    private Map<String, String> captureHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    private Map<String, String[]> captureParameters(HttpServletRequest request) {
        return request.getParameterMap();
    }

    private String captureRequestBody(HttpServletRequest request) throws Exception {
        StringBuilder body = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            body.append(line);
        }

        return body.toString();
    }


    private void logRequestDetails(
            Map<String, String> headers,
            Map<String, String[]> parameters,
            String requestBody
    ) {
        StringBuilder stringBuilder = new StringBuilder();

        // Log Headers
        headers.forEach((key, value) ->
                stringBuilder.append("Header - ").append(key).append(": ").append(value).append("\n")
        );
        stringBuilder.append("\n");
        // Log Parameters
        parameters.forEach((key, values) ->
                stringBuilder.append("Param - ").append(key).append(": ").append(String.join(", ", values)).append("\n")
        );

        log.info(stringBuilder.toString() + " \n " + "Request Body: {}", requestBody);
    }
}
