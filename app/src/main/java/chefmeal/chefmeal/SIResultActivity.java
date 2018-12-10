package chefmeal.chefmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SIResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siresult);

        Intent intent = getIntent();
        String []listIngre = intent.getStringArrayExtra("IngSelected");
        int entree = intent.getIntExtra("entChecked", 0);
        int plat = intent.getIntExtra("plaChecked", 0);
        int dessert = intent.getIntExtra("desChecked", 0);
    }


    public void BackToHome(View view){
        super.onBackPressed();
    }
}
