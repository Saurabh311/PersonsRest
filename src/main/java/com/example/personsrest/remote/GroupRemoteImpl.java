package com.example.personsrest.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

public class GroupRemoteImpl implements GroupRemote{
    WebClient webClient;

    public GroupRemoteImpl(){
        webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    @Override
    public String getNameById(String groupId) {
        return (webClient
                .get()
                .uri("posts/" + groupId)
                .retrieve()
                .bodyToMono(Group.class)
                .block()).getName();
    }


    @Override
    public String createGroup(String name) {
         return  (webClient
                 .post()
                .uri("posts/")
                .body(BodyInserters.fromValue(new Group("1001", name, "xyx")))
                .retrieve()
                .bodyToMono(Group.class)
                .single()
                .block()).getName();
    }

    @Override
    public String removeGroup(String name) {
        return Objects.requireNonNull(webClient
                .delete()
                .uri("posts/" + name)
                .retrieve()
                .bodyToMono(Group.class)
                .block()).getName();
    }

    @Value

    static class Group {
        String id;
        String name;
        String body;


        @JsonCreator
        public Group(@JsonProperty("id") String id,
                               @JsonProperty("name") String name,
                               @JsonProperty("body") String body) {
            this.id = id;
            this.name = name;
            this.body = body;

        }
    }
}
