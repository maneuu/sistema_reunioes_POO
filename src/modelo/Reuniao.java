package modelo;
import java.util.ArrayList;

public class Reuniao {
	private int id;
	private String data;
	private String assunto;
	private ArrayList<Participante> participantes = new ArrayList<Participante>();
	
	public Reuniao(int id, String data, String assunto) {
		this.id = id;
		this.data = data;
		this.assunto = assunto;
	}
	// Getters e Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	    return new ArrayList<Participante>(participantes);
	}
	
}
