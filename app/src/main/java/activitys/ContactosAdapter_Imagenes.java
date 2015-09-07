package activitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigatio.R;

import java.util.ArrayList;

import Beans.Contactos;
//import antonio.ejemplos.agendacomercial.R;


public class ContactosAdapter_Imagenes extends ArrayAdapter<Contactos> {

    public ContactosAdapter_Imagenes(Context context, ArrayList<Contactos> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml 
            listItemView = inflater.inflate(R.layout.image_list_item_3,parent,false);
        }

      //Obteniendo instancias de los elementos
        TextView titulo = (TextView)listItemView.findViewById(R.id.text1);//Se alimenta de contactos.Nombre
        TextView subtitulo = (TextView)listItemView.findViewById(R.id.text2);//Se alimenta de contactos.Email
        TextView descripcion = (TextView)listItemView.findViewById(R.id.text3);//Se alimenta de contactos.Email
        ImageView categoria = (ImageView)listItemView.findViewById(R.id.category);//Por src de su layout tien una imagen por defecto.
        TextView telefono = (TextView)listItemView.findViewById(R.id.text4);

/*
        *Id_Zona:
        *          1:Alcorcón y alrededores
                * 			2:Madrid capital
        * 			3:Madrid CC.AA.
                * 			4:Otra CC.AA..
        * 			5:Otro País
        * 		    6:Otros
                * valorar posibilidad de crear otra tabla...
        * */


//        categoria.setImageResource(R.drawable.amigos);
//        categoria.setImageResource(R.drawable.colores);
//        categoria.setImageResource(R.drawable.companeros);
//        categoria.setImageResource(R.drawable.otros);
//        categoria.setImageResource(R.drawable.image1);
        
        //Obteniendo instancia de la Tarea en la posici�n actual
        Contactos contactos = getItem(position);

        titulo.setText(contactos.getNombre());
        subtitulo.setText(contactos.getEmail());
        telefono.setText(contactos.getTelefono());
        
        if(contactos.getId_Categoria()==1){
            descripcion.setText("Alcorcón");
            categoria.setImageResource(R.drawable.furgopeque);
        }
        else if (contactos.getId_Categoria()==2){
            descripcion.setText("Madrid capital");
            categoria.setImageResource(R.drawable.furgonew);
        	
        }else if (contactos.getId_Categoria()==3){
            descripcion.setText("Madrid CC.AA.");
            categoria.setImageResource(R.drawable.trolle);

        }else if (contactos.getId_Categoria()==4){
            descripcion.setText("Otra CC.AA.");
            categoria.setImageResource(R.drawable.train);
        }
        
        else if (contactos.getId_Categoria()==5){
            descripcion.setText("Otro país");
            categoria.setImageResource(R.drawable.mundo);
        }


        else if (contactos.getId_Categoria()==6){
            descripcion.setText("SIN ZONA");
            categoria.setImageResource(R.drawable.importado);
        }
        		
        //telefono.setText(""+ contactos.getId_Categoria());
        //telefono.setText(String.valueOf(contactos.getId_Categoria()));	
        	
        
        
    	//Recogemos los valores que se introduzcan en el edittext.
//		String valor=txtEntrada.getText().toString();
//		apuesta=Integer.parseInt(valor);
        
        //categoria.setImageResource(item.getCategoria());

        //Devolver al ListView la fila creada
        return listItemView;

    }
    
    @Override
    public long getItemId(int position)
    {
        return getItem(position).get_id();
    }
    
}
