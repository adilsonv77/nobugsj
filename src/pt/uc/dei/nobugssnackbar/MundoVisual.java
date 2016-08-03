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

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.ExercicioFactory;

/**
 * 
 * @author Adilson Vahldick 
 */
public class MundoVisual extends JFrame {

	public static void proxMundo(int indiceMundo) {
		if (indiceMundo >= euMesmo.nomesArquivosXML.length) {
			throw new MundoException("\315ndice do mundo fora da faixa.");
		} else {
			euMesmo.mundoAtual = indiceMundo;
			euMesmo.executarProxMundo = true;
			return;
		}
	}

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

	private MundoFurbot mundoFurbot;

	private MundoVisual(Exercicio exercicio, String autor) throws Exception {
		executou = false;
		euMesmo = this;
		
		mundoFurbot = new MundoFurbot(exercicio);
		mundoFurbot.addDisseramListener(new DisseramListener(){

			@Override
			public void disse(String texto) {
				console.setText((new StringBuilder(String
						.valueOf(console.getText()))).append(texto).append("\n")
						.toString());
			}

			@Override
			public void limpar() {
				console.setText("");
			}});

		mundoFurbot.addFinalizouExecucaoListener(new FinalizouExecucaoListener(){

			@Override
			public void finalizouExecucao() {
				if (executarProxMundo) {
					executarProxMundo = false;
					try {
						executar(ExercicioFactory.create(nomesArquivosXML[mundoAtual], furbotClass));
					} catch (Exception e) {
						throw new MundoException(e.getMessage());
					}
				} else {
					jbExecutar.setEnabled(true);
					jbParar.setEnabled(false);
					jbRenovar.setEnabled(true);
				}
			}});
		
		initComponents(exercicio, autor);
	}

	public MundoVisual(String autor, Exercicio exercicio, Class<?> furbotClass,
			String nomesArquivosXML[]) throws Exception {
		this(exercicio, autor);
		this.nomesArquivosXML = nomesArquivosXML;
		this.furbotClass = furbotClass;
	}

	private void initComponents(Exercicio exercicio, String autor) {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		String title = "Mundo do Furbot v " + Furbot.VERSAO; 
		if (autor != null) {
			title = title + " ["+autor+"]";
		}
		setTitle(title);
		JPanel jp = getEnunciado(exercicio.getEnunciado());
		getContentPane().add(jp, "North");
		JScrollPane scrollmundo = new JScrollPane();
		scrollmundo.setViewportView(mundoFurbot);
		getContentPane().add(mundoFurbot, "Center");
		jp.add(getControle(exercicio), "South");
		console = new JTextArea();
		JScrollPane scroll = new JScrollPane(console);
		console.setEditable(false);
		console.setRows(5);
		getContentPane().add(scroll, "South");
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent arg0) {
				mundoFurbot.parar();
			}

		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getEnunciado(String enunciado) {
		JPanel jp = new JPanel();
		jlEnunciado = new JLabel(enunciado);
		jp.add(jlEnunciado);
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
				executarProxMundo = false;
				novaSequencia(exercicio);
				executar(exercicio);
			}

		};
		jbExecutar.addActionListener(action);

		jbRenovar = new JButton("New");
		jpBotoes.add(jbRenovar);
		jbRenovar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mundoFurbot.parar();
				novaSequencia(exercicio);
				try {
					mundoFurbot.setExercicio(exercicio);
					mundoFurbot.reiniciar();
					pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
				executou = false;
				mundoFurbot.requestFocus();
			}

		});
		jbParar = new JButton("Stop");
		jbParar.setEnabled(false);
		jpBotoes.add(jbParar);
		jbParar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				mundoFurbot.parar();
				jbExecutar.setEnabled(true);
				jbParar.setEnabled(false);
			}

		});
		JPanel jpSlider = new JPanel(new GridLayout(2, 1));
		slider = new JSlider();
		slider.setMaximum(2000);
		slider.setInverted(true);
		slider.setValue(mundoFurbot.getTempoEspera());
		slider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent ev) {
				mundoFurbot.setTempoEspera(slider.getValue());
				mundoFurbot.requestFocus();
			}

		});
		jpSlider.add(new JLabel("   Velocidade :"));
		jpSlider.add(slider);
		jp.add(jpSlider);
		return jconsole;
	}

	private void novaSequencia(Exercicio exercicio) {
		jlEnunciado.setText(exercicio.getEnunciado());
		atributos.clear();
	}

	private void executar(Exercicio exercicio) {
		if (executou)
			try {
				jlEnunciado.setText(exercicio.getEnunciado());
				mundoFurbot.setExercicio(exercicio);
				mundoFurbot.reiniciar();
				pack();
			} catch (Exception e) {
				e.printStackTrace();
			}
		habilitarBotoesExecucao();
		mundoFurbot.executar();
		executou = true;
		mundoFurbot.requestFocus();
	}

	private void habilitarBotoesExecucao() {
		jbExecutar.setEnabled(false);
		jbParar.setEnabled(true);
		jbRenovar.setEnabled(false);
	}

	public static void iniciar(String nomeArquivo) {
		iniciar(nomeArquivo, null, null);
	}

	public static void iniciar(String nomeArquivo, String autor) {
		iniciar(nomeArquivo, null, autor);
	}
	
	public static void iniciar(String nomeArquivo, Class<?> classExercicio, final String autor) {
		try {
			final Exercicio exercicio = ExercicioFactory.create(nomeArquivo,classExercicio);
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					try {
						new MundoVisual(exercicio, autor);
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
	
	public static void iniciar(String nomeArquivoXML, Class<?> furbotClass) {
		iniciar(nomeArquivoXML, furbotClass, null);
	}

	
	public static void iniciarSequencia(Class<?> furbotClass, String... nomesArquivosXML) {
		iniciarSequencia(null, furbotClass, nomesArquivosXML);
	}
	
	public static void iniciarSequencia(final String autor, Class<?> furbotClass, String... nomesArquivosXML) {
		try {
			final Exercicio exercicio = ExercicioFactory.create(
					nomesArquivosXML[0], furbotClass);
			final String arquivosXML[] = nomesArquivosXML;
			final Class<?> classe = furbotClass;
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					try {
						new MundoVisual(autor, exercicio, classe, arquivosXML);
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

	public static void main(String args[]) throws Exception {
		if (args.length > 0)
			iniciar(args[0]);
		throw new Exception("Faltou informar o nome do arquivo do enunciado.");
	}

	private JTextArea console;
	private boolean executou;
	private String nomesArquivosXML[];
	private int mundoAtual;
	private boolean executarProxMundo;
	private Class<?> furbotClass;
	private static MundoVisual euMesmo;
	private static HashMap<String, Object> atributos = new HashMap<String, Object>();
	private JLabel jlEnunciado;
	private JButton jbExecutar;
	private JButton jbParar;
	private JSlider slider;
	private JButton jbRenovar;

}