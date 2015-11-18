package activitys;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigatio.R;

import java.util.ArrayList;
import java.util.Locale;

import Beans.Contactos;

/**
 Adaptador que devuelve controles a una listview. Puede recibir filtrados desde un Searchrview de la activity donde está la listview y personaliza la letra de uno de los
 EditText, cambiando el color de la letra de los caracteres introducidos....
 */
public class Contacts_Adapter_Images_Search extends ArrayAdapter<Contactos> implements Filterable {

    private ActivityLista activity;
    private FriendFilter friendFilter;//Clase para gestionar el filtrado
    // private Typeface typeface;
    private ArrayList<Contactos> friendList;//Contactos  sin filtrar
    private ArrayList<Contactos> filteredList;//Contactos filtrados

    private String filter = "";//Caracteres introducidos para el filtrado
    private String itemValue = "";//Nombre completo que aparece en el textview del nombre

    private final String noTieneImail="Email no disponible";//Se trae por defecto al importar contactos o al dar de alta


    public Contacts_Adapter_Images_Search(ActivityLista activity, ArrayList<Contactos> friendList) {

        super(activity, 0, friendList);

        this.activity = activity;
        this.friendList = friendList;
        this.filteredList = friendList;

        getFilter();


    }


    @Override
    public int getCount() {
        return filteredList.size();
    }



    @Override
    public Contactos getItem(int i) {
        return filteredList.get(i);
    }




    @Override
    public long getItemId(int position)
   {
        return getItem(position).get_id();
    }
    ///////////////////

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;//Clase statica definida más abajo
        //final User user = (User) getItem(position);
        //Obteniendo instancia de la Tarea en la posici�n actual
        final Contactos contactos = getItem(position);



        //long contacto_id=getItem(position).get_id();//Recuperamos el id


        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.image_list_item_3, parent, false);

            holder = new ViewHolder();

            holder.titulo = (TextView) view.findViewById(R.id.text1);
            holder.subtitulo = (TextView) view.findViewById(R.id.text2);
            holder.descripcion = (TextView) view.findViewById(R.id.text3);
            holder.categoria = (ImageView) view.findViewById(R.id.category);
            holder.telefono = (TextView) view.findViewById(R.id.text4);

            holder.iconoEmail=(ImageView)view.findViewById(R.id.imageView2);

            view.setTag(holder);

        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        /*COMIENZA A PINTAR LAS VIEWS. EL CONTROL TITULO (CONTIENE EL NOMBRE) Y CUANDO SE HA REALIZADO UN FILTRADO EN EL SEARCHRVIEW SE MODIFICA
        PONIENDO LOS CARACTERES QUE COINCIDAN CON LA BÚSQUEDA DE OTRO COLOR...*/

       if (filter.toString().equals("")) {//Antes de haber realizado alguna filtración se pintan los controles asociados al listview sin modificaciones.
           holder.titulo.setText(contactos.getNombre());//Pinta el textview normal

        }

        else{//Ha habido filtrado: pinta los caracteres del textview que correspondan en otro color y el resto permanece igual

           //AQUI
           itemValue = contactos.getNombre();

           int startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
           int endPos = startPos + filter.length();

           if (startPos != -1) // This should always be true, just a sanity check
           {
               Spannable spannable = new SpannableString(itemValue);
               ColorStateList color = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});//No ponen bien los colores traidos desde res?
               TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, color, null);

               spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
               holder.titulo.setText(spannable);//Pinta los cambios
           } else
               holder.titulo.setText(itemValue);//El resto permanece igual

       }

        //Pinta el resto de los controles....

        //Si tiene email pinta un icono y si no lo tiene pinta otro
        if (!contactos.getEmail().equals(noTieneImail)) {
            holder.subtitulo.setText(contactos.getEmail());
            holder.iconoEmail.setImageResource(R.drawable.mail);
        }

        else if(contactos.getEmail().equals(noTieneImail)){
            holder.subtitulo.setText(noTieneImail);
            //holder.subtitulo.setText(contactos.getEmail());
            holder.iconoEmail.setImageResource(R.drawable.nomail);
        }

        holder.telefono.setText(contactos.getTelefono());

        /*DESCRIPCIÓN DE LAS ZONAS:
        *Id_Zona:
        *           1:Alcorcón y alrededores
        * 			2:Madrid capital
        * 			3:Madrid CC.AA.
        * 			4:Otra CC.AA..
        * 			5:Otro País
        * 		    6:IMPORTADO ANDROID..sin zona.
        * 		    7:SINCRONIZADO WEB...sin zona.
                * valorar posibilidad de crear otra tabla...
        * */
//TODO:crear strings para las descripciones de los tipos.
        if (contactos.getId_Categoria() == 1) {
            holder.descripcion.setText("Alcorcón");
            holder.categoria.setImageResource(R.drawable.furgopeque);
        } else if (contactos.getId_Categoria() == 2) {
            holder.descripcion.setText("Madrid capital");
            holder.categoria.setImageResource(R.drawable.furgonew);

        } else if (contactos.getId_Categoria() == 3) {
            holder.descripcion.setText("Madrid CC.AA.");
            holder.categoria.setImageResource(R.drawable.trolle);

        } else if (contactos.getId_Categoria() == 4) {
            holder.descripcion.setText("Otra CC.AA.");
            holder.categoria.setImageResource(R.drawable.train);
        } else if (contactos.getId_Categoria() == 5) {
            holder.descripcion.setText("Otro país");
            holder.categoria.setImageResource(R.drawable.mundo);
        } else if (contactos.getId_Categoria() == 6) {
            holder.descripcion.setText("IMPORTADO ANDROID");
            holder.categoria.setImageResource(R.drawable.importado);
        }else if (contactos.getId_Categoria() == 7) {
            holder.descripcion.setText("IMPORTADO WB");
            holder.categoria.setImageResource(R.drawable.sincronizado);
        }


        return view;
    }




    @Override
    public android.widget.Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }



    static class ViewHolder {//Establecer las views necesarias. No creamos getter y setter. Llamamos directamente a los atributos de la class desde fuera..
        TextView titulo;
        TextView subtitulo;
        TextView descripcion;
        ImageView categoria;
        TextView telefono;

        ImageView iconoEmail;

    }


    /**
     * Contenido en la lista según el texto de búsqueda
     */
    private class FriendFilter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            //String filter = "";
            //String itemValue = "";

            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Contactos> tempList = new ArrayList<Contactos>();

                // search content in friend list
                for (Contactos user : friendList) {//AQUI
                    if (user.getNombre().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);

                        filter=constraint.toString();
                        //itemValue=user.getNombre();


                    }
                }


                filterResults.count = tempList.size();
                filterResults.values = tempList;


            } else {
                filterResults.count = friendList.size();
                filterResults.values = friendList;
            }

            return filterResults;
        }

        /**
         * Se notifica a la lista
         * @param constraint texto introducido en el searchview
         * @param results resultado del filtrado
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredList = (ArrayList<Contactos>) results.values;

            notifyDataSetChanged();
        }
    }


//EJEMPLO 1
/*    public  static  CharSequence highlightText ( String search ,  String originalText )  {
        if  ( search !=  null  &&  ! search . equalsIgnoreCase ( "" ))  {
            String normalizedText =  Normalizer . normalize ( originalText ,  Normalizer . Form . NFD ). replaceAll ( "\\p{InCombiningDiacriticalMarks}+" ,  "" ). toLowerCase ();
            int start = normalizedText . indexOf ( search );
            if  ( start <  0 )  {
                return originalText ;
            }  else  {
                Spannable highlighted =  new  SpannableString ( originalText );
                while  ( start >=  0 )  {
                    int spanStart =  Math . min ( start , originalText . length ());
                    int spanEnd =  Math . min ( start + search . length (), originalText . length ());
                    highlighted . setSpan ( new  ForegroundColorSpan ( Color . BLUE ), spanStart , spanEnd ,  Spannable . SPAN_EXCLUSIVE_EXCLUSIVE );
                    start = normalizedText . indexOf ( search , spanEnd );
                }
                return highlighted ;
            }
        }
        return originalText ;
    }*/


    //EJEMPLO 2.CON ALGUNAS ADAPATACIONES ES EL UTILIZADO,...

    /*String filter = ...;
    String itemValue = ...;

    int startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
    int endPos = startPos + filter.length();

    if (startPos != -1) // This should always be true, just a sanity check
    {
        Spannable spannable = new SpannableString(itemValue);
        ColorStateList blueColor = new ColorStateList(new int[][] { new int[] {}}, new int[] { Color.BLUE });
        TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

        spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }
    else
            textView.setText(itemValue);*/

}