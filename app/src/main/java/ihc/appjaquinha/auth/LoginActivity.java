package ihc.appjaquinha.auth;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.User;

public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnLoginInteractionListener, RegisterFragment.OnRegisterInteractionListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Fragment fragmentLogin = new LoginFragment();
    Fragment fragmentRegister = new RegisterFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addFragment(fragmentLogin);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser); //comentar aqui caso nao queira conectar direto ao abrir
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("LoginActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("LoginActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    private void loginUser(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        testTask(task, "signInWithEmail", null);
                    }
                });
    }

    private void registerUser(String email, String senha, String nome, String data, String sexo, float peso, int altura){
        final User user = new User(email, nome, data, sexo, peso, altura);
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        testTask(task, "createUserWithEmail", user);
                    }
                });
    }

    private void testTask(Task<AuthResult> task, String logTask, User user){
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d("SUCCESS", logTask + ":success");
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if(user != null) writeNewUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), user);
            updateUI(firebaseUser);
        } else {
            // If sign in fails, display a message to the user.
            Log.w("FAILURE", logTask + ":failure", task.getException());
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    private void writeNewUser(final String userId, final User user) {
        Query dataUser = mDatabase.child("users").orderByKey().equalTo(userId);
        dataUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() < 1){
                    mDatabase.child("users").child(userId).setValue(user);
                    Log.d("NEWUSER", "true");
                } else Log.d("NEWUSER", "false");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "onCancelled", databaseError.toException());
            }
        });
    }

    private void updateUI(FirebaseUser firebaseUser){
        if(firebaseUser != null){
            startActivity(new Intent(LoginActivity.this, ContainerActivity.class));
        }
    }

    @Override
    public void onLoginInteraction(String email, String senha) {
        loginUser(email, senha);
    }

    @Override
    public void onClickFragmentRegister() {
        replaceFragment(fragmentRegister);
    }

    @Override
    public void onRegisterInteraction(String email, String senha, String nome, String data, String sexo, float peso, int altura) {
        registerUser(email, senha, nome, data, sexo, peso, altura);
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

    public static Calendar stringToDate(String d, String f){
        if(d == null || f == null || f.isEmpty()) return null;
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(f, Locale.ENGLISH);
            cal.setTime(formatter.parse(d));
        } catch (ParseException e) {
            cal = null;
        }
        return cal;
    }

    public static String dateToString(Calendar d, String f){
        if(d == null || f == null || f.isEmpty()) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(f, Locale.ENGLISH);
        return formatter.format(d.getTime());
    }
}
