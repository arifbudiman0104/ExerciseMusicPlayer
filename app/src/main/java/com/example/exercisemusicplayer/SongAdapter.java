package com.example.exercisemusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

//berfungsi untuk memetakan lagu pada tampilan daftar.
public class SongAdapter extends BaseAdapter {
    //variable untuk memindahkan lagu dari kelas utama
    private ArrayList<Song> songs;
    //variable untuk memetakan deretan judul dan artis ke TextView
    private LayoutInflater songInflater;


    //metod konstruktor untuk memanggil variable
    public SongAdapter(Context context, ArrayList<Song> theSongs){
        songs=theSongs;
        songInflater=LayoutInflater.from(context);
    }

    @Override
    //untuk mengembalikan ukuran daftar
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //mengumpulkan lagu ke layout lagu
        LinearLayout songLay = (LinearLayout)songInflater.inflate
                (R.layout.song, parent, false);
        //untuk mendapatkan judul dan nama artis
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        //untuk mendapatkan posisi lagu
        Song currSong = songs.get(position);
        //untuk mendapatkan string judul lagu dan artisnya
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        //mengubah posisi sebagai tag
        songLay.setTag(position);
        return songLay;
    }
}