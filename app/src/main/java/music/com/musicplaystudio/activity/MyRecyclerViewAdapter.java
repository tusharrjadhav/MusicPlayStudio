package music.com.musicplaystudio.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

import music.com.musicplaystudio.R;
import music.com.musicplaystudio.data.Audio;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<Audio> list = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    public MyRecyclerViewAdapter(List<Audio> list, Context context) {
        this.list = list;
        mContext = context;
        this.mInflater = LayoutInflater.from(context);;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = mInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        Audio audio = list.get(position);
        holder.title.setText(audio.getSong());
        holder.artists.setText(audio.getArtists());
        holder.favorite.setSelected(audio.isFavorite());
        Glide.with(mContext).load(audio.getCover_image())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.songThumbnail);
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void playAudio(View view, int position);
        void downloadAudio(View view, int position);
        void favAudio(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView artists;
        ImageView playPause;
        ImageView songThumbnail;
        ImageView download;
        ImageView favorite;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            artists = itemView.findViewById(R.id.artists);
            playPause = itemView.findViewById(R.id.play_pause);
            songThumbnail = itemView.findViewById(R.id.song_iamge);
            download = itemView.findViewById(R.id.download);
            favorite = itemView.findViewById(R.id.fav);
            playPause.setOnClickListener(view -> mClickListener.playAudio(view, getAdapterPosition()));
            download.setOnClickListener(view -> mClickListener.downloadAudio(view, getAdapterPosition()));
            favorite.setOnClickListener(view -> mClickListener.favAudio(view, getAdapterPosition()));
        }
    }

}

