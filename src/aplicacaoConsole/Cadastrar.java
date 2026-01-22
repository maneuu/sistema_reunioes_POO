package aplicacaoConsole;
import java.util.ArrayList;
import java.util.List;

import requisitos.Fachada;

public class Cadastrar {

	public Cadastrar() {
		System.out.println("cadastro de empregados, convidados e reunioes");
		try {
			Fachada.criarEmpregado("carlos", "carlos@gmail.com", "vendas");
			Fachada.criarEmpregado("joao", "joao@gmail.com", "marketing");
			Fachada.criarEmpregado("paulo", "paulo@gmail.com", "youtuber");
			Fachada.criarEmpregado("marcos", "marcos@gmail.com", "inova��o");
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}	
		
		try {
			Fachada.criarConvidado("ana", "ana@gmail.com", "google");
			Fachada.criarConvidado("maria", "maria@gmail.com", "meta");
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}	
		
		try {
			Fachada.criarReuniao("20/01/2026", "reuniao1", new ArrayList<>(List.of("joao","carlos","ana","maria")));
			Fachada.criarReuniao("20/02/2026", "reuniao2", new ArrayList<>(List.of("joao","carlos")));
			Fachada.criarReuniao("20/03/2026", "reuniao3", new ArrayList<>(List.of("joao","carlos")));
			Fachada.criarReuniao("20/04/2026", "reuniao4", new ArrayList<>(List.of("paulo","marcos")));
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}	

		System.out.println("Fim do programa");
	}



	public static void main (String[] args) 
	{
		new Cadastrar();
	}
}


