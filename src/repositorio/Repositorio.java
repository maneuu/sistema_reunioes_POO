package repositorio;

public class Repositorio {

}
//Atributos:
//- ArrayList<Reuniao> reunioes
//- ArrayList<Participante> participantes
//- int proximoIdReuniao = 1
//
//Métodos de Busca:
//- Reuniao localizarReuniao(int id)
//- Participante localizarParticipante(String nome)
//- boolean existeParticipanteComNome(String nome)
//- boolean existeReuniaoNaData(String data)
//
//Métodos de Adição: 
//- void adicionarReuniao(Reuniao r)
//- void adicionarParticipante(Participante p)
//
//Métodos de Remoção:
//- void removerReuniao(int id)
//- void removerParticipante(String nome) [se necessário]
//
//Métodos de Listagem: 
//- ArrayList<Reuniao> getReunioes()
//- ArrayList<Participante> getParticipantes()
//- ArrayList<Empregado> getEmpregados() [filtra por tipo]
//- ArrayList<Convidado> getConvidados() [filtra por tipo]
//
//Geração de ID:
//- int gerarProximoId()