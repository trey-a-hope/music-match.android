package Services;

import DTO.Song;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;

public class MediaService {
    private Activity activity;
    private ContentResolver contentResolver;
    private Cursor cursor;
    private final Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final String SONG_SELECTION = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
    private final String SORT_ORDER = MediaStore.Audio.Media.TITLE + " ASC";

    public MediaService(Activity activity) {
        this.activity = activity;
        contentResolver = activity.getContentResolver();
        cursor = contentResolver.query(URI, null, SONG_SELECTION, null, SORT_ORDER);
    }

    public ArrayList<Song> getSongsFromDevice() {
        int count = 0;
        ArrayList<Song> songs = new ArrayList<Song>();
        if (cursor != null) {
            count = cursor.getCount();

            if (count > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    String albumId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                    String dateModified = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    // Save song here.
                    Song song = new Song(id, name, artist, album, dateModified);
                    songs.add(song);
                }

            }
        }
        cursor.close();
        return songs;
    }
}
