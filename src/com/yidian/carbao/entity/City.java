package com.yidian.carbao.entity;

public class City {  
    private String name;  
    private String pinYinName;  
  
    public City(String name) {  
        super();  
        this.name = name;  
    }  
  
    public City(String name, String pinYinName) {  
        super();  
        this.name = name;  
        this.pinYinName = pinYinName;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getPinYinName() {  
        return pinYinName;  
    }  
  
    public void setPinYinName(String pinYinName) {  
        this.pinYinName = pinYinName;  
    }  
  
}  