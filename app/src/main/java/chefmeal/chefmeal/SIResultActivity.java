package chefmeal.chefmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import chefmeal.chefmeal.Adatpers.SearchResultAdapter;
import chefmeal.chefmeal.Fragments.SearchResultItemActivity;

public class SIResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchResultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Création de beaucoup de liste qui permettent la gestion de données
    private List<String> listIngData = new ArrayList<String>();
    private List<String> listIng = new ArrayList<String>();
    private List<String> listFinal = new ArrayList<String>();
    private List<String> listRecData = new ArrayList<String>();
    private List<String> listRec = new ArrayList<String>();
    private List<String> listRecFinal = new ArrayList<String>();
    private List<String> listDisplayData = new ArrayList<String>();
    private List<String> listDisplay = new ArrayList<String>();
    private List<String> listDisplayFinal = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siresult);

        // Récupération de l'intent envoyé par la page "SearchIngredientsActivity"
        Intent intent = getIntent();
        // Récupération des données envoyé par la page "SearchIngredientsActivity"
        final String []listIngre = intent.getStringArrayExtra("IngSelected");
        int entree = intent.getIntExtra("entChecked", 0);
        int plat = intent.getIntExtra("plaChecked", 0);
        int dessert = intent.getIntExtra("desChecked", 0);

        db.collection("Ingredients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                // Stockage des données de la base de donnée dans une liste
                                listIngData.add(String.valueOf(document.get("idIng")));
                                listIng.add(String.valueOf(document.get("Nom")));
                            }

                            for(int i = 0; i < listIngre.length;i++){
                                for(int j = 0; j < listIng.size();j++){
                                    if (listIngre[i].matches(listIng.get(j))){
                                        // Pour chaque donnée envoyé par la page "SearchIngredientsActivity" on récupère son id
                                        listFinal.add(String.valueOf(listIngData.get(j)));
                                    }
                                }
                            }
//                            System.out.println("Lol" + listIngData.toString());
//                            System.out.println("Lol" + listIng.toString());
//                            System.out.println("Lol" + listIngre[0]);
//                            System.out.println("Lol" + listFinal.toString());

                            db.collection("Recette_ingredients")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                                    // Stpclage des données de la base de donnée dans une liste
                                                    listRecData.add(String.valueOf(document.get("idIng")));
                                                    listRec.add(String.valueOf(document.get("idRec")));
                                                }

                                                for (int i=0;i<listFinal.size();i++){
                                                    for (int j=0;j<listRec.size();j++){
                                                        if(listFinal.get(i).matches(listRecData.get(j))){
                                                            // Pour chaque id d'ingrédients, on récupère l'id de sa recette correspondante
                                                            listRecFinal.add(String.valueOf(listRec.get(j)));
                                                        }
                                                    }
                                                }
//                                                System.out.println("alal" + listRecData.size());
//                                                System.out.println("alal" + listRec.size());
//                                                System.out.println("alal" + listRecFinal.toString());
                                                db.collection("Recette")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if(task.isSuccessful()){

                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                                                        // Stockage des données de la base de donnée dans une liste
                                                                        listDisplayData.add(String.valueOf(document.get("idRec")));
                                                                        listDisplay.add(String.valueOf(document.get("Nom")));
                                                                    }

                                                                    for (int i=0;i<listRecFinal.size();i++){
                                                                        for (int j=0;j<listDisplay.size();j++){
                                                                            if(listRecFinal.get(i).matches(listDisplayData.get(j))){
                                                                                // Pour chaque id de recette, on récupère le nom de la recette correspondante
                                                                                listDisplayFinal.add(String.valueOf(listDisplay.get(j)));
                                                                            }
                                                                        }
                                                                    }
//                                                                    System.out.println("olol" + listDisplayData.toString());
//                                                                    System.out.println("olol" + listDisplay.toString());
//                                                                    System.out.println("olol" + listDisplayFinal.toString());
//                                                                    System.out.println("olol" + listDisplayFinal.get(0));
                                                                    // Appel de la méthode qui créer la liste de recette
                                                                    createListe();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                            });
                        }
                    }
        });



    }

    // Méthode qui créer la lsite de recette
    public void createListe(){
        ArrayList<SearchResultItemActivity> SIR_List = new ArrayList<>();

        // ajout du nom de chaque recette trouvé dans la liste
        for(String i:listDisplayFinal){
            SIR_List.add(new SearchResultItemActivity(i));
        }
        //SIR_List.add(new SearchResultItemActivity(String.valueOf(listDisplayFinal.get(0))));
//        SIR_List.add(new SearchResultItemActivity("Line two"));
//        SIR_List.add(new SearchResultItemActivity("Line three"));

        mRecyclerView = findViewById(R.id.ListResult);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchResultAdapter(SIR_List);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Gestion des évènements de cliques sur chaque éléments de la liste
        mAdapter.setOnClickListener(new SearchResultAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(int position) {
                Intent toRecipe = new Intent(getBaseContext(), RecipePageActivity.class);
                toRecipe.putExtra("RecipeName", listDisplayFinal.get(position));
                startActivity(toRecipe);
            }
        });
    }

    // Appuyer sur la flèche en haut de l'écran permet de faire un retour sur l'ancienne page
    public void BackToHome(View view){
        super.onBackPressed();
    }
}
