package requisitos;

import java.util.ArrayList;
import repositorio.Repositorio;
import modelo.Participante;
import modelo.Empregado;
import modelo.Convidado;
import modelo.Reuniao;

public class Fachada {

    private static Repositorio repositorio = new Repositorio();

    private Fachada() {
    }

    // Listagens

    public static ArrayList<Participante> listarParticipantes() {
        ArrayList<Participante> lista = new ArrayList<>(repositorio.getParticipantes());
        lista.sort((p1, p2) -> p1.getNome().compareTo(p2.getNome()));
        return lista;
    }

    public static ArrayList<Empregado> listarEmpregados() {
        ArrayList<Empregado> lista = repositorio.getEmpregados();
        lista.sort((e1, e2) -> e1.getNome().compareTo(e2.getNome()));
        return lista;
    }

    public static ArrayList<Convidado> listarConvidados() {
        ArrayList<Convidado> lista = repositorio.getConvidados();
        lista.sort((c1, c2) -> c1.getNome().compareTo(c2.getNome()));
        return lista;
    }

    public static ArrayList<Reuniao> listarReunioes() {
        return repositorio.getReunioes();
    }

    // Criar Participantes
    public static void criarEmpregado(String nome, String email, String setor) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome não pode ser vazio");
        }

        if (!validarEmail(email)) {
            throw new Exception("Email inválido");
        }

        if (setor == null || setor.trim().isEmpty()) {
            throw new Exception("Setor não pode ser vazio");
        }

        if (repositorio.existeParticipanteComNome(nome)) {
            throw new Exception("Já existe participante com o nome: " + nome);
        }

        Empregado empregado = new Empregado(nome, email, setor);
        repositorio.adicionarParticipante(empregado);
    }

    public static void criarConvidado(String nome, String email, String instituicao) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome não pode ser vazio");
        }

        if (!validarEmail(email)) {
            throw new Exception("Email inválido");
        }

        if (instituicao == null || instituicao.trim().isEmpty()) {
            throw new Exception("Instituição não pode ser vazia");
        }

        if (repositorio.existeParticipanteComNome(nome)) {
            throw new Exception("Já existe participante com o nome: " + nome);
        }

        Convidado convidado = new Convidado(nome, email, instituicao);
        repositorio.adicionarParticipante(convidado);
        gravarObjetos();
    }

    // Criar Reunião

    public static void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {
        if (!validarData(data)) {
            throw new Exception("Data inválida.Formato esperado: dd/mm/aaaa");
        }

        if (repositorio.existeReuniaoNaData(data)) {
            throw new Exception("Já existe reunião na data: " + data);
        }

        if (assunto == null || assunto.trim().isEmpty()) {
            throw new Exception("Assunto não pode ser vazio");
        }

        if (nomes == null) {
            throw new Exception("Lista de nomes não pode ser null");
        }

        if (nomes.size() < 2) {
            throw new Exception("Reunião deve ter no mínimo 2 participantes");
        }

        // Validar nomes distintos
        for (int i = 0; i < nomes.size(); i++) {
            for (int j = i + 1; j < nomes.size(); j++) {
                if (nomes.get(i).equals(nomes.get(j))) {
                    throw new Exception("Nome duplicado na lista:  " + nomes.get(i));
                }
            }
        }

        for (String nome : nomes) {
            if (!repositorio.existeParticipanteComNome(nome)) {
                throw new Exception("Participante não encontrado: " + nome);
            }
        }

        int id = repositorio.gerarProximoId();
        Reuniao reuniao = new Reuniao(id, data, assunto);

        for (String nome : nomes) {
            Participante p = repositorio.localizarParticipante(nome);
            reuniao.adicionarParticipante(p);
        }

        repositorio.adicionarReuniao(reuniao);
        gravarObjetos();
    }

    // Modificar Reunião

    public static void adicionarParticipanteReuniao(String nome, int id) throws Exception {
        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new Exception("Reunião com ID " + id + " não encontrada");
        }

        Participante participante = repositorio.localizarParticipante(nome);
        if (participante == null) {
            throw new Exception("Participante não encontrado: " + nome);
        }

        if (reuniao.getParticipantes().contains(participante)) {
            throw new Exception("Participante " + nome + " já está na reunião " + id);
        }

        reuniao.adicionarParticipante(participante);
        gravarObjetos();
    }

    public static void removerParticipanteReuniao(String nome, int id) throws Exception {
        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new Exception("Reunião com ID " + id + " não encontrada");
        }

        Participante participante = repositorio.localizarParticipante(nome);
        if (participante == null) {
            throw new Exception("Participante não encontrado: " + nome);
        }

        if (!reuniao.getParticipantes().contains(participante)) {
            throw new Exception("Participante " + nome + " não está na reunião " + id);
        }

        reuniao.removerParticipante(participante);

        // Cancelar reunião se menos de 2 participantes
        if (reuniao.getQuantidadeParticipantes() < 2) {
            cancelarReuniao(id);
        }
        gravarObjetos();
    }

    public static void cancelarReuniao(int id) throws Exception {
        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new Exception("Reunião com ID " + id + " não encontrada");
        }

        ArrayList<Participante> copia = new ArrayList<>(reuniao.getParticipantes());
        for (Participante p : copia) {
            reuniao.removerParticipante(p);
        }

        repositorio.removerReuniao(id);
        gravarObjetos();
    }

    // Consultas

    public static ArrayList<Participante> consulta1(int n) {
        ArrayList<Participante> resultado = new ArrayList<>();

        for (Participante p : repositorio.getParticipantes()) {
            if (p.getQuantidadeReunioes() >= n) {
                resultado.add(p);
            }
        }

        return resultado;
    }

    public static int consulta2(String mes, String ano) {
        int m = Integer.parseInt(mes);
        int a = Integer.parseInt(ano);
        int contador = 0;

        for (Reuniao r : repositorio.getReunioes()) {
            String[] partes = r.getData().split("/");
            int mesReuniao = Integer.parseInt(partes[1]);
            int anoReuniao = Integer.parseInt(partes[2]);

            if (mesReuniao == m && anoReuniao == a) {
                contador++;
            }
        }

        return contador;
    }

    // Leitura e Gravação

    public static void lerObjetos() {
        repositorio.lerObjetos();
    }

    public static void gravarObjetos() {
        repositorio.gravarObjetos();
    }

    // Utilitários de Validação

    private static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private static boolean validarData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        return data.matches(regex);
    }
}