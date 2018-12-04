package chefmeal.chefmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchIngredientsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredients);
        ListView list = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);

    }

    public void addIngredients(View view){
        EditText newIngredient = (EditText) findViewById(R.id.editText2);
        if(!(newIngredient.getText().toString().matches(""))){
            arrayList.add(newIngredient.getText().toString());
            adapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(this, "You did not enter anything", Toast.LENGTH_SHORT).show();
        }
    }

    public void BackToHome(View view){
        super.onBackPressed();
    }
}
