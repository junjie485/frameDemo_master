package com.kuaqu.reader.module_common_ui.utils;

public class TestBuild2 {
    private String name;
    private String pwd;

    public TestBuild2(Builder builder) {
        this.name=builder.name;
        this.pwd=builder.pwd;

    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getPwd() {
        return pwd == null ? "" : pwd;
    }

    public static final class Builder{
        private String name;
        private String pwd;

        public String getName() {
            return name == null ? "" : name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public String getPwd() {
            return pwd == null ? "" : pwd;
        }

        public Builder setPwd(String pwd) {
            this.pwd = pwd;
            return this;
        }
        public TestBuild2 build(){
            return new TestBuild2(this);
        }
    }
}
