package Adapters;

import DTO.Song;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import com.example.musicmatch.app.R;

import java.util.*;

/*
    Each song's info card.
 */

public abstract class SongListAdapter<VH extends SongListAdapter.SongViewHolder> extends RecyclerView.Adapter<VH> {
    private ArrayList<Song> songs;
    private int count;

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView name, artist, dateModified;

        public SongViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.song_item_name);
            artist = (TextView) itemView.findViewById(R.id.song_item_artist);
            dateModified = (TextView) itemView.findViewById(R.id.song_item_date_modified);
        }
    }

    public void add(Song song) {
        if (song != null) {
            songs.add(song);
            notifyDataSetChanged();
        }
    }

    public void addAll(ArrayList<Song> list) {
        if (list != null) {
            songs = list;
            notifyDataSetChanged();
        }
    }

    public void remove(Song song) {
        songs.remove(song);
        notifyDataSetChanged();
        //Call in activity as such; adapter.remove(adapter.getItem(position));
    }

    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

}