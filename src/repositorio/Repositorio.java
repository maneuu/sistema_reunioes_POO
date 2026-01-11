package repositorio;

import java.util.ArrayList;

import modelo.Reuniao;
import modelo.Participante;
import modelo.Empregado;
import modelo.Convidado;

public class Repositorio {

    // Armazena dados
    private ArrayList<Reuniao> reunioes = new ArrayList<>();
    private ArrayList<Participante> participantes = new ArrayList<>();
    private int proximoIdReuniao = 1;

    public Repositorio() {
        // listas já iniciadas
    }

    // Adição

    public void adicionarReuniao(Reuniao r) {
        reunioes.add(r);
    }

    public void adicionarParticipante(Participante p) {
        participantes.add(p);
    }

    // Busca

    public Reuniao localizarReuniao(int id) {
        for (Reuniao r : reunioes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public Participante localizarParticipante(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    // Validações

    public boolean existeParticipanteComNome(String nome) {
        return localizarParticipante(nome) != null;
    }

    public boolean existeReuniaoNaData(String data) {
        for (Reuniao r : reunioes) {
            if (r.getData().equals(data)) {
                return true;
            }
        }
        return false;
    }

    // Remoção

    public boolean removerReuniao(int id) {
        Reuniao r = localizarReuniao(id);
        if (r != null) {
            return reunioes.remove(r);
        }
        return false;
    }

    // Listagem

    public ArrayList<Reuniao> getReunioes() {
        return reunioes;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public ArrayList<Empregado> getEmpregados() {
        ArrayList<Empregado> lista = new ArrayList<>();
        for (Participante p : participantes) {
            if (p instanceof Empregado) {
                lista.add((Empregado) p);
            }
        }
        return lista;
    }

    public ArrayList<Convidado> getConvidados() {
        ArrayList<Convidado> lista = new ArrayList<>();
        for (Participante p : participantes) {
            if (p instanceof Convidado) {
                lista.add((Convidado) p);
            }
        }
        return lista;
    }

    // Controle de ID

    public int gerarProximoId() {
        return proximoIdReuniao++;
    }

    // Ajuste após leitura de arquivo
    public void ajustarProximoId(int maiorId) {
        proximoIdReuniao = maiorId + 1;
    }
}
