package com.example.juliana.teste;

/**
 * Created by juliana on 20/05/2017.
 */

public class SharedInstance {
    private static SharedInstance ourInstance = new SharedInstance();

    public static SharedInstance getInstance(){return ourInstance;}
    int IDUsuario=1;


    private SharedInstance(){
    }

}
