/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação orientada a objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/
package aplicacaoConsole;

import requisitos.Fachada;

public class Alterar {

	public Alterar() {
		try {
			Fachada.removerParticipanteReuniao("joao", 1); 
			Fachada.removerParticipanteReuniao("carlos", 1); 
			Fachada.removerParticipanteReuniao("maria", 1); 
			System.out.println("primeira alteracao ok");
		}
		catch(Exception e){
			System.out.println("-->" + e.getMessage());
		}
		
		try {
			Fachada.adicionarParticipanteReuniao("marcos", 2);
			Fachada.adicionarParticipanteReuniao("paulo", 3);
			System.out.println("segunda alteracao ok");

		}
		catch(Exception e){
			System.out.println("-->" + e.getMessage());
		}
	}

	public static void main(String[] args){
		new Alterar();
	}
}
