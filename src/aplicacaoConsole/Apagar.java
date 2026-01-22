/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o orientada a objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package aplicacaoConsole;

import requisitos.Fachada;

public class Apagar {

	public Apagar() {
	System.out.println("---------apagando reuniao 4-----");
	Fachada.lerObjetos();
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
