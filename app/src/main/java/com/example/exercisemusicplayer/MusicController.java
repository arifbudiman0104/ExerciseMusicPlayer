package com.example.exercisemusicplayer;

import android.content.Context;
import android.widget.MediaController;

//kelas untuk musik kontroler
public class MusicController extends MediaController {
    public MusicController(Context c){
        super(c);
    }
    //untuk menyembunyikan kontroler ketika musik belum di mainkan
    public void hide(){}
}
