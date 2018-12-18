package chefmeal.chefmeal;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import chefmeal.chefmeal.Adatpers.ViewPagerAdapter;
import chefmeal.chefmeal.Fragments.FragmentAll;
import chefmeal.chefmeal.Fragments.FragmentDessert;
import chefmeal.chefmeal.Fragments.FragmentMainDish;
import chefmeal.chefmeal.Fragments.FragmentStarter;


public class RecipesListActivity extends AppCompatActivity
        implements FragmentAll.OnFragmentInteractionListener,
        FragmentStarter.OnFragmentInteractionListener,
        FragmentMainDish.OnFragmentInteractionListener,
        FragmentDessert.OnFragmentInteractionListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Création de chaque fragments de vue, avec leurs titre dans un tableau
        adapter.AddFragment(new FragmentAll(), "Toutes");
        adapter.AddFragment(new FragmentStarter(), "Entrées");
        adapter.AddFragment(new FragmentMainDish(), "Plats");
        adapter.AddFragment(new FragmentDessert(), "Desserts");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    // Appuyer sur la flèche en haut de l'écran permet de faire un retour sur l'ancienne page
    public void BackToHome(View view){
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
