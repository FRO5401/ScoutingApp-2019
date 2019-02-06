package com.example.nyarlathotech.froscouting;

import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


//This class creates the MainActivity for the context that is gotten later on
//Also creates the navigation bar
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //After toolbar is instantiated, this allows for the navigation and toolbar to be created
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //This loads the homepage when the app initially loads!
        HomeFragment home = new HomeFragment();
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FragMain, home).commit();
    }

    //Method makes sure that the user cannot back out of the scouting/creation tabs unless they wish to via the Navigation menu!
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please use the Navigation Menu", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        //Each one of these if statements allow for another class to load, thus generating a new page
        //Each name correlates to the page that the tab opens
        if (id == R.id.nav_home) {
            HomeFragment home = new HomeFragment();
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.FragMain, home).commit();
            Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_scouting) {
            PreGame scout = new PreGame();
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.FragMain, scout).commit();
            Toast.makeText(this, "SCOUTING", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_create){
            UnlockCreate uc = new UnlockCreate();
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.FragMain, uc).commit();
            Toast.makeText(this, "TEAMS LIST", Toast.LENGTH_SHORT).show();
        }

        //Though the fragment should NEVER be null, this is an exception that allows the app not to crash
        if(fragment != null){
            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.FragMain, fragment);
        }

        //This is for the navigation drawer to close
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
