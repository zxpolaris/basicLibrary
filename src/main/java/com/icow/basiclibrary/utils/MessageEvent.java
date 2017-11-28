package com.icow.basiclibrary.utils;

public class MessageEvent {
    private String id;
    private String name;
    private String content;

    public MessageEvent(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
    public MessageEvent(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
