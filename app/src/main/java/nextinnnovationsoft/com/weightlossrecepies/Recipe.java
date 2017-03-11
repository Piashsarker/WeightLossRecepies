package nextinnnovationsoft.com.weightlossrecepies;

import java.io.Serializable;

/**
 * Created by PT on 3/3/2017.
 */
public class Recipe implements Serializable {
    private String recipeName;
    private int recipeImage;
    private String description;

    public Recipe(String recipeName, int recipeImage) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
    }

    public Recipe(String recipeName, String description, int recipeImage) {
        this.recipeName = recipeName;
        this.description = description;
        this.recipeImage = recipeImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(int recipeImage) {
        this.recipeImage = recipeImage;
    }

}
