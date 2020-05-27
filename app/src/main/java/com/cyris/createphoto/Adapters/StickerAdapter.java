package com.cyris.createphoto.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.R;

import java.io.InputStream;
import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    ArrayList<Integer> stickerRawId;
    Context context;
    StickerSelected stickerSelected;
    public StickerAdapter(Context ctx,StickerSelected selected)
    {
        context = ctx;
        stickerSelected = selected;
        stickerAdd();
    }

    public interface StickerSelected{
        void StickerSelecteId(int rawId);
    }
    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sticker_holder,parent,false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        holder.ImageSet();
        holder.ImageClickListner();
    }

    @Override
    public int getItemCount() {
        return stickerRawId.size();
    }

    class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerHolderImage;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerHolderImage = itemView.findViewById(R.id.stickerHolderImage);
        }

        public void ImageSet() {

                stickerHolderImage.setImageResource(stickerRawId.get(getLayoutPosition()));
            }

        public void ImageClickListner()
        {
            stickerHolderImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickerSelected.StickerSelecteId(stickerRawId.get(getLayoutPosition()));
                }
            });

        }
        }


        public void stickerAdd() {
            stickerRawId = new ArrayList<>();
            stickerRawId.add(R.raw.bubble);
            stickerRawId.add(R.raw.bubble2);
            stickerRawId.add(R.raw.bubble3);
            stickerRawId.add(R.raw.bubble4);
            stickerRawId.add(R.raw.bubble5);
            stickerRawId.add(R.raw.bubble6);
            stickerRawId.add(R.raw.bubble7);
            stickerRawId.add(R.raw.bubble9);
            stickerRawId.add(R.raw.bubble10);
            stickerRawId.add(R.raw.glass1);
            stickerRawId.add(R.raw.glass2);
            stickerRawId.add(R.raw.glass3);
            stickerRawId.add(R.raw.glass4);
            stickerRawId.add(R.raw.glass5);
            stickerRawId.add(R.raw.glass6);
            stickerRawId.add(R.raw.glass7);
            stickerRawId.add(R.raw.glass8);
            stickerRawId.add(R.raw.glass9);
            stickerRawId.add(R.raw.glass10);
            stickerRawId.add(R.raw.glass_red_dot);
            stickerRawId.add(R.raw.glasses_black);
            stickerRawId.add(R.raw.laser1);
            stickerRawId.add(R.raw.laser2);
            stickerRawId.add(R.raw.laser3);
            stickerRawId.add(R.raw.laser4);
            stickerRawId.add(R.raw.laser5);
            stickerRawId.add(R.raw.laser6);
            stickerRawId.add(R.raw.laser7);
            stickerRawId.add(R.raw.laser8);
            stickerRawId.add(R.raw.laser9);
            stickerRawId.add(R.raw.laser10);
            stickerRawId.add(R.raw.laser11);
            stickerRawId.add(R.raw.laser12);
            stickerRawId.add(R.raw.laser13);
            stickerRawId.add(R.raw.eye1);
            stickerRawId.add(R.raw.eye2);
            stickerRawId.add(R.raw.eye3);
            stickerRawId.add(R.raw.eye4);
            stickerRawId.add(R.raw.eye5);
            stickerRawId.add(R.raw.eye6);
            stickerRawId.add(R.raw.eye7);
            stickerRawId.add(R.raw.eye8);
            stickerRawId.add(R.raw.eye9);
            stickerRawId.add(R.raw.eye10);
            stickerRawId.add(R.raw.eye11);
            stickerRawId.add(R.raw.eye12);
            stickerRawId.add(R.raw.moustache1);
            stickerRawId.add(R.raw.moustache2);
            stickerRawId.add(R.raw.moustache3);
            stickerRawId.add(R.raw.moustache4);
            stickerRawId.add(R.raw.moustache5);
            stickerRawId.add(R.raw.moustache6);
            stickerRawId.add(R.raw.moustache7);
            stickerRawId.add(R.raw.moustache8);
            stickerRawId.add(R.raw.moustache9);
            stickerRawId.add(R.raw.moustache10);
            stickerRawId.add(R.raw.moustache11);
            stickerRawId.add(R.raw.moustache12);
            stickerRawId.add(R.raw.moustache13);
            stickerRawId.add(R.raw.moustache14);
            stickerRawId.add(R.raw.moustache15);
            stickerRawId.add(R.raw.moustache16);
            stickerRawId.add(R.raw.moustache17);
            stickerRawId.add(R.raw.moustache18);
            stickerRawId.add(R.raw.moustache19);
            stickerRawId.add(R.raw.moustache20);
            stickerRawId.add(R.raw.moustache21);
            stickerRawId.add(R.raw.moustache22);
            stickerRawId.add(R.raw.moustache23);
            stickerRawId.add(R.raw.moustache24);
            stickerRawId.add(R.raw.moustache25);
            stickerRawId.add(R.raw.moustache26);
            stickerRawId.add(R.raw.moustache27);
            stickerRawId.add(R.raw.moustache28);
            stickerRawId.add(R.raw.moustache29);
            stickerRawId.add(R.raw.moustache30);
            stickerRawId.add(R.raw.cigere1);
            stickerRawId.add(R.raw.cigere2);
            stickerRawId.add(R.raw.cigere3);
            stickerRawId.add(R.raw.blast1);
            stickerRawId.add(R.raw.blast2);
            stickerRawId.add(R.raw.blast3);
            stickerRawId.add(R.raw.blast4);
            stickerRawId.add(R.raw.gift1);
            stickerRawId.add(R.raw.gift2);
            stickerRawId.add(R.raw.gift3);
            stickerRawId.add(R.raw.gift4);
            stickerRawId.add(R.raw.hbd1);
            stickerRawId.add(R.raw.hbd2);
            stickerRawId.add(R.raw.money1);
            stickerRawId.add(R.raw.money2);
            stickerRawId.add(R.raw.money3);
            stickerRawId.add(R.raw.necklace1);
            stickerRawId.add(R.raw.necklace2);
            stickerRawId.add(R.raw.necklace3);
            stickerRawId.add(R.raw.necklace4);
            stickerRawId.add(R.raw.necklace5);
            stickerRawId.add(R.raw.necklace6);
            stickerRawId.add(R.raw.necklace7);
            stickerRawId.add(R.raw.hat1);
            stickerRawId.add(R.raw.hat2);
            stickerRawId.add(R.raw.hat3);
            stickerRawId.add(R.raw.hat4);
            stickerRawId.add(R.raw.hat5);
            stickerRawId.add(R.raw.hat6);
            stickerRawId.add(R.raw.hat7);
            stickerRawId.add(R.raw.hat8);
            stickerRawId.add(R.raw.hat9);
            stickerRawId.add(R.raw.hat10);
            stickerRawId.add(R.raw.hat11);
            stickerRawId.add(R.raw.hat12);
            stickerRawId.add(R.raw.hat13);
            stickerRawId.add(R.raw.hat14);
            stickerRawId.add(R.raw.hat15);
            stickerRawId.add(R.raw.hat16);
            stickerRawId.add(R.raw.stick1);
            stickerRawId.add(R.raw.stick2);
            stickerRawId.add(R.raw.stick3);
            stickerRawId.add(R.raw.thug1);
            stickerRawId.add(R.raw.thug2);




        }
}
