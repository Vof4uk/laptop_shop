package ua.mykytenko.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WebUtil {
    private WebUtil(){}

    public static HttpHeaders getHeadersRestUtf8(){
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application","json", UTF_8);
        headers.setContentType(mediaType);
        return headers;
    }
}
