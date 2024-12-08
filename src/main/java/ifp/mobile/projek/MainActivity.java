package ifp.mobile.projek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private BookmarkFragment bookmarkFragment = new BookmarkFragment();
    private HomeFragment homeFragment = new HomeFragment();

    private TrackFragment lacakFragment = new TrackFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
//        bottomNavigationView.setSelectedItemId(R.id.bookmark);

        if (savedInstanceState == null) { // Only set fragment if it's the first launch
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, homeFragment)
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.home); // Set default selected item
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.bookmark) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, bookmarkFragment)
                    .commit();
            return true;
        }
        else if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, homeFragment)
                    .commit();
            return true;
        }
        else if (item.getItemId() == R.id.lacak) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, lacakFragment)
                    .commit();
            return true;
        }
        else {
            return false;
        }
    }
}