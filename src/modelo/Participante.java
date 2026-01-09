package modelo;

import java.util.ArrayList;

public abstract class Participante {

    private String nome;
    private String email;
    private ArrayList<Reuniao> reunioes = new ArrayList<>();

    // Construtor
    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Reuniao> getReunioes() {
        return reunioes;
    }

    // Relacionamento com reunião


    public void adicionarReuniao(Reuniao r) { // apenas chamado por Reuniao
        if (!reunioes.contains(r)) {
            reunioes.add(r);
        }
    }

    public void removerReuniao(Reuniao r) { // apenas chamado por Reuniao
        reunioes.remove(r);
    }

    public boolean participaDe(Reuniao r) {
        return reunioes.contains(r);
    }

    public int getQuantidadeReunioes() {
        return reunioes.size();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + nome + " (" + email + ")";
    }

    // Comparação por nome

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Participante other = (Participante) obj;
        if (nome == null) {
            return other.nome == null;
        }
        return nome.equals(other.nome);
    }
}
