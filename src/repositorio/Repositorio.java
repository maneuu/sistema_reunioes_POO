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
        File f1 = null;
        File f2 = null;

        try {
            // Obter caminho dos arquivos
            f1 = new File(new File(".\\reunioes.csv").getCanonicalPath());
            f2 = new File(new File(".\\participantes.csv").getCanonicalPath());

            // Se não existem, cria arquivos vazios
            if (!f1.exists() || !f2.exists()) {
                FileWriter arquivo1 = new FileWriter(f1);
                arquivo1.close();
                FileWriter arquivo2 = new FileWriter(f2);
                arquivo2.close();
                return;
            }

        } catch (Exception ex) {
            throw new RuntimeException("Problema na criação dos arquivos CSV:  " + ex.getMessage());
        }

        // Ler arquivo de reuniões
        Scanner arquivo1 = null;
        try {
            arquivo1 = new Scanner(f1);
            while (arquivo1.hasNextLine()) {
                String linha = arquivo1.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String data = partes[1];
                String assunto = partes[2];

                Reuniao r = new Reuniao(id, data, assunto);
                this.adicionarReuniao(r);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler reunioes.csv: " + e.getMessage());
        } finally {
            if (arquivo1 != null) arquivo1.close();
        }

        // Ler arquivo de participantes
        Scanner arquivo2 = null;
        try {
            arquivo2 = new Scanner(f2);
            while (arquivo2.hasNextLine()) {
                String linha = arquivo2.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");
                String tipo = partes[0];
                String nome = partes[1];
                String email = partes[2];
                String complemento = partes[3];

                Participante p = null;

                // Criar participante baseado no tipo
                if (tipo.equals("EMPREGADO")) {
                    p = new Empregado(nome, email, complemento);
                } else if (tipo.equals("CONVIDADO")) {
                    p = new Convidado(nome, email, complemento);
                } else {
                    throw new RuntimeException("Tipo inválido em participantes.csv: " + tipo);
                }

                this.adicionarParticipante(p);

                // Processar IDs das reuniões (se existirem)
                if (partes.length > 4 && !partes[4].isEmpty()) {
                    String[] idsReunioes = partes[4].split(",");

                    for (String idStr : idsReunioes) {
                        int idReuniao = Integer.parseInt(idStr.trim());
                        Reuniao r = this.localizarReuniao(idReuniao);

                        if (r != null) {
                            r.adicionarParticipante(p);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler participantes.csv: " + e.getMessage());
        } finally {
            if (arquivo2 != null) arquivo2.close();
        }

        // Ajustar próximo ID de reunião
        if (!reunioes.isEmpty()) {
            int maiorId = 0;
            for (Reuniao r : reunioes) {
                if (r.getId() > maiorId) {
                    maiorId = r.getId();
                }
            }
            ajustarProximoId(maiorId);
        }
    }

    public void gravarObjetos() {
        FileWriter arquivo1 = null;
        FileWriter arquivo2 = null;

        // Gravar participantes
        try {
            arquivo1 = new FileWriter(new File(".\\participantes.csv").getCanonicalPath());

            for (Participante p :  participantes) {
                String tipo = (p instanceof Empregado) ? "EMPREGADO" : "CONVIDADO";
                String complemento;

                if (p instanceof Empregado) {
                    complemento = ((Empregado) p).getSetor();
                } else {
                    complemento = ((Convidado) p).getInstituicao();
                }

                String dados = tipo + ";" + p.getNome() + ";" + p.getEmail() + ";" + complemento + ";";

                // Coletar IDs das reuniões
                ArrayList<String> idsReunioes = new ArrayList<>();
                for (Reuniao r : p.getReunioes()) {
                    idsReunioes.add(String.valueOf(r.getId()));
                }
                String ids = String.join(",", idsReunioes);

                arquivo1.write(dados + ids + "\n");
            }

            arquivo1.close();

        } catch (Exception e) {
            throw new RuntimeException("Problema na gravação de participantes.csv: " + e.getMessage());
        }

        // Gravar reuniões
        try {
            arquivo2 = new FileWriter(new File(".\\reunioes.csv").getCanonicalPath());

            for (Reuniao r : reunioes) {
                arquivo2.write(r.getId() + ";" + r.getData() + ";" + r.getAssunto() + "\n");
            }

            arquivo2.close();

        } catch (Exception e) {
            throw new RuntimeException("Problema na gravação de reunioes.csv: " + e.getMessage());
        }
    }
}