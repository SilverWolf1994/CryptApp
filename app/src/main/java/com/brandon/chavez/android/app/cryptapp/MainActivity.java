package com.brandon.chavez.android.app.cryptapp;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        private View rootView = null;
        private TableLayout tlEncriptacionColumnarA;
        private LinearLayout llEncriptarA, llDesencriptarA;
        private RadioButton rbEncriptarA, rbDesencriptarA;
        private EditText etClaveEncriptarA, etTextoEncriptarA, etTextoDesencriptarA;
        private TextView tvTextoEncriptarA, tvTextoDesencriptarA;
        private FloatingActionButton fabPolialfabetoA, fabColumnarA;

        private HashMap<String, String> alfabeto_impar = new HashMap<>();
        private HashMap<String, String> alfabeto_par = new HashMap<>();

        private ArrayList<String> alfabeto = new ArrayList<>();
        private Vector vectorLetrasClave;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_fragment";

        public PlaceholderFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
                rootView = inflater.inflate(R.layout.fragment_polialfabeto, container, false);
                llEncriptarA = (LinearLayout) rootView.findViewById(R.id.fp_llEncriptar);
                llDesencriptarA = (LinearLayout) rootView.findViewById(R.id.fp_llDesencriptar);
                rbEncriptarA = (RadioButton) rootView.findViewById(R.id.fp_rbEncriptar);
                rbDesencriptarA = (RadioButton) rootView.findViewById(R.id.fp_rbDesencriptar);
                etTextoEncriptarA = (EditText) rootView.findViewById(R.id.fp_etTextoEncriptar);
                etTextoDesencriptarA = (EditText) rootView.findViewById(R.id.fp_etTextoDesencriptar);
                tvTextoEncriptarA = (TextView) rootView.findViewById(R.id.fp_tvTextoEncriptar);
                tvTextoDesencriptarA = (TextView) rootView.findViewById(R.id.fp_tvTextoDesencriptar);
                fabPolialfabetoA = (FloatingActionButton) rootView.findViewById(R.id.fp_fabPolialfabeto);

                cargarAlfabetoImpar(); cargarAlfabetoPar();

                rbEncriptarA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fp_layout_visible();
                    }
                });

                rbDesencriptarA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fp_layout_visible();
                    }
                });

                fabPolialfabetoA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String texto;
                        if (rbEncriptarA.isChecked()) {
                            texto = etTextoEncriptarA.getText().toString();
                            tvTextoEncriptarA.setText(encriptarTexto(texto));
                            etTextoDesencriptarA.setText(tvTextoEncriptarA.getText());
                        } else if (rbDesencriptarA.isChecked()) {
                            texto = etTextoDesencriptarA.getText().toString();
                            tvTextoDesencriptarA.setText(desencriptarTexto(texto));
                            etTextoEncriptarA.setText(tvTextoDesencriptarA.getText());
                        }
                    }
                });

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_columnar, container, false);
                tlEncriptacionColumnarA = (TableLayout) rootView.findViewById(R.id.fc_tlEncriptacionColumnar);
                llEncriptarA = (LinearLayout) rootView.findViewById(R.id.fc_llEncriptar);
                //llDesencriptarA = (LinearLayout) rootView.findViewById(R.id.fc_llDesencriptar);
                rbEncriptarA = (RadioButton) rootView.findViewById(R.id.fc_rbEncriptar);
                rbDesencriptarA = (RadioButton) rootView.findViewById(R.id.fc_rbDesencriptar);
                etClaveEncriptarA = (EditText) rootView.findViewById(R.id.fc_etClaveEncriptar);
                etTextoEncriptarA = (EditText) rootView.findViewById(R.id.fc_etTextoEncriptar);
                tvTextoEncriptarA = (TextView) rootView.findViewById(R.id.fc_tvTextoEncriptar);
                fabColumnarA = (FloatingActionButton) rootView.findViewById(R.id.fc_fabColumnar);

                cargarAlfabeto();

                fabColumnarA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String clave, texto;
                        if (rbEncriptarA.isChecked()) {
                            try {
                                clave = etClaveEncriptarA.getText().toString();
                                texto = etTextoEncriptarA.getText().toString();
                                tlEncriptacionColumnarA.removeAllViewsInLayout();
                                encriptarTextoColumnar(clave, texto);
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else if (rbDesencriptarA.isChecked()) {
                            Toast.makeText(getContext(), "Por el momento solamente se puede encriptar :D", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            return rootView;
        }


        // POLIALFABETO //
        public String encriptarTexto(String texto) {
            String textoEncriptado = "", letraEncriptada = "";
            for (int posicionLetra = 1; posicionLetra < texto.length() + 1; posicionLetra++) {
                if (posicionLetra % 2 == 0) {
                    letraEncriptada = alfabeto_par.get(String.valueOf(texto.charAt(posicionLetra - 1)));
                } else {
                    letraEncriptada = alfabeto_impar.get(String.valueOf(texto.charAt(posicionLetra - 1)));
                }
                textoEncriptado = textoEncriptado + letraEncriptada;
            }

            return textoEncriptado;
        }

        public String desencriptarTexto(String texto) {
            String textoDesencriptado = "", letraDesencriptada = "";
            for (int posicionLetra = 1; posicionLetra < texto.length() + 1; posicionLetra++) {
                if (posicionLetra % 2 == 0) {
                    for (Map.Entry<String, String> entry: alfabeto_par.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(String.valueOf(texto.charAt(posicionLetra - 1)))) {
                            letraDesencriptada = entry.getKey();
                        }
                    }
                } else {
                    for (Map.Entry<String, String> entry: alfabeto_impar.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(String.valueOf(texto.charAt(posicionLetra - 1)))) {
                            letraDesencriptada = entry.getKey();
                        }
                    }
                }
                textoDesencriptado = textoDesencriptado + letraDesencriptada;
            }

            return textoDesencriptado;
        }

        public void fp_layout_visible() {
            if (rbEncriptarA.isChecked()) {
                llEncriptarA.setVisibility(rootView.VISIBLE);
                llDesencriptarA.setVisibility(rootView.GONE);
            } else if (rbDesencriptarA.isChecked()) {
                llDesencriptarA.setVisibility(rootView.VISIBLE);
                llEncriptarA.setVisibility(rootView.GONE);
            }
        }

        public void cargarAlfabetoImpar() {
            alfabeto_impar.put("A", "a");alfabeto_impar.put("B", "e");alfabeto_impar.put("C", "i");
            alfabeto_impar.put("D", "m");alfabeto_impar.put("E", "p");alfabeto_impar.put("F", "t");
            alfabeto_impar.put("G", "x");alfabeto_impar.put("H", "b");alfabeto_impar.put("I", "f");
            alfabeto_impar.put("J", "j");alfabeto_impar.put("K", "n");alfabeto_impar.put("L", "q");
            alfabeto_impar.put("M", "u");alfabeto_impar.put("N", "y");alfabeto_impar.put("Ñ", "c");
            alfabeto_impar.put("O", "g");alfabeto_impar.put("P", "k");alfabeto_impar.put("Q", "ñ");
            alfabeto_impar.put("R", "r");alfabeto_impar.put("S", "v");alfabeto_impar.put("T", "z");
            alfabeto_impar.put("U", "d");alfabeto_impar.put("V", "h");alfabeto_impar.put("W", "l");
            alfabeto_impar.put("X", "o");alfabeto_impar.put("Y", "s");alfabeto_impar.put("Z", "w");
        }

        public void cargarAlfabetoPar() {
            alfabeto_par.put("A", "n");alfabeto_par.put("B", "r");alfabeto_par.put("C", "w");
            alfabeto_par.put("D", "b");alfabeto_par.put("E", "g");alfabeto_par.put("F", "l");
            alfabeto_par.put("G", "p");alfabeto_par.put("H", "u");alfabeto_par.put("I", "z");
            alfabeto_par.put("J", "e");alfabeto_par.put("K", "j");alfabeto_par.put("L", "ñ");
            alfabeto_par.put("M", "s");alfabeto_par.put("N", "x");alfabeto_par.put("Ñ", "c");
            alfabeto_par.put("O", "h");alfabeto_par.put("P", "m");alfabeto_par.put("Q", "q");
            alfabeto_par.put("R", "v");alfabeto_par.put("S", "a");alfabeto_par.put("T", "f");
            alfabeto_par.put("U", "k");alfabeto_par.put("V", "o");alfabeto_par.put("W", "t");
            alfabeto_par.put("X", "y");alfabeto_par.put("Y", "d");alfabeto_par.put("Z", "i");
        }
        // /POLIALFABETO //

        // COLUMNAR //
        public void encriptarTextoColumnar(String clave, String texto) {
            String encriptacionColumnar = "";
            int numLetrasClave = numeroLetras(clave);
            int numLetrasTexto = numeroLetras(texto);
            vectorLetrasClave = new Vector<>(numLetrasClave);

            cargarVectorPalabraClave(clave, numLetrasClave);
            insertarTableRowLetraClave(clave, numLetrasClave);

            try {

                if (numLetrasTexto % numLetrasClave == 0) {
                    String matriz[][] = new String[numLetrasTexto/numLetrasClave][numLetrasClave];

                    cargarMatriz(clave, texto, numLetrasClave, numLetrasTexto, matriz);

                    for (int filas = 0; filas < matriz.length; filas++)
                    {
                        TableRow tableRowTexto = new TableRow(getContext());
                        tableRowTexto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        tableRowTexto.setBackgroundColor(Color.rgb(100, 50, 120));
                        tableRowTexto.setPadding(30, 30, 30, 30);

                        for (int columnas = 0; columnas < matriz[filas].length; columnas++)
                        {
                            TextView tvLetra = new TextView(getContext());
                            tvLetra.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tvLetra.setPadding(30, 30, 30, 30);
                            tvLetra.setTextColor(Color.GREEN);
                            tvLetra.setTextSize(18);
                            tvLetra.setText(matriz[filas][columnas]);
                            tableRowTexto.addView(tvLetra);
                            encriptacionColumnar = encriptacionColumnar + matriz[filas][columnas];
                        }
                        tlEncriptacionColumnarA.addView(tableRowTexto);
                    }
                    tvTextoEncriptarA.setText(encriptacionColumnar);

                } else if (numLetrasTexto % numLetrasClave != 0) {
                    Toast.makeText(getContext(), "Hay filas incompletas, intente otro texto", Toast.LENGTH_LONG).show();
                    /*int indice_vector = 0;
                    int aux_vector = 0;
                    insertarTableRowLetraClave(clave, numLetrasClave);

                    for (int posicionLetraClave = 1; posicionLetraClave <= numLetrasTexto/numLetrasClave + 0; posicionLetraClave++)
                    {
                        TableRow tableRowTexto = new TableRow(getContext());
                        tableRowTexto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        tableRowTexto.setBackgroundColor(Color.rgb(100, 50, 120));
                        tableRowTexto.setPadding(30, 30, 30, 30);

                        for (int posicionLetraTexto = 1; posicionLetraTexto <= numLetrasClave; posicionLetraTexto++)
                        {
                            TextView tvLetra = new TextView(getContext());
                            tvLetra.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tvLetra.setPadding(30, 30, 30, 30);
                            tvLetra.setTextColor(Color.GREEN);
                            tvLetra.setTextSize(18);
                            if (String.valueOf(clave.charAt(posicionLetraTexto - 1)).equalsIgnoreCase(alfabeto.get((int)vectorLetrasClave.get(indice_vector))))
                            {
                                tvLetra.setText(String.valueOf(texto.charAt(aux_vector)));
                                aux_vector++;
                            }
                            if (posicionLetraClave == numLetrasTexto/numLetrasClave + 0)
                            {
                                indice_vector++;
                                posicionLetraClave = 0;
                                posicionLetraTexto = 0;
                            }
                            else
                            {
                                //tvLetra.setText("x");
                            }
                            //if (posicionLetraClave == 2 && posicionLetraTexto == numLetrasClave) { posicionLetraClave = 0; indice_vector++; }
                            tableRowTexto.addView(tvLetra);
                        }
                        tlEncriptacionColumnarA.addView(tableRowTexto);
                    }*/
                }

            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        public int numeroLetras(String cadena) {
            int numLetras = 0;
            for (int posicionLetra = 1; posicionLetra < cadena.length() + 1; posicionLetra++) {
                numLetras = posicionLetra;
            }
            return numLetras;
        }

        public void cargarMatriz(String clave, String texto, int numLetrasClave, int numLetrasTexto, String[][] matriz) {
            String letraAsignada = "";
            int indice_vector = 0;
            int aux_vector = 0;
            int aux_clave = 0;
            int aux_ite = 0;

            for (int posicionLetraClave = 1; posicionLetraClave <= numLetrasTexto/numLetrasClave; posicionLetraClave++)
            {
                for (int posicionLetraTexto = 1; posicionLetraTexto <= numLetrasClave; posicionLetraTexto++)
                {
                    if ((alfabeto.get(indice_vector)).equalsIgnoreCase(String.valueOf(clave.charAt(posicionLetraTexto - 1))))
                    {
                        letraAsignada = String.valueOf(texto.charAt(aux_vector + aux_ite));
                        aux_vector = aux_vector + (numLetrasTexto/numLetrasClave);
                        aux_clave++;
                    }
                    else
                    {
                        if (matriz[posicionLetraClave-1][posicionLetraTexto-1] == null)
                        {
                            letraAsignada = "x";
                        }
                        else
                        {
                            letraAsignada = matriz[posicionLetraClave-1][posicionLetraTexto-1];
                        }
                    }
                    matriz[posicionLetraClave-1][posicionLetraTexto-1] = letraAsignada;
                    if (posicionLetraTexto == numLetrasClave)
                    {
                        if (aux_clave == numLetrasClave)
                        {
                            aux_ite++;
                            aux_vector = 0;
                            aux_clave = 0;
                            indice_vector = 0;
                        }
                        else
                        {
                            indice_vector++;
                            posicionLetraTexto = 0;
                        }
                    }
                }
            }
        }

        public void insertarTableRowLetraClave(String clave, int numLetrasClave) {
            TableRow tableRowClave = new TableRow(getContext());
            tableRowClave.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRowClave.setBackgroundColor(Color.rgb(100, 50, 200));
            tableRowClave.setPadding(30, 30, 30, 30);
            for (int posicionLetra = 1; posicionLetra <= numLetrasClave; posicionLetra++)
            {
                TextView tvLetra = new TextView(getContext());
                tvLetra.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tvLetra.setPadding(30, 30, 30, 30);
                tvLetra.setTextColor(Color.WHITE);
                tvLetra.setTextSize(18);
                tvLetra.setText(String.valueOf(clave.charAt(posicionLetra - 1)));
                tableRowClave.addView(tvLetra);
            }
            tlEncriptacionColumnarA.addView(tableRowClave);
        }

        public void cargarVectorPalabraClave(String clave, int numLetrasClave) {
            int indice = 0;
            int aux = 0;
            for (int posicionLetraClaveIndice = 1; posicionLetraClaveIndice <= numLetrasClave; posicionLetraClaveIndice++)
            {
                if (indice == alfabeto.indexOf(String.valueOf(clave.charAt(posicionLetraClaveIndice - 1))))
                {
                    aux++;
                    vectorLetrasClave.add(alfabeto.indexOf(String.valueOf(clave.charAt(posicionLetraClaveIndice - 1))));
                }
                if (posicionLetraClaveIndice == numLetrasClave)
                {
                    if (aux != numLetrasClave)
                    {
                        indice++;
                        posicionLetraClaveIndice = 0;
                    }
                }
            }
        }

        public void cargarAlfabeto() {
            alfabeto.add("A");alfabeto.add("B");alfabeto.add("C");alfabeto.add("D");alfabeto.add("E");alfabeto.add("F");
            alfabeto.add("G");alfabeto.add("H");alfabeto.add("I");alfabeto.add("J");alfabeto.add("K");alfabeto.add("L");
            alfabeto.add("M");alfabeto.add("N");alfabeto.add("Ñ");alfabeto.add("O");alfabeto.add("P");alfabeto.add("Q");
            alfabeto.add("R");alfabeto.add("S");alfabeto.add("T");alfabeto.add("U");alfabeto.add("V");alfabeto.add("W");
            alfabeto.add("X");alfabeto.add("Y");alfabeto.add("Z");
        }
        // /COLUMNAR //
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "POLIALFABETO";
                case 1:
                    return "COLUMNAR";
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
