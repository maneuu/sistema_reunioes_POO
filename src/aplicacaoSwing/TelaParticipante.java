/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package aplicacaoSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Participante;
import modelo.Reuniao;
import requisitos.Fachada;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class TelaParticipante {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label_3;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button_1;
	private JLabel label_4;
	private JTextField textField_2;
	private JPanel panel;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JLabel label;
	private ButtonGroup grupo;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaReuniao window = new TelaReuniao();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public TelaParticipante() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Participante");
		frame.setBounds(100, 100, 684, 368);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 616, 165);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label_3 = new JLabel("selecione");
		label_3.setBounds(21, 178, 431, 14);
		frame.getContentPane().add(label_3);

		label_1 = new JLabel("nome:");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(21, 221, 71, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(73, 218, 134, 20);
		frame.getContentPane().add(textField);

		label_2 = new JLabel("email:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(21, 266, 54, 14);
		frame.getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(73, 263, 134, 20);
		frame.getContentPane().add(textField_1);

		button_1 = new JButton("Criar ");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField.getText().isEmpty() || textField_1.getText().isEmpty()) {
						label.setText("campo vazio");
						return;
					}
					String nome = textField.getText();
					String email = textField_1.getText();
					String extra = textField_2.getText();
					if (radioButton.isSelected())
						Fachada.criarEmpregado(nome, email, extra);
					else
						Fachada.criarConvidado(nome, email, extra);

					label.setText("participante criado");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(528, 239, 71, 23);
		frame.getContentPane().add(button_1);

		label_4 = new JLabel("setor/institui\u00E7\u00E3o:");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(230, 219, 126, 14);
		frame.getContentPane().add(label_4);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(332, 218, 134, 20);
		frame.getContentPane().add(textField_2);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Participante", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(226, 244, 248, 39);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		radioButton = new JRadioButton("Empregado");
		radioButton.setSelected(true);
		panel.add(radioButton);

		radioButton_1 = new JRadioButton("Convidado");
		radioButton_1.setSelected(false);
		panel.add(radioButton_1);

		grupo = new ButtonGroup();
		grupo.add(radioButton);
		grupo.add(radioButton_1);

		label = new JLabel("");
		label.setBounds(20, 304, 600, 14);
		frame.getContentPane().add(label);

		frame.setVisible(true);

	}

	public void listagem() {
		try {
			ArrayList<Participante> lista = Fachada.listarParticipantes();
			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("classe");
			model.addColumn("nome");
			model.addColumn("email");
			model.addColumn("reuniao");

			// criar as linhas da tabela
			String texto;
			for (Participante p : lista) {
				String classe = p.getClass().getSimpleName();
				if (p.getReunioes().isEmpty())
					model.addRow(new Object[] {classe, p.getNome(), p.getEmail(), "sem reunioes" });
				else
					for (Reuniao r : p.getReunioes()) {
						model.addRow(new Object[] {classe, p.getNome(), p.getEmail(),
								r.getId() + " " + r.getData() + " - " + r.getAssunto() });
					}
			}
			label_3.setText("resultados: " + lista.size() + " participantes");
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
			table.getColumnModel().getColumn(2).setMinWidth(150);	
			table.getColumnModel().getColumn(3).setMinWidth(200);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
