package com.videumcorp.desarrolladorandroid.navigatio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Presentacion extends Activity {

    protected boolean active = true;
    protected int splashTime = 1900;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Activity sin título. Debe extender de Activity
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Habilitar transiciones...
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_presentacion);

        Thread splashThread = new Thread(){
            @Override
            public void run(){
                try{
                    int waited = 0;
                    while(active && (waited < splashTime)){
                        sleep(100);
                        if(active){
                            waited += 100;
                        }

                    }
                } catch(InterruptedException e){

                } finally{

                    openApp();
                    //stop();//Está obsoleto.
                    interrupt();

                }

            }
        };
        splashThread.start();
    }

    private void openApp(){

        startActivity(new Intent(this, Inicio.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        //overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);

        //Animaciones por defecto en android...
        overridePendingTransition (android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        //overridePendingTransition (android.R.anim.fade_in, android.R.anim.fade_out);


        finish();

    }
}
