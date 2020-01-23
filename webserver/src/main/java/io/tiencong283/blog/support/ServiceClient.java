package io.tiencong283.blog.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Component
public class ServiceClient {
    // client-side service traversal
    private final Traverson traverson;
    // rest client
    private final RestTemplate client;
    private final String serviceUrl = "http://localhost:9889/";

    public ServiceClient() {
        this.traverson = new Traverson(URI.create(serviceUrl), MediaTypes.HAL_JSON);
        this.client = new RestTemplate();
        this.client.setMessageConverters(Traverson.getDefaultMessageConverters());
    }

    public String renderMarkdown(String markup) {
        return renderMarkup(markup, MediaType.TEXT_MARKDOWN);
    }

    private String renderMarkup(String markup, MediaType contentType) {
        Link renderLink = this.traverson.follow("markup").asLink();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        headers.setContentType(contentType);
        HttpEntity<String> request = new HttpEntity<>(markup, headers);

        return this.client.postForObject(renderLink.getHref(), request, String.class);
    }
}
