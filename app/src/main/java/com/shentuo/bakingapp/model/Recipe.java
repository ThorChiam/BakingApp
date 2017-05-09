package com.shentuo.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;

/**
 * Created by ShentuoZhan on 9/5/17.
 */

public class Recipe implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

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

    @Override
    public String toString() {
        return "Recipe{\n" +
                "id:" + id + ",\n" +
                "name:" + name + ",\n" +
                "} " + super.toString();
    }
}
