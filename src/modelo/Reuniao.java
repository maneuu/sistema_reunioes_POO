package modelo;

import java.util.ArrayList;

public class Reuniao {

    private int id;
    private String data;
    private String assunto;
    private ArrayList<Participante> participantes = new ArrayList<>();

    // Construtor (id gerado pelo repositório)
    public Reuniao(int id, String data, String assunto) {
        this.id = id;
        this.data = data;
        this.assunto = assunto;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public int getQuantidadeParticipantes() {
        return participantes.size();
    }

    // Relacionamento com participantes

    public void adicionarParticipante(Participante p) {
        if (!participantes.contains(p)) {
            participantes.add(p);
            p.adicionarReuniao(this);
        }
    }

    public void removerParticipante(Participante p) {
        if (participantes.remove(p)) {
            p.removerReuniao(this);
        }
    }

    public boolean temParticipante(Participante p) {
        return participantes.contains(p);
    }

    @Override
    public String toString() {
        return "Reuniao #" + id + " - " + data + " - " + assunto;
    }
}
