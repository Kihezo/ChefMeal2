package chefmeal.chefmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class SIResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore dbIng = FirebaseFirestore.getInstance();
    FirebaseFirestore dbRec = FirebaseFirestore.getInstance();

    private List<String> listIngData = new ArrayList<String>();
    private List<String> listIng = new ArrayList<String>();
    private List<String> listRecData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siresult);

        Intent intent = getIntent();
        final String []listIngre = intent.getStringArrayExtra("IngSelected");
        int entree = intent.getIntExtra("entChecked", 0);
        int plat = intent.getIntExtra("plaChecked", 0);
        int dessert = intent.getIntExtra("desChecked", 0);

        dbIng.collection("Ingredients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listIngData.add(String.valueOf(document.getData()));
                            }
                            for(int i = 0; i < listIngre.length; i++){
                                if (listIngre[i] == listIngData.get(i)){

                                }
                            }
                            System.out.println(listIngData.toString());
                        }
                    }
        });


        dbRec.collection("Recette")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listRecData.add(String.valueOf(document.getData()));
                            }
                            System.out.println(listRecData.toString());
                        }
                    }
        });



        ArrayList<SearchResultItemActivity> SIR_List = new ArrayList<>();

        SIR_List.add(new SearchResultItemActivity("Line one"));
        SIR_List.add(new SearchResultItemActivity("Line two"));
        SIR_List.add(new SearchResultItemActivity("Line three"));

        mRecyclerView = findViewById(R.id.ListResult);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchResultAdapter(SIR_List);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void BackToHome(View view){
        super.onBackPressed();
    }
}
