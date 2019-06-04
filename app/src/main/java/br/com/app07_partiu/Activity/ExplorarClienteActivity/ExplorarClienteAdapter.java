package br.com.app07_partiu.Activity.ExplorarClienteActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

import br.com.app07_partiu.Activity.ExplorarClienteDetalhesActivity;
import br.com.app07_partiu.Model.Restaurante;
import br.com.app07_partiu.R;


public class ExplorarClienteAdapter extends RecyclerView.Adapter<ExplorarClienteAdapter.MyViewHolder> {

    private Context context;
    private List<Restaurante> restauranteConvertViewsList;
    public static final String RECOMENDACAO_DETALHE = "br.com.app07_partiu.ExplorarClienteAdapter.recomendacaoDetalhe";

    private Intent intentRecomendacaoDetalhe;



    public ExplorarClienteAdapter(Context mContext, List<Restaurante> restauranteConvertViewsList){
        this.context = mContext;
        this.restauranteConvertViewsList = restauranteConvertViewsList;
    }


    @Override
    public ExplorarClienteAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_card_imagem_nome, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ExplorarClienteAdapter.MyViewHolder viewHolder, int i){
        viewHolder.textViewNomeRestaurante.setText(restauranteConvertViewsList.get(i).getNomeFantasia());
        String poster = restauranteConvertViewsList.get(i).getLogo();
        if (restauranteConvertViewsList.get(i).getCodigoComanda().equals("OAF")){
            Log.d("TESTES", "Logo: "+restauranteConvertViewsList.get(i).getLogo());
        }
        Glide.with(context)
                .load(poster)
                .placeholder(R.drawable.ic_load)
                .into(viewHolder.imageViewLogoRestaurante);

/*
        viewHolder.imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickPosition = (int) view.getTag();


            }
        });*/
    }






        @Override
    public int getItemCount(){
        return restauranteConvertViewsList.size();
    }

    public Object getItem(int position) {
        return restauranteConvertViewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewLogoRestaurante;
        public TextView textViewNomeRestaurante;

        public MyViewHolder(View view){
            super(view);
            textViewNomeRestaurante = (TextView) view.findViewById(R.id.textView_nomeRestaurante);
            imageViewLogoRestaurante = (ImageView) view.findViewById(R.id.imageView_logoRestaurante);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        final Restaurante itemRestauranteConvertView = restauranteConvertViewsList.get(pos);
                        intentRecomendacaoDetalhe = new Intent(context, ExplorarClienteDetalhesActivity.class);
                        new Thread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            intentRecomendacaoDetalhe.putExtra(RECOMENDACAO_DETALHE, itemRestauranteConvertView);
                                            intentRecomendacaoDetalhe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intentRecomendacaoDetalhe);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }}
                        ).start();


                    }
                }
            });
        }
    }

}
