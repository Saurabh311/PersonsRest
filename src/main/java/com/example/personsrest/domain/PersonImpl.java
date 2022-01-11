package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonImpl implements Person {
    String id;
    String name;
    int age;
    String city;
    List<String> groups;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return this.age;
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
        return this.groups;
    }

    @Override
    public void addGroup(String groupId) {
        this.groups.add(groupId);

    }

    @Override
    public void removeGroup(String groupId) {
        this.groups.remove(groupId);

    }
}
