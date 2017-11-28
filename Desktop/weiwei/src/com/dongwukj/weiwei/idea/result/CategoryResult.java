package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

/**
 * Created by sunjaly on 15/3/24.
 */
public class CategoryResult extends BaseResult {
    private ArrayList<CategoryEntity> categories;
   public ArrayList<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryEntity> categories) {
        this.categories = categories;
    }
   
}
