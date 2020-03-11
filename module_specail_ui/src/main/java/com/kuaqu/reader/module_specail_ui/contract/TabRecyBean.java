package com.kuaqu.reader.module_specail_ui.contract;

public class TabRecyBean {
    private String title;
    private String content;

    public TabRecyBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
