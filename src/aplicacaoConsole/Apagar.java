/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação orientada a objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/
package aplicacaoConsole;

import requisitos.Fachada;

public class Apagar {

	public Apagar() {
	System.out.println("---------apagando reuniao 4-----");
		try {
			Fachada.cancelarReuniao(4);
		}
		catch(Exception e){
			System.out.println("-->" + e.getMessage());
		}
	}

	public static void main(String[] args){
		new Apagar();
	}
}
