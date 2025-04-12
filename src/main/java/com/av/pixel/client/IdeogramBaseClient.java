package com.av.pixel.client;

import com.av.pixel.response.ideogram.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import javax.net.ssl.SSLException;
import java.net.HttpRetryException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class IdeogramBaseClient {

    public <T> List<T> exchange(RestTemplate restTemplate, String url, HttpMethod httpMethod, Object requestBody, HttpHeaders httpHeaders, ParameterizedTypeReference<BaseResponse<T>> type){
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = getDefaultHeaders();
        }
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<BaseResponse<T>> response = restTemplate.exchange(url, httpMethod, entity, type);
        return validateAndReturnResponse(response, url, requestBody);
    }

    private <T> List<T> validateAndReturnResponse(ResponseEntity<BaseResponse<T>> response, String url, Object requestBody) {

        if (Objects.isNull(response)){
            log.error("empty response for url : {}, and body : {}", url, requestBody);
            return null;
        }
        HttpStatusCode statusCode = response.getStatusCode();

        if (HttpStatus.OK.equals(statusCode)) {
            return response.getBody().getData();
        }

        log.error("Http status : {}, for url : {}, and request body : {}", statusCode.value(), url, requestBody);
        return null;
    }

    public HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private <T> ResponseEntity<?> exchange(RestTemplate restTemplate, String url, HttpMethod httpMethod, HttpEntity<Object> entity, ParameterizedTypeReference<BaseResponse<T>> type) {
        try {
            return restTemplate.exchange(url, httpMethod, entity, type);
        } catch (HttpClientErrorException e) {
            // 4xx Client Errors
            String responseBody = e.getResponseBodyAsString();

            log.error("Client error: " + e.getStatusCode()+ " - " + e.getStatusText());
            log.error("Response body: " + responseBody);

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Resource not found");
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Handle 401 specifically
                log.error("Authentication required");
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                // Handle 403 specifically
                log.error("Access forbidden");
            }

            return new ResponseEntity<>(e.getStatusCode());
        } catch (HttpServerErrorException e) {
            // 5xx Server Errors
            String responseBody = e.getResponseBodyAsString();

            log.error("Server error: " + e.getStatusCode() + " - " + e.getStatusText());
            log.error("Response body: " + responseBody);

            // Could implement retry logic for certain 5xx errors
            return new ResponseEntity<>(e.getStatusCode());
        } catch (UnknownHttpStatusCodeException e) {
            // Unknown status codes
            log.error("Unknown status code: " + e.getRawStatusCode());
            log.error("Response body: " + e.getResponseBodyAsString());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceAccessException e) {
            // I/O errors, connection issues
            log.error("Resource access error: " + e.getMessage());

            if (e.getCause() instanceof java.net.ConnectException) {
                log.error("Failed to connect to server. Server might be down.");
            } else if (e.getCause() instanceof java.net.SocketTimeoutException) {
                log.error("Connection timed out. Server might be overloaded.");
            } else if (e.getCause() instanceof java.net.UnknownHostException) {
                log.error("Unknown host. Check the URL or your network connection.");
            }

            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (HttpMessageNotReadableException e) {
            // Deserialization errors
            log.error("Failed to read response: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (HttpMessageNotWritableException e) {
            // Serialization errors
            log.error("Failed to write request: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid method arguments
            log.error("Invalid argument: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RestClientException e) {
            // Other RestTemplate exceptions
            log.error("Rest client error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Catch-all for any other exceptions
            log.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
