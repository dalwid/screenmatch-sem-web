package com.github.dalwid.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpsodio(@JsonAlias("Title")      String titulo,
                           @JsonAlias("Episode")    Integer numero,
                           @JsonAlias("imdbRating") String avaiacao,
                           @JsonAlias("Released")   String dataLacamento) {
}
