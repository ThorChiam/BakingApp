package com.shentuo.bakingapp.data;

import android.app.Application;
import android.content.pm.PackageManager;

import com.shentuo.bakingapp.model.Recipe;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by ShentuoZhan on 11/5/17.
 */

public class RecipeDB {
    private static final String TAG = "RecipeDB";

    public static void init(Application app) {
        Realm.init(app);

        long versionCode = 0;
        try {
            versionCode = app.getPackageManager().getPackageInfo(app.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(versionCode)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    public static void deleteRecipeDB() {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    public static void saveRecipeList(Collection<Recipe> recipes) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.delete(Recipe.class);
        realm.copyToRealmOrUpdate(recipes);
        realm.commitTransaction();
        realm.close();
    }

    public static List<Recipe> getRecipeList() {
        List<Recipe> recipeListUnmanaged = null;
        Realm realm = getRealm();
        RealmResults<Recipe> response = realm.where(Recipe.class).findAll();
        if (response.size() != 0) {
            recipeListUnmanaged = realm.copyFromRealm(response);
        }
        realm.close();
        return recipeListUnmanaged;
    }
}
