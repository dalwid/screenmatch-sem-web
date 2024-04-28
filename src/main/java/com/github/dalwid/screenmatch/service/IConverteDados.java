package com.github.dalwid.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
