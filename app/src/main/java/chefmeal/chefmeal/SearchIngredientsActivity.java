package chefmeal.chefmeal;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SearchIngredientsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    // Création de la variable qui va gérer la connexion avec la base de donnée et de la list qui va contenir le nom des ingrédients
    FirebaseFirestore db_IngName = FirebaseFirestore.getInstance();
    private List<String> listIngName = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredients);

        // Création d'une ListView qui contiendra les ingrédients entré par l'utilisateur
        ListView list = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);


        // Connexion avec la base de donnée, récuparation des noms des ingrédients
        db_IngName.collection("Ingredients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                // Sauvegarde des noms des ingrédients dans la base de donnée dans une ArrayList
                                listIngName.add(String.valueOf(document.get("Nom")));
                            }
                        }else{
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        //System.out.println(listIngName.toString());
                    }
                });
    }

    // Méthode qui ajoute les ingrédients dans la liste
    public void addIngredients(View view){
        // Récupération du texte écrit par l'utilisateur et modification de son texte en petit caractère
        EditText newIng = (EditText) findViewById(R.id.editText2);
        String newIngredient = toLowerCase(newIng.getText().toString());

        // Si l'utilisateur n'a pas rien écrit, on va à la prochaine étape
        if(!(newIngredient.matches(""))){
            // Si la liste est vide, on regarde juste si l'ingrédient entré correspond à un des ingrédients de la base de donnée
            if(arrayList.isEmpty()){
                Boolean isInIt = false;
                // On parcours toute la liste de nom qui se trouve dans la base de donnée
                for(String i: listIngName){
                    if(newIngredient.matches(i)){
                        isInIt = true;
                    }
                }
                // Si une correspondance est trouvé, on ajoute l'ingrédient dans la listView
                if (isInIt){
                    arrayList.add(newIngredient);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, newIngredient + " ajouté", Toast.LENGTH_SHORT).show();
                }
                // Sinon, on indique à l'utilisateur que l'ingrédient ne fait pas parti de la base de donnée
                else{
                    Toast.makeText(this, "Cet ingrédient ne fait pas parti de la base de donnée.", Toast.LENGTH_SHORT).show();
                }
            }else{
                // Si la liste n'est pas vide, on ajoute la condition de si l'ingrédient est déjà présent dans la listView
                Boolean alreadyExist = false;
                Boolean isInList = false;
                // On parcours la liste de la base de donnée
                for (String i:listIngName) {
                    if (newIngredient.matches(i)) {
                        //System.out.println("L'ingrédient existe");
                        isInList = true;
                    }
                }
                // On parcours la listView
                for (String i:arrayList) {
                    if (newIngredient.matches(i)){
                        alreadyExist = true;
                    }
                }
                // Si l'ingrédient entré par l'utilisateur, ne correspond pas dans la liste de la base de donnée et dans la listView, on ajoute l'ingrédient
                if (!(alreadyExist) && (isInList)){
                    arrayList.add(newIngredient);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, newIngredient + " ajouté", Toast.LENGTH_SHORT).show();
                }
                // Sinon, si il n'est pas dans la base de donnée, on l'indique à l'utilisateur
                else if(!(isInList)){
                    Toast.makeText(this, "Cet ingrédient n'existe pas dans la base de donnée", Toast.LENGTH_SHORT).show();
                    }
                // Ou sinon, il existe déjà dans la listView
                else if(alreadyExist){
                    Toast.makeText(this, "Tu as déjà rentré cet ingrédient", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Sinon, l'utilisateur n'a rien écrit
        else{
            Toast.makeText(this, "Tu n'as rien écris.", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode du bouton "Chercher"
    public void goSearch(View view){
        // Création d'un nouvel intent, qui est nécessaire pour passer d'une page à une autre
        Intent intent = new Intent(this, SIResultActivity.class);

        // Création des variables qui gèrent les checkbox, avec une variable qui changera lorsqu'une checkbox est coché
        CheckBox entree = (CheckBox) findViewById(R.id.checkBox_Entree);
        int ent_id = 0;
        CheckBox plat = (CheckBox) findViewById(R.id.checkBox_Plat);
        int pla_id = 0;
        CheckBox dessert = (CheckBox) findViewById(R.id.checkBox_Dessert);
        int des_id = 0;

        // Création d'une liste qui contient chaque ingrédients entrés par l'utilisateur
        String []ingEntered = new String[arrayList.size()];
        int index = 0;
        // Parcours la listView
        for(String i:arrayList){
            ingEntered[index] = i;
            index ++;
        }

        // Si une des checkbox est coché, la variable correspondante changera de valeur pour correspondre à la base de donnée et de l'ajouté en information à l'intent (pour l'envoyer à l'autre page)
        if(entree.isChecked()){
            ent_id = 1;
            intent.putExtra("entChecked", ent_id);
        }
        if(plat.isChecked()){
            pla_id = 2;
            intent.putExtra("plaChecked", pla_id);
        }
        if(dessert.isChecked()){
            des_id = 3;
            intent.putExtra("desChecked", des_id);
        }

        // Envoie de la liste des ingrédients entrés par l'utilisateur
        intent.putExtra("IngSelected", ingEntered);
        // Commencer l'activité
        startActivity(intent);
    }

    // Méthode pour formatter le texte saisi par l'utilisateur
    public String toLowerCase(String toLower){
        return toLower.toLowerCase();
    }

    // Appuyer sur la flèche en haut de l'écran permet de faire un retour sur l'ancienne page
    public void BackToHome(View view){
        super.onBackPressed();
    }
}
