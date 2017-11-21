package ihc.appjaquinha.container;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ihc.appjaquinha.R;
import ihc.appjaquinha.database.comida.Alimento;

public class SearchBar extends RelativeLayout {

    private Context mContext;
    private ImageButton xlimparBt;
    private ImageButton lupaIV;
    private AutoCompleteTextView act;
    private SearchBarListener mCallBack;
    private List<Alimento> alimentos;

    //Interface do Listener
    //Ativado quando clica em um item do dropdown do AutoCompleteTextView, AKA act.
    public interface SearchBarListener {
        void onDropDownItemClicked(int indiceOriginal);
        void onSearchBarClickedOrSomethingTyped();
    }

    public SearchBar(Context context) {
        super(context);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setupSearchBar(Context context, SearchBarListener sbl, List<Alimento> alimentos){
        this.alimentos = alimentos;
        if(mContext == null || mCallBack == null) {
            initializeViews(context, sbl);
            setupOfChildren();
        } else{
            changeArrayAdapter(alimentos);
        }
    }

    private void initializeViews(Context context, SearchBarListener sbl) {
        mContext = context;
        try {
            mCallBack = sbl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.searchbar_layout, this);
        }

        xlimparBt = this.findViewById(R.id.xlimpa);
        lupaIV = this.findViewById(R.id.lupaSearch);
        act = this.findViewById(R.id.autoCompleteTextView);
    }

    public ArrayList<String> alimentosNomes(List<Alimento> alimentos) {
        ArrayList<String> nomesLocais = new ArrayList<>();
        for(Alimento alimento : alimentos) {
            nomesLocais.add(alimento.getNome());
        }
        return nomesLocais;
    }

    public void changeArrayAdapter(List<Alimento> alimentos) {
       setupAdapter(alimentosNomes(alimentos));
       refreshDrawableState();
    }

    private void setupOfChildren() {
        setupAdapter(alimentosNomes(alimentos));
        //showKeyboard();
        xlimparBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                act.setText("");
            }
        });
    }

    private void setupAdapter(final ArrayList<String> alimentosNomes) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                R.layout.custom_dropdown, alimentosNomes);
        act.setAdapter(arrayAdapter);
        //Caso preciso setar numero de caracteres esperados antes de oferecer sugestões, o minimo é 1
        act.setThreshold(1);
        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String alimentoNome = (String) parent.getItemAtPosition(position);
                //Para encontar o incide desse objeto no array não filtrado
                int indexOriginal = alimentosNomes.indexOf(alimentoNome);
                mCallBack.onDropDownItemClicked(indexOriginal);
            }
        });

        act.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.onSearchBarClickedOrSomethingTyped();
            }
        });
        act.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(count != 0) {
                    crossFade(xlimparBt, lupaIV);
                }
                else {
                    crossFade(lupaIV, xlimparBt);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                mCallBack.onSearchBarClickedOrSomethingTyped();

            }
        });
    }

    //Se lupa tiver visivel entao x nao estará, assim invertemos a visibilidade
    private void crossFade(View aparecendo, View desaparecendo) {
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        if(desaparecendo.getVisibility() == VISIBLE) {
            desaparecendo.setVisibility(GONE);
            aparecendo.setAlpha(0f);
            aparecendo.setVisibility(VISIBLE);
            aparecendo.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        }
    }

    public void dismissKeyboard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(act.getWindowToken(), 0);
    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(act, 0);
    }

    public void showDropDown() {
        act.showDropDown();
    }
}
