package com.shentuo.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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
    @SerializedName("ingredients")
    @Expose
    private ArrayList<BakingIngredient> ingredients;
    @SerializedName("steps")
    @Expose
    private ArrayList<Step> steps;
    @SerializedName("servings")
    @Expose
    private int servings;
    @SerializedName("image")
    @Expose
    private String image;


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

    public ArrayList<BakingIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<BakingIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{\n" +
                "id:" + id + ",\n" +
                "name:" + name + ",\n" +
                "servings:" + servings + ",\n" +
                "image:" + image + ",\n" +
                "} " + super.toString();
    }
}
