package chefmeal.chefmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchIngredientsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> listIngre = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredients);
        ListView list = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);


        db.collection("Ingredients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listIngre.add(document.getId().toString());
                            }
                        }else{
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        System.out.println(listIngre.toString());
                    }
                });
    }

    public void addIngredients(View view){
        EditText newIngredient = (EditText) findViewById(R.id.editText2);
        if(!(newIngredient.getText().toString().matches(""))){
            if(arrayList.isEmpty()){
                arrayList.add(newIngredient.getText().toString());
                adapter.notifyDataSetChanged();
            }else{
                Boolean alreadyExist = false;
                for (String i:arrayList) {
                    if (newIngredient.getText().toString().matches(i)){
                        alreadyExist = true;
                    }
                }
                if (!(alreadyExist)){
                    arrayList.add(newIngredient.getText().toString());

                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(this, "Tu as déjà rentré cet ingrédient", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(this, "Tu n'as rien écris.", Toast.LENGTH_SHORT).show();
        }
    }

    public void BackToHome(View view){
        super.onBackPressed();
    }
}
