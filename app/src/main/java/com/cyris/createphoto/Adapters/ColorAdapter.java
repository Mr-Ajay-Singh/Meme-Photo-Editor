package com.cyris.createphoto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.R;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    ArrayList<Integer> colorList;
    Context context;
    colorSelection colorSelection;
    String type;
    public ColorAdapter(Context ctx,colorSelection selection,String type1)
    {
        this.type = type1;
        this.context = ctx;
        this.colorSelection = selection;
        AllColor();
    }

    public interface colorSelection
    {
        void colorSelected(int colorId,String type);
    }
    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.color_holder,parent,false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.colorView.setBackgroundColor(colorList.get(position));
        holder.selectionInterfaceWork();
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        ImageView colorView;
        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.colorHolder);
        }

        public void selectionInterfaceWork()
        {
            colorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorSelection.colorSelected(colorList.get(getLayoutPosition()),type);
                }
            });

        }
    }




    public void AllColor()
    {
        colorList = new ArrayList<>();
        colorList.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.black));
        colorList.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.white));
        colorList.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorList.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
    }
}
