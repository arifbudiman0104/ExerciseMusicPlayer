package com.example.exercisemusicplayer;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

//kelas untuk Music Service
//extend untuk membuka jalan dari deklarasi kelas supaya mengimplementasikan
//interface yang akan digunakan pada pemutar musik
public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition()>0){
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //untuk playback
        mediaPlayer.start();
    }

    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    //media player
    private MediaPlayer player;
    //list lagu
    private ArrayList<Song> songs;
    //posisi lagu
    private int songPosn;

    //metod untuk membuat service
    public void onCreate(){
        //membuat service
        super.onCreate();
        //menginisialisasi posisi
        songPosn=0;
        //membuat pemutar
        player = new MediaPlayer();

        initMusicPlayer();
        rand=new Random();
    }
    //metod untuk menginisialisasi Media Player
    public void initMusicPlayer(){
        //konfigurasi musik player
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //untuk membiarkan musik diputar ketika smartphone dalam kondisi idle
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

    }

    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }
    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    private final IBinder musicBind = new MusicBinder();
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }
    //untuk memutar musik
    public void playSong(){
        player.reset();
        //untuk mendapatkan musik
        Song playSong = songs.get(songPosn);
        //untuk mendapatkan id
        long currSong = playSong.getID();
        //untuk mengatur uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    //untuk mndapatkan posisi lagu sekarang
    public int getPosn(){
        return player.getCurrentPosition();
    }
    //untuk mendapatkan durasi lagu
    public int getDur(){
        return player.getDuration();
    }
    //untuk mengetahui apakah musik sedang diputar
    public boolean isPng(){
        return player.isPlaying();
    }
    //untuk mengetahui apakah musik sedang di pause
    public void pausePlayer(){
        player.pause();
    }
    //untuk mempercepat lagu
    public void seek(int posn){
        player.seekTo(posn);
    }
    //untuk memulai lagu
    public void go(){
        player.start();
    }

    //untuk memainkan musik sebelumnya
    public void playPrev(){
        songPosn--;
        if(songPosn<0) songPosn=songs.size()-1;
        playSong();
    }
    //untuk memainkan musik selanjutnya
    public void playNext(){
        if(shuffle){
            int newSong = songPosn;
            while(newSong==songPosn){
                newSong=rand.nextInt(songs.size());
            }
            songPosn=newSong;
        }
        else{
            songPosn++;
            if(songPosn>=songs.size()) songPosn=0;
        }
        playSong();
    }
    private boolean shuffle=false;
    private Random rand;
    public void setShuffle(){
        if(shuffle) shuffle=false;
        else shuffle=true;
    }

}
