package com.example.demohibernate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Message {

    private Long id;
    private String content;

    public Message() {

    }

    public Message(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
