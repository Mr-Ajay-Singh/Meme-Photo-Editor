package com.cyris.createphoto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cyris.createphoto.ItemType.ItemType;
import com.cyris.createphoto.R;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewHolder> {

    ArrayList<ItemValues> itemCollection;
    ItemDetailInterface itemDetailInterface;
    public ItemAdapter(ItemDetailInterface detailInterface)
    {
        itemDetailInterface = detailInterface;
        setItemCollection();

    }

    public interface ItemDetailInterface{
        void ItemsInfo(ItemValues values);
    }
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder,parent,false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ItemValues values = itemCollection.get(position);
        holder.textInItemView.setText(values.name);
        holder.imageInItemHolder.setImageResource(values.imageId);
        holder.OnClickOnItem(values);
    }

    @Override
    public int getItemCount() {
        return itemCollection.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageInItemHolder;
        TextView textInItemView;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageInItemHolder = itemView.findViewById(R.id.imageInItemViewHolder);
            textInItemView = itemView.findViewById(R.id.textInItemViewHolder);
        }

        public void OnClickOnItem(final ItemValues values) {
            imageInItemHolder.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemDetailInterface.ItemsInfo(values);
                }
            });
        }
    }

    public class ItemValues{
        public String name;
        public ItemType type;
        public int imageId;
        ItemValues(String name1,int imageId1,ItemType type1)
        {
            this.imageId = imageId1;
            this.name = name1;
            this.type = type1;
        }
    }

    void setItemCollection()
    {
        itemCollection = new ArrayList<>();
        itemCollection.add(new ItemValues("Brush",R.drawable.brush,ItemType.BRUSH));
        itemCollection.add(new ItemValues("Text",R.drawable.text,ItemType.TEXT));
        itemCollection.add(new ItemValues("Eraser",R.drawable.eraser,ItemType.ERASER));
        itemCollection.add(new ItemValues("Sticker",R.drawable.sticker,ItemType.STICKER));
        itemCollection.add(new ItemValues("Emoji",R.drawable.emoji,ItemType.EMOJI));
        itemCollection.add(new ItemValues("Image",R.drawable.image,ItemType.IMAGE));
    }
}
