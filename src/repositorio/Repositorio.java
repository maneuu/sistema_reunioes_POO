package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;
import modelo.Reuniao;

public class Repositorio {

    private ArrayList<Reuniao> reunioes = new ArrayList<>();
    private ArrayList<Participante> participantes = new ArrayList<>();
    private int proximoIdReuniao = 1;

    public Repositorio() {
    }

    // Adição de objetos
    public void adicionarReuniao(Reuniao r) {
        reunioes.add(r);
    }

    public void adicionarParticipante(Participante p) {
        participantes.add(p);
    }

    // Busca de objetoss

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

    // Gerenciamento de IDs
    public int gerarProximoId() {
        return proximoIdReuniao++;
    }

    public void ajustarProximoId(int maiorId) {
        proximoIdReuniao = maiorId + 1;
    }

    // Leitura e gravação de arquivos

    public void lerObjetos() {
        try {
            File f1 = new File(".\\reunioes.csv");
            File f2 = new File(".\\participantes.csv");

            if (!f1.exists() || !f2.exists()) {
                if (!f1.exists()) new FileWriter(f1).close();
                if (!f2.exists()) new FileWriter(f2).close();
                return;
            }

            // Ler reuniões
            Scanner arquivo1 = new Scanner(f1);
            while (arquivo1.hasNextLine()) {
                String linha = arquivo1.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");
                Reuniao r = new Reuniao(Integer.parseInt(partes[0]), partes[1], partes[2]);
                adicionarReuniao(r);
            }
            arquivo1.close();

            // Ler participantes
            Scanner arquivo2 = new Scanner(f2);
            while (arquivo2.hasNextLine()) {
                String linha = arquivo2.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");
                
                // Criar participante
                Participante p;
                if (partes[0].equals("EMPREGADO")) {
                    p = new Empregado(partes[1], partes[2], partes[3]);
                } else {
                    p = new Convidado(partes[1], partes[2], partes[3]);
                }
                
                adicionarParticipante(p);

                // Relacionar com reuniões
                if (partes.length > 4 && !partes[4].isEmpty()) {
                    for (String idStr : partes[4].split(",")) {
                        Reuniao r = localizarReuniao(Integer.parseInt(idStr.trim()));
                        if (r != null) r.adicionarParticipante(p);
                    }
                }
            }
            arquivo2.close();

            // Ajustar próximo ID
            if (!reunioes.isEmpty()) {
                int maiorId = 0;
                for (Reuniao r : reunioes) {
                    if (r.getId() > maiorId) maiorId = r.getId();
                }
                ajustarProximoId(maiorId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler arquivos CSV: " + e.getMessage());
        }
    }

    public void gravarObjetos() {
        try {
            // Gravar participantes
            FileWriter arquivo1 = new FileWriter(".\\participantes.csv");
            
            for (Participante p : participantes) {
                String tipo;
                String complemento;
                
                if (p instanceof Empregado) {
                    tipo = "EMPREGADO";
                    complemento = ((Empregado) p).getSetor();
                } else {
                    tipo = "CONVIDADO";
                    complemento = ((Convidado) p).getInstituicao();
                }

                // Coletar IDs das reuniões
                ArrayList<String> idsReunioes = new ArrayList<>();
                for (Reuniao r : p.getReunioes()) {
                    idsReunioes.add(String.valueOf(r.getId()));
                }
                
                String linha = tipo + ";" + p.getNome() + ";" + p.getEmail() + ";" 
                             + complemento + ";" + String.join(",", idsReunioes) + "\n";
                
                arquivo1.write(linha);
            }
            arquivo1.close();

            // Gravar reuniões
            FileWriter arquivo2 = new FileWriter(".\\reunioes.csv");
            
            for (Reuniao r : reunioes) {
                arquivo2.write(r.getId() + ";" + r.getData() + ";" + r.getAssunto() + "\n");
            }
            arquivo2.close();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gravar arquivos CSV: " + e.getMessage());
        }
    }
}