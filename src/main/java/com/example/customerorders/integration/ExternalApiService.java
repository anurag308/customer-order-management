package com.example.customerorders.integration;

import com.example.customerorders.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ExternalApiService {

    private final RestClient restClient;

    public ExternalApiService(RestClient externalApiRestClient) {
        this.restClient = externalApiRestClient;
    }

    public ExternalPost getPost(long id) {
        try {
            ExternalPost post = restClient.get()
                    .uri("/posts/{id}", id)
                    .retrieve()
                    .body(ExternalPost.class);
            if (post == null) {
                throw new NotFoundException("External post %d was not found".formatted(id));
            }
            return post;
        } catch (RestClientException exception) {
            throw new ExternalApiException("External posts service is unavailable", exception);
        }
    }
}