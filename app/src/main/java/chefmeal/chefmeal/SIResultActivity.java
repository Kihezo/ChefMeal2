package chefmeal.chefmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SIResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siresult);

        Intent intent = getIntent();
        String []listIngre = intent.getStringArrayExtra("IngSelected");
        int entree = intent.getIntExtra("entChecked", 0);
        int plat = intent.getIntExtra("plaChecked", 0);
        int dessert = intent.getIntExtra("desChecked", 0);

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
