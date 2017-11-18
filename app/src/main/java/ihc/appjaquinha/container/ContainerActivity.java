package ihc.appjaquinha.container;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.CommonStatusCodes;
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

import java.util.Calendar;
import java.util.List;

import ihc.appjaquinha.R;
import ihc.appjaquinha.auth.LoginActivity;
import ihc.appjaquinha.camera.OcrCaptureActivity;
import ihc.appjaquinha.container.alimento.AlimentoFragment;
import ihc.appjaquinha.container.configuracoes.ConfiguracoesFragment;
import ihc.appjaquinha.container.diario.DiarioFragment;
import ihc.appjaquinha.container.estatisticas.EstatisticasFragment;
import ihc.appjaquinha.container.geladeira.GeladeiraFragment;
import ihc.appjaquinha.container.home.HomeFragment;
import ihc.appjaquinha.container.objetivos.ObjetivosFragment;
import ihc.appjaquinha.container.restricoes.RestricoesFragment;
import ihc.appjaquinha.database.comida.Alimento;
import ihc.appjaquinha.database.comida.diario.ConsumoDia;
import ihc.appjaquinha.database.comida.diario.Diario;
import ihc.appjaquinha.database.comida.Geladeira;
import ihc.appjaquinha.database.User;


public class ContainerActivity extends AppCompatActivity
    implements HomeFragment.HomeOnClickListener,
        AlimentoFragment.AlimentoOnClickListener,
        GeladeiraFragment.GeladeiraOnClickListener {

    private static final String TAG = ContainerActivity.class.getSimpleName();
    private Toolbar toolbar;
    private Drawer navDrawer;

    private DatabaseReference mDatabase;
    public static User user;

    private static final int RC_OCR_CAPTURE = 9003;

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
                    Log.d("USER", "processado");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "onCancelled", databaseError.toException());
            }
        });

        Fragment fragment = new HomeFragment();
        addFragment(fragment);
        Calendar data_atual = Calendar.getInstance();
        ((HomeFragment) fragment).setData(LoginActivity.dateToString(data_atual, "dd/MM/yyyy"));
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
            /*Fragment fragment = getVisibleFragment();
            int position = 0;
            if (fragment instanceof DiarioFragment){
                position = 1;
            } else if (fragment instanceof GeladeiraFragment){
                position = 2;
            } else if (fragment instanceof ObjetivosFragment){
                position = 3;
            } else if (fragment instanceof EstatisticasFragment){
                position = 4;
            } else if (fragment instanceof RestricoesFragment){
                position = 5;
            } else if (fragment instanceof ConfiguracoesFragment){
                position = 6;
            }
            navDrawer.setSelectionAtPosition(position, false);*/
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            String status, message = "";
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    message = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    status =  getResources().getString(R.string.ocr_success);
                    Log.d(TAG, "Text read: " + message);
                } else {
                    status = getResources().getString(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                status = (String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(status)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // confirmado -> novo alimento
                            Fragment fragment = getVisibleFragment();
                            if (fragment instanceof AlimentoFragment){
                                ((AlimentoFragment) fragment).fillForm(new Alimento());
                            } else{
                                replaceFragment(new AlimentoFragment());
                            }
                        }
                    })
                    .setNeutralButton(R.string.camera, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            launchCamera();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
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

        switch (position) {
            case 0:
                launchHome();
                break;
            case 1:
                replaceFragment(new DiarioFragment());
                break;
            case 2:
                replaceFragment(new GeladeiraFragment());
                break;
            case 3:
                replaceFragment(new ObjetivosFragment());
                break;
            case 4:
                replaceFragment(new EstatisticasFragment());
                break;
            case 5:
                replaceFragment(new RestricoesFragment());
                break;
            case 6:
                replaceFragment(new ConfiguracoesFragment());
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
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public Fragment getVisibleFragment(){
        android.support.v4.app.FragmentManager fragmentManager = ContainerActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private void launchCamera(){
        // launch Ocr capture activity.
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        //intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
        //intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    private void launchHome(){
        Calendar data_atual = Calendar.getInstance();
        launchHome(LoginActivity.dateToString(data_atual, "dd/MM/yyyy"));
    }

    private void launchHome(String data){
        Fragment fragment = new HomeFragment();
        replaceFragment(fragment);
        ((HomeFragment) fragment).setData(data);
    }

    private void addAlimentoDiario(final Alimento alimento, final String data){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        final EditText input = new EditText(ContainerActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setTitle("Determine a quantidade (g)")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        float qtd = Float.parseFloat(input.getText().toString());
                        Diario diario = user.getDiario();
                        List<ConsumoDia> consumoDiaList = diario.getConsumoDiaList();
                        int i;
                        for (i = consumoDiaList.size() - 1; i >= 0; i--) {
                            ConsumoDia dia = consumoDiaList.get(i);
                            if(dia.getData().equals(data)){
                                dia.addAlimento(alimento, qtd);
                                break;
                            }
                        }
                        if(i < 0){
                            ConsumoDia consumoDia = new ConsumoDia();
                            consumoDia.setData(data);
                            consumoDia.addAlimento(alimento, qtd);
                            consumoDiaList.add(consumoDia);
                        }
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onCameraSelected() {
        launchCamera();
    }

    @Override
    public void onAlimentoSelected() {
        replaceFragment(new AlimentoFragment());
    }

    @Override
    public void onAlimentoCreated(Alimento alimento) {
        onBackPressed();

        Geladeira geladeira = user.getGeladeira();
        geladeira.addAlimento(alimento);

        Fragment fragment = getVisibleFragment();
        if (fragment instanceof HomeFragment){
            addAlimentoDiario(alimento, ((HomeFragment) fragment).getData());
        } else{
            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
        }
    }

}
