package com.shrenikvikam.textalbums.ui.album.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shrenikvikam.textalbums.R;
import com.shrenikvikam.textalbums.model.Album;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListItemViewHolder> {

    private final List<Album> albumsList;

    public AlbumListAdapter(List<Album> albumsList) {
        this.albumsList = albumsList;
    }

    @NonNull
    @Override
    public AlbumListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.album_list_item, viewGroup, false);
        return new AlbumListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListItemViewHolder viewHolder, int position) {
        Album album = albumsList.get(position);
        viewHolder.title.setText(album.getTitle());
        viewHolder.title.setTag(position);
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    public final static class AlbumListItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public AlbumListItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
        }
    }
}
