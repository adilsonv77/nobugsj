package pt.uc.dei.nobugssnackbar;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pt.uc.dei.nobugssnackbar.goals.Objective;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.ExercicioFactory;
import pt.uc.dei.nobugssnackbar.suporte.JCheckList;

/**
 * 
 * @author Adilson Vahldick 
 */
public class MundoVisual extends JFrame {

	private static final long serialVersionUID = 1L;

	public static int getMundo() {
		return euMesmo.mundoAtual;
	}

	public static <T> void setAtributo(String nome, T valor) {
		atributos.put(nome, valor);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAtributo(String nome) {
		return (T) atributos.get(nome);
	}

	public static boolean temAtributo(String nome) {
		return atributos.containsKey(nome);
	}

	private MundoVisual(Exercicio exercicio, Class<? extends SnackMan> snackManClass) throws Exception {
		executou = false;
		euMesmo = this;
		
		mundoNoBugs = new NoBugsVisual(exercicio, snackManClass);
		mundoNoBugs.setMundoVisual(this);
		
		initComponents(exercicio);
	}

	private void initComponents(Exercicio exercicio) {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		String title = "NoBugsJ v " + SnackMan.VERSAO; 
		setTitle(title);
		
		getContentPane().add(mundoNoBugs, "Center");
		
		objetivos = new JCheckList();
		objetivos.setTitle("Objetivos:");
		
		for (Objective obj:mundoNoBugs.getObjectives())
			objetivos.addItem(obj.getText());
		
		getContentPane().add(objetivos, "East");
		
		console = new JTextArea();
		JScrollPane scroll = new JScrollPane(console);
		console.setEditable(false);
		console.setRows(5);
		getContentPane().add(scroll, "South");
		
		JPanel jp = getEnunciado();
		getContentPane().add(jp, "North");
		jp.add(getControle(exercicio), "South");
		
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent arg0) {
				mundoNoBugs.parar();
			}

		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getEnunciado() {
		JPanel jp = new JPanel();
		return jp;
	}

	private JPanel getControle(final Exercicio exercicio) {
		JPanel jconsole = new JPanel(new BorderLayout());
		JPanel jp = new JPanel(new GridLayout(2, 1));
		JPanel jpBotoes = new JPanel();
		jp.add(jpBotoes);

		jbExecutar = new JButton("Run");
		jpBotoes.add(jbExecutar);
		jconsole.add(jp, "West");
		ActionListener action = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mundoAtual = 0;
				novaSequencia(exercicio);
				executar(exercicio);
			}

		};
		jbExecutar.addActionListener(action);

		jbRenovar = new JButton("New");
		jpBotoes.add(jbRenovar);
		jbRenovar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mundoNoBugs.parar();
				novaSequencia(exercicio);
				try {
					mundoNoBugs.setExercicio(exercicio);
					mundoNoBugs.setTempoEspera(slider.getValue());
					mundoNoBugs.reiniciar();
					pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
				executou = false;
				mundoNoBugs.requestFocus();
			}

		});
		jbParar = new JButton("Stop");
		jbParar.setEnabled(false);
		jpBotoes.add(jbParar);
		jbParar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				mundoNoBugs.parar();
				jbExecutar.setEnabled(true);
				jbParar.setEnabled(false);
			}

		});
		
		jbEnunciado = new JButton("Enunciado");
		jbEnunciado.setEnabled(true);
		jpBotoes.add(jbEnunciado);
		jbEnunciado.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String explane = exercicio.getExplanation();
				
				JLabel msg = new JLabel("<html><body style='width:400px'>"+explane+"</body></html>");
				JOptionPane.showMessageDialog(null, msg, "Enunciado", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JPanel jpSlider = new JPanel(new GridLayout(2, 1));
		slider = new JSlider();
		slider.setMaximum(2000);
		slider.setInverted(true);
		slider.setValue(mundoNoBugs.getTempoEspera());
		slider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent ev) {
				mundoNoBugs.setTempoEspera(slider.getValue());
				mundoNoBugs.requestFocus();
			}

		});
		jpSlider.add(new JLabel("   Velocidade :"));
		jpSlider.add(slider);
		jp.add(jpSlider);
		return jconsole;
	}

	private void novaSequencia(Exercicio exercicio) {
		atributos.clear();
		objetivos.uncheckAllItems();
	}

	private void executar(Exercicio exercicio) {
		if (executou)
			try {
				mundoNoBugs.setExercicio(exercicio);
				mundoNoBugs.reiniciar();
				pack();
			} catch (Exception e) {
				e.printStackTrace();
			}
		habilitarBotoesExecucao();
		mundoNoBugs.setTempoEspera(slider.getValue());
		mundoNoBugs.executar();
		executou = true;
		mundoNoBugs.requestFocus();
	}

	public void fimExecucao() {
		jbExecutar.setEnabled(true);
		jbParar.setEnabled(false);
		jbRenovar.setEnabled(true);
	}

	private void habilitarBotoesExecucao() {
		jbExecutar.setEnabled(false);
		jbParar.setEnabled(true);
		jbRenovar.setEnabled(false);
	}

	public void checkGoal(int index) {
		objetivos.checkItem(index);
	}
	
	public void addTextConsole(Object text) {
		console.setText(console.getText()+text+"\n");
	}

	public static void iniciar(String nomeArquivo, final Class<? extends SnackMan> snackManClass) {
		try {
			
			final Exercicio exercicio = ExercicioFactory.create(nomeArquivo);
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					try {
						new MundoVisual(exercicio, snackManClass);
					} catch (ClassNotFoundException classE) {
						JOptionPane.showMessageDialog(null, (new StringBuilder(
								"Voc\352 precisa criar uma classe de nome "))
								.append(classE.getLocalizedMessage())
								.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		} catch (Exception e) {
			System.out.println("Problemas no carregamento do Exerc\355cio");
			e.printStackTrace();
		}

	}
	
	private JTextArea console;
	private boolean executou;
	private int mundoAtual;
	private static MundoVisual euMesmo;
	private static HashMap<String, Object> atributos = new HashMap<String, Object>();
	private JButton jbExecutar;
	private JButton jbEnunciado;
	private JButton jbParar;
	private JSlider slider;
	private JButton jbRenovar;
	private NoBugsVisual mundoNoBugs;
	private JCheckList objetivos;
	

}