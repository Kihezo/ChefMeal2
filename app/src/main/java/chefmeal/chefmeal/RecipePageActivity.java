package chefmeal.chefmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecipePageActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<String> listIng = new ArrayList<String>();
    private String steps ;
    private String RecipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        Intent intent = getIntent();
        RecipeName = intent.getStringExtra("RecipeName");
        TextView title = (TextView) findViewById(R.id.title_RecipePage);
        title.setText(RecipeName);

        db.collection("Recette")
                .whereEqualTo("Nom", RecipeName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                steps = String.valueOf(document.get("Etapes"));
                            }
                        }else{
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        TextView Etapes = (TextView) findViewById(R.id.idStep);
                        Etapes.setText(steps);
                    }
                });
    }

    public void BackToHome(View view){ super.onBackPressed(); }
}
