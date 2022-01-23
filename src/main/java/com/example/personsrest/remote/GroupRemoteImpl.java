package com.example.personsrest.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GroupRemoteImpl implements GroupRemote{

    private static final String BASE_URL = "/api/groups/";
    private static final String BASE_URL2 = "/api/groups";

    WebClient webClient;
    KeyCloakToken token;

    public GroupRemoteImpl() {
        this.webClient = WebClient.create("https://groups.edu.sensera.se/");
        this.token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();
    }

    @Override
    public String getNameById(String groupId) {
        return webClient.get().uri(BASE_URL + groupId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(Group.class)
                .block().getName();
    }

    @Override
    public String createGroup(String name) {
        return webClient.post()
                .uri(BASE_URL2)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .header(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(new CreateGroup(name)))
                .retrieve()
                .bodyToMono(Group.class)
                .single()
                .block().getId();
    }

    @Override
    public String removeGroup(String name) {
        return webClient.delete().uri(BASE_URL + name)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Value
    static class Group {
        String id;
        String name;

        @JsonCreator
        public Group(@JsonProperty("id") String id, @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Value
    static class CreateGroup {
        String name;

        @JsonCreator
        public CreateGroup(@JsonProperty("name") String name) {
            this.name = name;
        }
    }
}