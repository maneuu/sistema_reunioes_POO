package teste;

import requisitos. Fachada;
import java.util.ArrayList;
import modelo.Participante;
import modelo.Empregado;
import modelo.Convidado;

public class testeMain {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();
        
        System.out.println("========== CRIANDO PARTICIPANTES ==========");
        
        try {
            fachada.criarEmpregado("Pedro", "pedro@gmail.com", "TI");
            System.out.println("✅ Empregado Pedro criado");
            
            fachada.criarConvidado("Ana", "ana@ufpb.br", "UFPB");
            System. out.println("✅ Convidado Ana criado");
            
            fachada.criarEmpregado("Maria", "maria@gmail.com", "RH");
            System.out.println("✅ Empregado Maria criado");
            
            fachada.criarConvidado("João", "joao@ufpe.br", "UFPE");
            System.out.println("✅ Convidado João criado");
            
            fachada.criarEmpregado("Carlos", "carlos@gmail.com", "TI");
            System.out. println("✅ Empregado Carlos criado");
            
        } catch (Exception e) {
            System.out.println("❌ ERRO: " + e.getMessage());
            e.printStackTrace();
        }
        
        // ========== TESTE 1: Listar todos (ordem alfabética) ==========
        System.out.println("\n========== TESTE 1: LISTAR TODOS OS PARTICIPANTES ==========");
        ArrayList<Participante> todos = fachada.listarParticipantes();
        System.out.println("Total:  " + todos.size() + " participantes");
        for (Participante p : todos) {
            System.out.println("  - " + p.getNome() + " (" + p.getEmail() + ")");
        }
        System.out.println("Esperado: Ana, Carlos, João, Maria, Pedro (ordem alfabética)");
        
        // ========== TESTE 2: Listar apenas empregados ==========
        System.out.println("\n========== TESTE 2: LISTAR APENAS EMPREGADOS ==========");
        ArrayList<Empregado> empregados = fachada. listarEmpregados();
        System.out.println("Total: " + empregados.size() + " empregados");
        for (Empregado e : empregados) {
            System.out.println("  - " + e.getNome() + " - Setor: " + e.getSetor());
        }
        System.out.println("Esperado: Carlos, Maria, Pedro (ordem alfabética)");
        
        // ========== TESTE 3: Listar apenas convidados ==========
        System.out. println("\n========== TESTE 3: LISTAR APENAS CONVIDADOS ==========");
        ArrayList<Convidado> convidados = fachada.listarConvidados();
        System.out.println("Total: " + convidados.size() + " convidados");
        for (Convidado c : convidados) {
            System. out.println("  - " + c.getNome() + " - Instituição: " + c.getInstituicao());
        }
        System.out.println("Esperado: Ana, João (ordem alfabética)");
        
        // ========== TESTE 4: Testar validações ==========
        System.out. println("\n========== TESTE 4: VALIDAÇÕES ==========");
        
        // Teste nome duplicado
        try {
            fachada.criarEmpregado("Pedro", "pedro2@gmail.com", "RH");
            System.out. println("❌ FALHOU: Deveria ter rejeitado nome duplicado!");
        } catch (Exception e) {
            System.out.println("✅ Nome duplicado rejeitado: " + e.getMessage());
        }
        
        // Teste email inválido
        try {
            fachada.criarConvidado("Roberto", "robertogmail.com", "UFPB");
            System.out.println("❌ FALHOU: Deveria ter rejeitado email inválido!");
        } catch (Exception e) {
            System.out.println("✅ Email inválido rejeitado: " + e. getMessage());
        }
        
        // Teste nome vazio
        try {
            fachada.criarEmpregado("   ", "teste@gmail.com", "TI");
            System.out.println("❌ FALHOU:  Deveria ter rejeitado nome vazio!");
        } catch (Exception e) {
            System. out.println("✅ Nome vazio rejeitado: " + e.getMessage());
        }
        
        System.out.println("\n========== TESTES CONCLUÍDOS ==========");
    }
}