package br.com.app07_partiu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecomendacaoRecyclerViewAdapter extends RecyclerView.Adapter<RecomendacaoRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecomendacaoRecyclerViewAdapter";

    //variáveis
    private ArrayList<String> mNomes = new ArrayList<>();
    private ArrayList<String> mImgEstabelecimantoUrls = new ArrayList<>();
    private Context mContext;

    public RecomendacaoRecyclerViewAdapter(Context mContext, ArrayList<String> mNomes, ArrayList<String> mImgEstabelecimantoUrls) {
        this.mNomes = mNomes;
        this.mImgEstabelecimantoUrls = mImgEstabelecimantoUrls;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista_item_recomendacao,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImgEstabelecimantoUrls.get(position))
                .into(holder.imgEstabelecimento);

        holder.tfNome.setText(mNomes.get(position));

        holder.imgEstabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image: " + mNomes.get(position));
                Toast.makeText(mContext, mNomes.get(position) ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImgEstabelecimantoUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgEstabelecimento;
        TextView tfNome;

        public ViewHolder(View itemView) {
            super(itemView);
            imgEstabelecimento = itemView.findViewById(R.id.img_estabelecimento);
            tfNome = itemView.findViewById(R.id.tf_nome);

        }



    }
}
