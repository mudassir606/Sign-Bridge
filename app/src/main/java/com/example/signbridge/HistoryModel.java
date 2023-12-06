package com.example.signbridge;

public class HistoryModel {

    String Value;
    int delete;
    long id;


    public HistoryModel(long id,String value){
        Value=value;
        this.id=id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
