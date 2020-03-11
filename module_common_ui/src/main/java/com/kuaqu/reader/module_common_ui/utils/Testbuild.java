package com.kuaqu.reader.module_common_ui.utils;

public  class Testbuild {
    private  String name;
    private  String pwd;
    public Testbuild setName(String name){
        this.name=name;
        return this;
    }
    public String getName(){
        return name;
    }
    public Testbuild setPwd(String pwd){
        this.pwd=pwd;
        return this;
    }
    public String getPwd(){
        return pwd;
    }
}
