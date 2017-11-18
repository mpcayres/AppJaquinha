package ihc.appjaquinha.container;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ihc.appjaquinha.R;
import ihc.appjaquinha.database.User;


public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = ContainerActivity.class.getSimpleName();
    private Toolbar toolbar;
    private Drawer navDrawer;

    private DatabaseReference mDatabase;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home);

        setupToolbar();
        setupNavDrawer();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query dataUser = mDatabase.child("users").orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "onCancelled", databaseError.toException());
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HomeFragment());
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawer.openDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.nav_drawer_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupNavDrawer() {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.nav_drawer_home)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.nav_drawer_diario)
                .withTextColor(getResources().getColor(R.color.white))
                .withIcon(R.drawable.nav_drawer_diario)
                .withSelectedIcon(R.drawable.nav_drawer_diario_selected);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.nav_drawer_geladeira)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4)
                .withName(R.string.nav_drawer_objetivos)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5)
                .withName(R.string.nav_drawer_estatisticas)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6)
                .withName(R.string.nav_drawer_restricoes)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7)
                .withName(R.string.nav_drawer_configuracoes)
                .withIcon(R.drawable.nav_drawer_settings)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.nav_drawer_settings_selected);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(8)
                .withName(R.string.nav_drawer_exit)
                .withIcon(R.drawable.newadv_fechar)
                .withTextColor(getResources().getColor(R.color.white))
                .withSelectedIcon(R.drawable.newadv_fechar);

        //create the drawer and remember the `Drawer` navDrawer object
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.drawable.nav_drawer_bg))
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        item6,
                        item7,
                        item8
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switchFragment(position);
                        Log.d(TAG, position + " Clicked");
                        navDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();
    }

    private void switchFragment(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                ft.replace(R.id.container, new HomeFragment()).commit();
                break;
            case 1:
                ft.replace(R.id.container, new DiarioFragment()).commit();
                break;
            case 2:
                ft.replace(R.id.container, new GeladeiraFragment()).commit();
                break;
            case 3:
                ft.replace(R.id.container, new ObjetivosFragment()).commit();
                break;
            case 4:
                ft.replace(R.id.container, new EstatisticasFragment()).commit();
                break;
            case 5:
                ft.replace(R.id.container, new RestricoesFragment()).commit();
                break;
            case 6:
                ft.replace(R.id.container, new ConfiguracoesFragment()).commit();
                break;
            case 7:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            default:
                break;
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout_fragments, fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_fragments, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
