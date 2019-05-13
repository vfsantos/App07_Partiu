package br.com.app07_partiu.Activity.ComandaGarcomActivity;

import android.widget.ImageView;
import android.widget.TextView;

import br.com.app07_partiu.Model.ComandaConvertView;

public class ComandaGarcomViewHolder {
    public TextView textViewDetalhes;
    public TextView textViewValor;
    public ImageView imageViewIcon;


    public ComandaGarcomViewHolder(TextView detalhe, TextView valor, ImageView icon) {
        this.textViewDetalhes = detalhe;
        this.textViewValor = valor;
        this.imageViewIcon = icon;
    }

    public TextView getTextViewDetalhes() {
        return textViewDetalhes;
    }

    public void setTextViewDetalhes(TextView textViewDetalhes) {
        this.textViewDetalhes = textViewDetalhes;
    }

    public TextView getTextViewValor() {
        return textViewValor;
    }

    public void setTextViewValor(TextView textViewValor) {
        this.textViewValor = textViewValor;
    }

    public ImageView getImageViewIcon() {
        return imageViewIcon;
    }

    public void setImageViewIcon(ImageView imageViewIcon) {
        this.imageViewIcon = imageViewIcon;
    }
}