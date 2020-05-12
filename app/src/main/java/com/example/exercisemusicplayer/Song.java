package com.example.exercisemusicplayer;

//membuat kelas lagu
public class Song {
    //variable id, judul dan nama artis.
    private long id;
    private String title;
    private String artist;

    //metod yg dipake untuk memanggil variable
    public Song(long songID, String songTitle, String songArtist) {
        id=songID;
        title=songTitle;
        artist=songArtist;
    }

    //metod yang dipake untuk mendapatkan id, judul, nama artis
    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
}
