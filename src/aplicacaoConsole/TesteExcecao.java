package aplicacaoConsole;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Participante;
import modelo.Reuniao;
import requisitos.Fachada;

public class TesteExcecao {

	public TesteExcecao() {
		System.out.println("\n-------TESTE EXCEÇÕES LANÇADAS--------");
		
		//apagar arquivos de dados para forçar exceções
		File f1;
		File f2;
		try {
			f1 = new File(new File(".\\reunioes.csv").getCanonicalPath());
			f1.delete();
			f2 = new File(new File(".\\participantes.csv").getCanonicalPath());
			f2.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Fachada.criarEmpregado("aaa", "aaa@gmail.com", "aaa");
			Fachada.criarConvidado("bbb", "bbb@gmail.com", "bbb");
			Fachada.criarEmpregado("bbb", "bbb@gmail.com", "bbb");
			System.out.println("*************1--->erro: criar participante duplicado ");
		} catch (Exception e) {
			System.out.println("1ok--->" + e.getMessage());
		}

		try {
			Fachada.criarReuniao("20/01/2026", "teste2", new ArrayList<>(List.of("aaa","xxx")));
			System.out.println("*************2--->erro: criar reuniao com participante inexistente ");
		} catch (Exception e) {
			System.out.println("2ok--->" + e.getMessage());
		}
		
		try {
			Fachada.criarReuniao("20/01/2026", "teste3", new ArrayList<>(List.of("aaa")));
			System.out.println("*************3--->erro: criar reuniao com 1 participante apenas ");
		} catch (Exception e) {
			System.out.println("3ok--->" + e.getMessage());
		}
		
		try {
			Fachada.criarReuniao("20/01/2026", "teste4", new ArrayList<>(List.of("aaa","aaa")));
			System.out.println("*************4--->erro: criar reuniao com participante duplicado ");
		} catch (Exception e) {
			System.out.println("4ok--->" + e.getMessage());
		}
		
		try {
			Fachada.criarReuniao("20/01/2026", "teste5", new ArrayList<>(List.of("aaa","bbb")));
			Fachada.criarReuniao("20/01/2026", "teste5", new ArrayList<>(List.of("aaa","aaa")));
			System.out.println("*************5--->erro: criar reuniao com data duplicada ");
		} catch (Exception e) {
			System.out.println("5ok--->" + e.getMessage());
		}

		try {
			Fachada.removerParticipanteReuniao("ccc", 1);
			System.out.println("*************6--->erro: remover participante que nao participa");
		} catch (Exception e) {
			System.out.println("6ok--->" + e.getMessage());
		}

		try {
			Fachada.removerParticipanteReuniao("aaa", 5);
			System.out.println("*************7--->erro: remover participante reuniao inexistente");
		} catch (Exception e) {
			System.out.println("7ok--->" + e.getMessage());
		}

		try {
			Fachada.criarReuniao("30/12/2030", "teste8", new ArrayList<>(List.of("bbb","aaa")));
			Fachada.removerParticipanteReuniao("aaa", 2);
			Fachada.adicionarParticipanteReuniao("aaa", 2);
			System.out.println("*************8--->erro: cancelar com quorum minimo");
		} catch (Exception e) {
			System.out.println("8ok--->" + e.getMessage());
		}
		
		try {
			Fachada.cancelarReuniao(999);
			System.out.println("*************9--->erro: cancelar reuniao inexistente");
		} catch (Exception e) {
			System.out.println("9ok--->" + e.getMessage());
		}

		System.out.println("\n---------listagem final participantes-----");
		for (Participante p : Fachada.listarParticipantes())
			System.out.println(p);
		
		System.out.println("\n---------listagem final reunioes");
		for (Reuniao r : Fachada.listarReunioes())
			System.out.println(r);
		
		
		//apagar arquivos apos o teste
		try {
			f1 = new File(new File(".\\reunioes.csv").getCanonicalPath());
			f1.delete();
			f2 = new File(new File(".\\participantes.csv").getCanonicalPath());
			f2.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TesteExcecao();
	}
}
