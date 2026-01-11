package arquivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;
import modelo.Reuniao;

public class Arquivos {

    // Nomes dos arquivos
    private static final String ARQUIVO_REUNIOES = "reunioes.csv";
    private static final String ARQUIVO_PARTICIPANTES = "participantes.csv";

    // Leitura

    public ArrayList<Reuniao> lerReunioes() {
        ArrayList<Reuniao> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO_REUNIOES);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String data = partes[1];
                String assunto = partes[2];

                Reuniao reuniao = new Reuniao(id, data, assunto);
                lista.add(reuniao);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler reunioes.csv: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<ParticipanteComReunioes> lerParticipantes() {
        ArrayList<ParticipanteComReunioes> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO_PARTICIPANTES);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(";");
                String tipo = partes[0];
                String nome = partes[1];
                String email = partes[2];
                String setorOuInstituicao = partes[3];

                // Cria o participante
                Participante participante;
                if (tipo.equals("EMPREGADO")) {
                    participante = new Empregado(nome, email, setorOuInstituicao);
                } else {
                    participante = new Convidado(nome, email, setorOuInstituicao);
                }

                // IDs das reuniões
                ArrayList<Integer> idsReunioes = new ArrayList<>();
                if (partes.length > 4 && !partes[4].isEmpty()) {
                    String[] idsStr = partes[4].split(",");
                    for (String idStr : idsStr) {
                        idsReunioes.add(Integer.parseInt(idStr.trim()));
                    }
                }

                lista.add(new ParticipanteComReunioes(participante, idsReunioes));
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler participantes.csv: " + e.getMessage());
        }

        return lista;
    }

    // Gravação

    public void gravarReunioes(ArrayList<Reuniao> reunioes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_REUNIOES))) {
            for (Reuniao r : reunioes) {
                pw.println(r.getId() + ";" + r.getData() + ";" + r.getAssunto());
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar reunioes.csv: " + e.getMessage());
        }
    }

    public void gravarParticipantes(ArrayList<Participante> participantes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_PARTICIPANTES))) {

            for (Participante p : participantes) {
                String tipo = (p instanceof Empregado) ? "EMPREGADO" : "CONVIDADO";

                String setorOuInstituicao;
                if (p instanceof Empregado) {
                    setorOuInstituicao = ((Empregado) p).getSetor();
                } else {
                    setorOuInstituicao = ((Convidado) p).getInstituicao();
                }

                // IDs das reuniões
                ArrayList<String> idsStr = new ArrayList<>();
                for (Reuniao r : p.getReunioes()) {
                    idsStr.add(String.valueOf(r.getId()));
                }

                String idsReunioes = String.join(",", idsStr);

                String linha = tipo + ";" + p.getNome() + ";" + p.getEmail() + ";" +
                               setorOuInstituicao + ";" + idsReunioes;

                pw.println(linha);
            }

        } catch (IOException e) {
            System.err.println("Erro ao gravar participantes.csv: " + e.getMessage());
        }
    }

    // Classe auxiliar

    public static class ParticipanteComReunioes {
        public Participante participante;
        public ArrayList<Integer> idsReunioes;

        public ParticipanteComReunioes(Participante participante, ArrayList<Integer> idsReunioes) {
            this.participante = participante;
            this.idsReunioes = idsReunioes;
        }
    }
}
