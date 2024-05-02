package com.github.dalwid.screenmatch.principal;

import com.github.dalwid.screenmatch.model.DadosEpsodio;
import com.github.dalwid.screenmatch.model.DadosSerie;
import com.github.dalwid.screenmatch.model.DadosTemporada;
import com.github.dalwid.screenmatch.model.Episodio;
import com.github.dalwid.screenmatch.service.ConsumoApi;
import com.github.dalwid.screenmatch.service.ConvertDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private  ConsumoApi consumo = new ConsumoApi();
    private ConvertDados conversor = new ConvertDados();

    private  final String ENDERECO = "http://www.omdbapi.com/?t=";
    private  final  String API_KEY = "&apikey=26fe8df5";

    public void exibeMenu(){
        //"http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=1&apikey=26fe8df5"
        System.out.println("Digite o nome da série para busca ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+")+API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTempoaradas() ; i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+")+"&season=" +i+API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        /*for (int i = 0; i < dados.totalTempoaradas() ; i++) {
            List<DadosEpsodio> episodiosTemporada = temporadas.get(i).eisodios();
            for (int j = 0; j < episodiosTemporada.size() ; j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/

        /* never have i ever nome do filme a ser digitado */
        temporadas.forEach(t -> t.eisodios().forEach(e -> System.out.println(e.titulo())));
        List<DadosEpsodio> dadosEpsodios = temporadas.stream()
                .flatMap(t -> t.eisodios().stream())
                .collect(Collectors.toList());

//        System.out.println("\nTop 10 episódios");
//        dadosEpsodios.stream()
//                .filter(e -> !e.avaiacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DadosEpsodio::avaiacao).reversed())
//                .peek(e -> System.out.println("Ordenação (N/A)" + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e-> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.eisodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toUnmodifiableList());

        episodios.forEach(System.out::println);

//        System.out.println("Digete um trecho do título do episódio ");
//
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        }
//        else{
//            System.out.println("Episódio não encontrado!");
//        }

//
//        System.out.println("Apartir de que ano vc deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLacamento() != null && e.getDataLacamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada()+
//                                " Episódio: " + e.getTitulo() +
//                                " Data de lancaçamento: " + e.getDataLacamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacoePortemporada = episodios.stream()
                .filter(e -> e.getAvaiacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaiacao)));

        System.out.println(avaliacoePortemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaiacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaiacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor epsódio: "+ est.getMax());
        System.out.println("Pior epsódio: "+ est.getMin());
        System.out.println("Quantidade: "+ est.getCount());
    }
}
