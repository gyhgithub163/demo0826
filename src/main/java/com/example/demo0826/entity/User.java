package com.example.demo0826.entity;
/*
* 实体类，类中包含属性，构造器，get和set方法。
* 通过鼠标右击->选择Generate->选择方法， 进行方法创建。
* */
public class User {
    private int id;
    private String name;
    private String Permission;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Permission='" + Permission + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return Permission;
    }

    public void setPermission(String permission) {
        Permission = permission;
    }
}
