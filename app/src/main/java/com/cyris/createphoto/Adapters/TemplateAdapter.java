package com.cyris.createphoto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.ExtraMemeTemplate;
import com.cyris.createphoto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplatViewHolder> {

    HashMap<String,String> templateList;
    Context context;
    ArrayList<String> textList,imageList;
    public TemplateAdapter(Context ctx,HashMap<String,String> template)
    {
        context =ctx;
        templateList = template;
        textList = new ArrayList<>();
        imageList = new ArrayList<>();
        for(HashMap.Entry<String,String> entry : templateList.entrySet())
        {
                textList.add(entry.getKey());
                imageList.add(entry.getValue());
        }
    }
    @NonNull
    @Override
    public TemplatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.template_holder,parent,false);
        return new TemplatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplatViewHolder holder, int position) {
        holder.SetTemplateData();
        holder.OnTemplateClick();
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }

    class TemplatViewHolder extends RecyclerView.ViewHolder {
        ImageView templateImage;
        TextView templateText;
        public TemplatViewHolder(@NonNull View itemView) {
            super(itemView);
            templateImage = itemView.findViewById(R.id.imageInTemplateHolder);
            templateText = itemView.findViewById(R.id.textInTemplateHolder);
        }

        public void SetTemplateData()
        {
            Picasso.get().load(imageList.get(getLayoutPosition())).into(templateImage);
            templateText.setText(textList.get(getLayoutPosition()));
        }

        public void OnTemplateClick()
        {
            templateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ExtraMemeTemplate.class);
                    intent.putExtra(context.getString(R.string.extra_meme_template),textList.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
