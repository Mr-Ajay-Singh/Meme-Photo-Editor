package com.cyris.createphoto.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.R;

import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {

    Context context;
    ArrayList<String> emojiList;
    EmojiSelect emojiSelect;
    public EmojiAdapter(Context ctx,EmojiSelect  select)
    {
        context = ctx;
        emojiSelect = select;
        emojiList  = PhotoEditor.getEmojis(context);
    }


    public interface EmojiSelect{
        void EmojiSelected(String emoji);
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emoji_holder,parent,false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        holder.emojiText.setText(emojiList.get(position));
        holder.EmojiClick();

    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    class EmojiViewHolder extends RecyclerView.ViewHolder {
        TextView emojiText;
        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiText = itemView.findViewById(R.id.emojiText);
        }

        public void EmojiClick()
        {
            emojiText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emojiSelect.EmojiSelected(emojiList.get(getLayoutPosition()));
                }
            });
        }
    }
}
