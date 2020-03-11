package com.kuaqu.reader.component_base.base;

public class EventBusBean {
    private String type;
    private Object object;

    public EventBusBean(String type, Object object) {
        this.type = type;
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
