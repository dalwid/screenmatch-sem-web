package com.github.dalwid.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer   temporada;
    private String    titulo;
    private Integer   numeroEpisodio;
    private double    avaiacao;
    private LocalDate dataLacamento;

    public Episodio(Integer numeroTemporada, DadosEpsodio dadosEpsodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpsodio.titulo();
        this.numeroEpisodio = dadosEpsodio.numero();

        try {
            this.avaiacao = Double.valueOf(dadosEpsodio.avaiacao());
        }
        catch (NumberFormatException ex){
            this.avaiacao = 0.0;
        }

        try {
            this.dataLacamento = LocalDate.parse(dadosEpsodio.dataLacamento());
        }
        catch(DateTimeParseException ex){
            this.dataLacamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numeroEpisodio;
    }

    public void setNumero(Integer numero) {
        this.numeroEpisodio = numero;
    }

    public double getAvaiacao() {
        return avaiacao;
    }

    public void setAvaiacao(double avaiacao) {
        this.avaiacao = avaiacao;
    }

    public LocalDate getDataLacamento() {
        return dataLacamento;
    }

    public void setDataLacamento(LocalDate dataLacamento) {
        this.dataLacamento = dataLacamento;
    }

    @Override
    public String toString() {
        return  "temporada = " + temporada +
                ", titulo = '" + titulo + '\'' +
                ", numeroEpisodio = " + numeroEpisodio +
                ", avaiacao = " + avaiacao +
                ", dataLacamento = " + dataLacamento;
    }
}
