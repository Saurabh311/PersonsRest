package com.example.personsrest.domain;

import lombok.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonImpl implements Person {
    String id;
    String name;
    String city;
    int age;
    List<String> groups;

    public PersonImpl(String name, String city, int age, List<String> groups) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.city = city;
        this.age = age;
        this.groups = groups;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age=age;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public void setCity(String city) {
        this.city=city;

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean active) {

    }

    @Override
    public List<String> getGroups() {
        return groups;
    }

    @Override
    public void addGroup(String groupId) {
        groups.add(groupId);

    }

    @Override
    public void removeGroup(String groupId) {
        groups.remove(groupId);

    }
}
