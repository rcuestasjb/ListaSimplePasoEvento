package com.example.listasimplepasoevento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

//1) Definicion de la clase evento; esta clase la utilizaremos para rellenar una lista
class Evento {
    private String categoria;
    private String descripcion;
    private String dia;
    private String hora;
    private String id;
    private String titulo;
    public Evento(String titulo) {
        this.titulo = titulo;
    }
    public String getTitulo() { return this.titulo; }
}
public class MainActivity extends AppCompatActivity {

    List<Evento> listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //1) Creación de la activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2) Obtenemos el id del list View (con el id definido en el layout), mas tarde la rellenaremos
        ListView listView = (ListView) findViewById(R.id.listViewEventos);

        //3) Vamos a leer el fichero lista json del a carpeta de assets
        String json ="";
        try {
            InputStream stream = getAssets().open("lista.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json  = new String(buffer);
        } catch (Exception e) { }

        //4) En el string json ahora tenemos el listado como texto, en la siguente linea vamos a parsear
        //el string y convertirlo en una lista de objetos eventos
        listaEventos = Arrays.asList(new Gson().fromJson(json, Evento[].class));

        //5) En la listaEventos tenemos todos los objetos (con todos los campos), vamos a quedarnos solo con el campo que queremos
        //mostrar en la lista  por ejemplo el titulo
        String[] arrayTitulos = new String[listaEventos.size()];
        for (int i = 0; i < listaEventos.size(); i++) {
            arrayTitulos[i] = listaEventos.get(i).getTitulo();
        }

        //6) Vamos a rellenar el component listView
        ListView listView1 = (ListView) findViewById(R.id.listViewEventos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayTitulos);
        listView1.setAdapter(adapter);

        //7) Vamos a definir el evento que se disparará cuando cliquemos un item de la lista
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí pillamos el texto del item de la lista
                String item = ((TextView)view).getText().toString();
                // Aquí pillamos el objeto de la lista global
                Evento eventoSeleccionado = MainActivity.this.listaEventos.get(position);
                // Aquí lo mostramos en una etiqueta
                Toast.makeText(getBaseContext(), "Este es el titulo del item de la lista: "+ item
                        + " \nEsta es la posicion "+position
                        + " \ny este es el titulo del ojeto !!!!"+eventoSeleccionado.getTitulo() ,
                        Toast.LENGTH_LONG).show();

                // Y aqui tenemos que recojer el el objeto eventoSeleccionado ...
                // serializarlo en json utilizando la biblioteca GSON ...
                // y pasarlo al activity de visualización mediante el Inten.
                // pero aqui ya hay buscarse la vida ....
                // ten en cuenta que todo lo anterioemente descrio ja esta echo en
                // en el ejemplo 2 https://github.com/rcuestasjb/PasoObjetos.git

            }
        });
    }
}
