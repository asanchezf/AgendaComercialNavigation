package activitys;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Beans.Clientes;

/**
 * Created by Susana on 24/11/2015.
 */

/*
Clase utilizada para leer los objetos Json que devuelve el WS. Se utiliza la librería Gson...
* */
public class GsonClientesParser {


    public ArrayList<Clientes> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        ArrayList<Clientes> animales = new ArrayList<>();

        // Iniciar el array
        reader.beginArray();

        while (reader.hasNext()) {
            // Lectura de objetos
            Clientes cliente = gson.fromJson(reader, Clientes.class);
            animales.add(cliente);
        }


        reader.endArray();
        reader.close();
        return animales;
    }




}
