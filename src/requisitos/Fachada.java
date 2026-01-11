package requisitos;

import java.util.ArrayList;

import repositorio.Repositorio;
import modelo.Participante;
import modelo.Empregado;
import modelo.Convidado;
import modelo.Reuniao;
import arquivos.Arquivos;

public class Fachada {

    // Acesso aos dados
    private Repositorio repositorio;
    private Arquivos arquivos;

    public Fachada() {
        repositorio = new Repositorio();
        arquivos = new Arquivos();
    }

    // ===== CRIAR PARTICIPANTES =====

    public void criarEmpregado(String nome, String email, String setor) throws Exception {

        // Validações básicas
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome não pode ser vazio");
        }

        if (!validarEmail(email)) {
            throw new Exception("Email inválido");
        }

        if (setor == null || setor.trim().isEmpty()) {
            throw new Exception("Setor não pode ser vazio");
        }

        // Evita nomes duplicados
        if (repositorio.existeParticipanteComNome(nome)) {
            throw new Exception("Já existe participante com o nome: " + nome);
        }

        Empregado empregado = new Empregado(nome, email, setor);
        repositorio.adicionarParticipante(empregado);
    }

    public void criarConvidado(String nome, String email, String instituicao) throws Exception {

        // Validações básicas
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome não pode ser vazio");
        }

        if (!validarEmail(email)) {
            throw new Exception("Email inválido");
        }

        if (instituicao == null || instituicao.trim().isEmpty()) {
            throw new Exception("Instituição não pode ser vazia");
        }

        // Evita nomes duplicados
        if (repositorio.existeParticipanteComNome(nome)) {
            throw new Exception("Já existe participante com o nome: " + nome);
        }

        Convidado convidado = new Convidado(nome, email, instituicao);
        repositorio.adicionarParticipante(convidado);
    }

    // ===== CRIAR REUNIÃO =====

    public void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {

        // Valida data e assunto
        if (!validarData(data)) {
            throw new Exception("Data inválida. Formato esperado: dd/mm/aaaa");
        }

        if (repositorio.existeReuniaoNaData(data)) {
            throw new Exception("Já existe reunião na data: " + data);
        }

        if (assunto == null || assunto.trim().isEmpty()) {
            throw new Exception("Assunto não pode ser vazio");
        }

        // Valida lista de participantes
        if (nomes == null) {
            throw new Exception("Lista de nomes não pode ser null");
        }

        if (nomes.size() < 2) {
            throw new Exception("Reunião deve ter no mínimo 2 participantes");
        }

        // Verifica nomes duplicados
        for (int i = 0; i < nomes.size(); i++) {
            for (int j = i + 1; j < nomes.size(); j++) {
                if (nomes.get(i).equals(nomes.get(j))) {
                    throw new Exception("Nome duplicado na lista: " + nomes.get(i));
                }
            }
        }

        // Verifica se participantes existem
        for (String nome : nomes) {
            if (!repositorio.existeParticipanteComNome(nome)) {
                throw new Exception("Participante não encontrado: " + nome);
            }
        }

        int id = repositorio.gerarProximoId();
        Reuniao reuniao = new Reuniao(id, data, assunto);

        // Associa participantes à reunião
        for (String nome : nomes) {
            Participante p = repositorio.localizarParticipante(nome);
            reuniao.adicionarParticipante(p);
        }

        repositorio.adicionarReuniao(reuniao);
    }

    // ===== GERENCIAR REUNIÕES =====

    public void adicionarParticipanteReuniao(String nome, int id) throws Exception {

        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new Exception("Reunião com ID " + id + " não encontrada");
        }

        Participante participante = repositorio.localizarParticipante(nome);
        if (participante == null) {
            throw new Exception("Participante não encontrado: " + nome);
        }

        // Evita duplicar participante
        if (reuniao.getParticipantes().contains(participante)) {
            throw new Exception("Participante " + nome + " já está na reunião " + id);
        }

        reuniao.adicionarParticipante(participante);
    }

    public void removerParticipanteReuniao(String nome, int id) throws Exception {

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

        // Se ficar com menos de 2, cancela
        if (reuniao.getQuantidadeParticipantes() < 2) {
            cancelarReuniao(id);
        }
    }

    public void cancelarReuniao(int id) throws Exception {

        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new Exception("Reunião com ID " + id + " não encontrada");
        }

        // Remove vínculos dos participantes
        ArrayList<Participante> copia = new ArrayList<>(reuniao.getParticipantes());
        for (Participante p : copia) {
            reuniao.removerParticipante(p);
        }

        repositorio.removerReuniao(id);
    }

    // ===== LISTAR =====

    public ArrayList<Reuniao> listarReunioes() {
        return repositorio.getReunioes();
    }

    public ArrayList<Participante> listarParticipantes() {
        ArrayList<Participante> lista = new ArrayList<>(repositorio.getParticipantes());
        lista.sort((p1, p2) -> p1.getNome().compareTo(p2.getNome()));
        return lista;
    }

    public ArrayList<Empregado> listarEmpregados() {
        ArrayList<Empregado> lista = repositorio.getEmpregados();
        lista.sort((e1, e2) -> e1.getNome().compareTo(e2.getNome()));
        return lista;
    }

    public ArrayList<Convidado> listarConvidados() {
        ArrayList<Convidado> lista = repositorio.getConvidados();
        lista.sort((c1, c2) -> c1.getNome().compareTo(c2.getNome()));
        return lista;
    }

    // ===== CONSULTAS =====

    public ArrayList<Participante> consulta1(int n) throws Exception {

        if (n < 0) {
            throw new Exception("Número de reuniões não pode ser negativo");
        }

        ArrayList<Participante> resultado = new ArrayList<>();

        for (Participante p : repositorio.getParticipantes()) {
            if (p.getQuantidadeReunioes() >= n) {
                resultado.add(p);
            }
        }

        return resultado;
    }

    public int consulta2(int m, int a) throws Exception {

        if (m < 1 || m > 12) {
            throw new Exception("Mês deve estar entre 1 e 12");
        }

        if (a < 0) {
            throw new Exception("Ano não pode ser negativo");
        }

        int contador = 0;

        for (Reuniao r : repositorio.getReunioes()) {
            String[] partes = r.getData().split("/");
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);

            if (mes == m && ano == a) {
                contador++;
            }
        }

        return contador;
    }

    // ===== PERSISTÊNCIA =====

    public void lerObjetos() {

        // Lê reuniões
        ArrayList<Reuniao> reunioes = arquivos.lerReunioes();
        for (Reuniao r : reunioes) {
            repositorio.adicionarReuniao(r);
        }

        // Lê participantes e vínculos
        ArrayList<Arquivos.ParticipanteComReunioes> dados = arquivos.lerParticipantes();

        for (Arquivos.ParticipanteComReunioes pcr : dados) {
            repositorio.adicionarParticipante(pcr.participante);

            for (Integer idReuniao : pcr.idsReunioes) {
                Reuniao r = repositorio.localizarReuniao(idReuniao);
                if (r != null) {
                    r.adicionarParticipante(pcr.participante);
                }
            }
        }

        // Ajusta próximo ID
        if (!reunioes.isEmpty()) {
            int maiorId = 0;
            for (Reuniao r : reunioes) {
                if (r.getId() > maiorId) {
                    maiorId = r.getId();
                }
            }
            repositorio.ajustarProximoId(maiorId);
        }
    }

    public void gravarObjetos() {
        arquivos.gravarReunioes(repositorio.getReunioes());
        arquivos.gravarParticipantes(repositorio.getParticipantes());
    }

    // ===== VALIDAÇÕES =====

    private boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private boolean validarData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        return data.matches(regex);
    }
}
