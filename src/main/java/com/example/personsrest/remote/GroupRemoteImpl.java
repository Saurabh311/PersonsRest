package com.example.personsrest.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Component
public class GroupRemoteImpl implements GroupRemote{
    WebClient webClient;

    public GroupRemoteImpl(){
        webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    @Override
    public String getNameById(String groupId) {
        GroupRemoteImpl.GroupDTO groupDTO = webClient.get()
                .uri("posts/"+groupId)
                .retrieve()
                .bodyToMono(GroupDTO.class)
                .block();
        String groupName = groupDTO.getTitle();
        System.out.println(groupName);
        return groupName;
    }


    @Override
    public String createGroup(String name) {
        return null;
    }

    @Override
    public String removeGroup(String name) {
        return null;
    }

    @Value
    static class GroupDTO {
        String id;
        String title;
        String body;
        String userId;

        @JsonCreator
        public GroupDTO(@JsonProperty("id") String id,
                               @JsonProperty("title") String title,
                               @JsonProperty("body") String body,
                               @JsonProperty("userId") String userId) {
            this.id = id;
            this.title = title;
            this.body = body;
            this.userId = userId;
        }
    }
}
