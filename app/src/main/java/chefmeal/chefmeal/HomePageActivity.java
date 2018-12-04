package chefmeal.chefmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void getIngredients(View view){
        Intent toIngredients = new Intent(this, SearchIngredientsActivity.class);
        startActivity(toIngredients);
    }

    public void getRecipes(View view){
        Intent toRecipe = new Intent(this, RecipesListActivity.class);
        startActivity(toRecipe);
    }

}
