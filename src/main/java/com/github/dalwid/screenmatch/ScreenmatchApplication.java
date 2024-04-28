package com.github.dalwid.screenmatch;

import com.github.dalwid.screenmatch.model.DadosSerie;
import com.github.dalwid.screenmatch.service.ConsumoApi;
import com.github.dalwid.screenmatch.service.ConvertDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=26fe8df5");
		//var json =consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=26fe8df5");
		//json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		//System.out.println(json);
		System.out.println(json);
		ConvertDados conversor = new ConvertDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
