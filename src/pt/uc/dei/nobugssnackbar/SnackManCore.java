package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.exceptions.SnackManEncerradoException;
import pt.uc.dei.nobugssnackbar.grafos.Grafo;
import pt.uc.dei.nobugssnackbar.grafos.Vertice;
import pt.uc.dei.nobugssnackbar.suporte.FinishedRunListener;

/**
 * 
 * This is the core class for SnackMan, representing the data. SnackMan class is an interface for 
 * students programm it.
 * 
 * @author adilsonv77
 *
 */

public class SnackManCore implements Runnable {

	private boolean podeParar;
	private List<FinishedRunListener> fimListeners = new ArrayList<>();
	private SnackMan snackMan;
	private Grafo graph;
	private HashMap<String, Point> map;
	private Vertice[] counterPositions;
	private Point curPosition;
	private Point initial;
	private int meuTempoEspera = -1;
	private NoBugsVisual noBugsVisual;
	private Vertice vertCurPosition;
	public static int tempoEspera = 500;
	
	public SnackManCore(SnackMan snackMan) {
		this.snackMan = snackMan;
		this.snackMan.setCore(this);
		createPath();
		counterPositions = new Vertice[]{graph.acharVertice("n21"), graph.acharVertice("n31"), graph.acharVertice("n79")};
	}

	public void setInitialPosition(String position) {
		if (position.equals("initial")) {
			this.vertCurPosition = graph.acharVertice("n1");
			this.initial = map.get(vertCurPosition.getNome());
			this.curPosition = this.initial;
		}
	}

	public void paint(Graphics g) {
		
		int curX = (int) Math.round(Math.floor(this.curPosition.getX()));
		int curY = (int) Math.round(Math.floor(this.curPosition.getY()));
		
		curY = curY - 77; // 77 is the snackman height

		g.drawImage(this.snackMan.getImage(), curX, curY, null);
		
	}
	
	public void run() {
		podeParar = false;
		MundoException ex = null;
		try {
			esperar(1);
			serve();
		} catch (MundoException e) {
			ex = e;
			e.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		podeParar = false;
		notifyFinishedRun();
		if (ex != null)
			throw ex;
		else
			return;

	}

	private void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void addFinishedRunListener(FinishedRunListener listener) {
		fimListeners.add(listener);
	}
	
	private void notifyFinishedRun() {
		for (FinishedRunListener list:fimListeners)
			list.finished();
	}

	public void parar() {
		this.podeParar = true;
		
	}
	
	private boolean isParar() {
		return this.podeParar;
	}

	private void serve() throws Exception {
		snackMan.serve();
		
	}
	public void goToBarCounter(int counter) throws Exception {
		if (counter < 0 || counter > 3) {
			throw new Exception("A posição " + counter + " não existe no balcão " );
		}
		
		this.animateSnackMan(counterPositions[counter-1]);
	}

	private void animateSnackMan(Vertice dest) throws Exception {
		if (isParar())
			throw new SnackManEncerradoException();
		
		ArrayList<Vertice> solution = graph.encontrarMenorCaminhoDijkstra(this.vertCurPosition, dest);
		
		for (Vertice v:solution) {
			
			if (isParar())
				return;
			
			noBugsVisual.changeSnackManPosition(v);

			try {
				Thread.sleep(getTempoEspera());
			} catch (Exception ex) {
				return;
			}
		}
		
		
	}
	
	public void changeSnackManPosition(Vertice v) {
		
		this.curPosition = map.get(v.getNome());
		this.vertCurPosition = v;
		
	}

	public int getTempoEspera() {
		if (meuTempoEspera  == -1)
			return tempoEspera;
		else
			return meuTempoEspera;
	}

	public void setTempoEspera(int milisegundos) {
		meuTempoEspera = milisegundos;
	}

	public NoBugsVisual getMundo() {
		return noBugsVisual;
	}

	public void setMundo(NoBugsVisual mundo) {
		this.noBugsVisual = mundo;
	}
	
	private void createPath() {
		graph = new Grafo();
		
		graph.addAresta(2, "n2", "n1");
		graph.addAresta(2, "n2", "n3");
		graph.addAresta(2, "n3", "n2");
		graph.addAresta(2, "n3", "n4");
		graph.addAresta(2, "n4", "n3");
		graph.addAresta(2, "n4", "n5");
		graph.addAresta(2, "n5", "n4");
		graph.addAresta(2, "n5", "n6");
		graph.addAresta(2, "n6", "n5");
		graph.addAresta(2, "n6", "n7");
		graph.addAresta(2, "n7", "n6");
		graph.addAresta(2, "n7", "n8");
		graph.addAresta(2, "n8", "n7");
		graph.addAresta(2, "n8", "n9");
		graph.addAresta(2, "n9", "n8");
		graph.addAresta(2, "n9", "n10");
		graph.addAresta(2, "n10", "n9");
		graph.addAresta(2, "n10", "n11");
		graph.addAresta(2, "n12", "n11");
		graph.addAresta(2, "n12", "n13");
		graph.addAresta(2, "n13", "n12");
		graph.addAresta(2, "n13", "n14");
		graph.addAresta(2, "n14", "n13");
		graph.addAresta(2, "n14", "n15");
		graph.addAresta(2, "n15", "n14");
		graph.addAresta(2, "n15", "n16");
		graph.addAresta(2, "n16", "n15");
		graph.addAresta(2, "n16", "n17");
		graph.addAresta(2, "n17", "n16");
		graph.addAresta(2, "n17", "n18");
		graph.addAresta(2, "n18", "n17");
		graph.addAresta(2, "n18", "n19");
		graph.addAresta(2, "n19", "n18");
		graph.addAresta(2, "n19", "n20");
		graph.addAresta(2, "n20", "n19");
		graph.addAresta(2, "n20", "n21");
		graph.addAresta(1, "n22", "n21");
		graph.addAresta(1, "n22", "n23");
		graph.addAresta(1, "n23", "n22");
		graph.addAresta(1, "n23", "n24");
		graph.addAresta(1, "n24", "n23");
		graph.addAresta(1, "n24", "n25");
		graph.addAresta(1, "n25", "n24");
		graph.addAresta(1, "n25", "n26");
		graph.addAresta(1, "n26", "n25");
		graph.addAresta(1, "n26", "n27");
		graph.addAresta(1, "n27", "n26");
		graph.addAresta(1, "n27", "n28");
		graph.addAresta(1, "n28", "n27");
		graph.addAresta(1, "n28", "n29");
		graph.addAresta(1, "n29", "n28");
		graph.addAresta(1, "n29", "n30");
		graph.addAresta(1, "n30", "n29");
		graph.addAresta(1, "n30", "n31");
		graph.addAresta(2, "n32", "n31");
		graph.addAresta(2, "n32", "n33");
		graph.addAresta(2, "n33", "n32");
		graph.addAresta(2, "n33", "n34");
		graph.addAresta(2, "n34", "n33");
		graph.addAresta(2, "n34", "n35");
		graph.addAresta(2, "n35", "n34");
		graph.addAresta(2, "n35", "n36");
		graph.addAresta(2, "n36", "n35");
		graph.addAresta(2, "n36", "n37");
		graph.addAresta(2, "n37", "n36");
		graph.addAresta(2, "n37", "n38");
		graph.addAresta(2, "n38", "n37");
		graph.addAresta(2, "n38", "n39");
		graph.addAresta(2, "n39", "n38");
		graph.addAresta(2, "n39", "n40");
		graph.addAresta(2, "n40", "n39");
		graph.addAresta(2, "n40", "n11");
		graph.addAresta(1, "n41", "n31");
		graph.addAresta(1, "n41", "n42");
		graph.addAresta(1, "n42", "n41");
		graph.addAresta(1, "n42", "n43");
		graph.addAresta(1, "n43", "n42");
		graph.addAresta(1, "n43", "n44");
		graph.addAresta(1, "n44", "n43");
		graph.addAresta(1, "n44", "n45");
		graph.addAresta(1, "n45", "n44");
		graph.addAresta(1, "n45", "n46");
		graph.addAresta(1, "n46", "n45");
		graph.addAresta(1, "n46", "n47");
		graph.addAresta(1, "n47", "n46");
		graph.addAresta(1, "n47", "n48");
		graph.addAresta(1, "n48", "n47");
		graph.addAresta(1, "n48", "n49");
		graph.addAresta(1, "n49", "n48");
		graph.addAresta(1, "n49", "n50");
		graph.addAresta(1, "n50", "n49");
		graph.addAresta(1, "n50", "n51");
		graph.addAresta(1, "n51", "n50");
		graph.addAresta(1, "n51", "n52");
		graph.addAresta(1, "n52", "n51");
		graph.addAresta(1, "n52", "n53");
		graph.addAresta(1, "n53", "n52");
		graph.addAresta(1, "n53", "n54");
		graph.addAresta(1, "n54", "n53");
		graph.addAresta(1, "n54", "n55");
		graph.addAresta(1, "n55", "n54");
		graph.addAresta(1, "n55", "n56");
		graph.addAresta(1, "n56", "n55");
		graph.addAresta(1, "n56", "n57");
		graph.addAresta(1, "n57", "n56");
		graph.addAresta(1, "n57", "n58");
		graph.addAresta(1, "n58", "n57");
		graph.addAresta(1, "n58", "n59");
		graph.addAresta(1, "n59", "n58");
		graph.addAresta(1, "n59", "n1");
		graph.addAresta(2, "n60", "n1");
		graph.addAresta(2, "n60", "n61");
		graph.addAresta(2, "n61", "n60");
		graph.addAresta(2, "n61", "n62");
		graph.addAresta(2, "n62", "n61");
		graph.addAresta(2, "n62", "n63");
		graph.addAresta(2, "n63", "n62");
		graph.addAresta(2, "n63", "n64");
		graph.addAresta(2, "n64", "n63");
		graph.addAresta(2, "n64", "n65");
		graph.addAresta(2, "n65", "n64");
		graph.addAresta(2, "n65", "n66");
		graph.addAresta(2, "n66", "n65");
		graph.addAresta(2, "n66", "n67");
		graph.addAresta(2, "n67", "n66");
		graph.addAresta(2, "n67", "n68");
		graph.addAresta(2, "n68", "n67");
		graph.addAresta(2, "n68", "n69");
		graph.addAresta(1, "n70", "n31");
		graph.addAresta(1, "n70", "n71");
		graph.addAresta(1, "n71", "n70");
		graph.addAresta(1, "n71", "n72");
		graph.addAresta(1, "n72", "n71");
		graph.addAresta(1, "n72", "n73");
		graph.addAresta(1, "n73", "n72");
		graph.addAresta(1, "n73", "n74");
		graph.addAresta(1, "n74", "n73");
		graph.addAresta(1, "n74", "n75");
		graph.addAresta(1, "n75", "n74");
		graph.addAresta(1, "n75", "n76");
		graph.addAresta(1, "n76", "n75");
		graph.addAresta(1, "n76", "n77");
		graph.addAresta(1, "n77", "n76");
		graph.addAresta(1, "n77", "n78");
		graph.addAresta(1, "n78", "n77");
		graph.addAresta(1, "n78", "n79");
		graph.addAresta(1, "n80", "n79");
		graph.addAresta(1, "n80", "n81");
		graph.addAresta(1, "n81", "n80");
		graph.addAresta(1, "n81", "n82");
		graph.addAresta(1, "n82", "n81");
		graph.addAresta(1, "n82", "n83");
		graph.addAresta(1, "n83", "n82");
		graph.addAresta(1, "n83", "n84");
		graph.addAresta(1, "n84", "n83");
		graph.addAresta(1, "n84", "n85");
		graph.addAresta(1, "n85", "n84");
		graph.addAresta(1, "n85", "n86");
		graph.addAresta(1, "n86", "n85");
		graph.addAresta(1, "n86", "n87");
		graph.addAresta(1, "n87", "n86");
		graph.addAresta(1, "n87", "n88");
		graph.addAresta(1, "n88", "n87");
		graph.addAresta(1, "n88", "n89");
		graph.addAresta(1, "n89", "n88");
		graph.addAresta(1, "n89", "n90");
		graph.addAresta(1, "n90", "n89");
		graph.addAresta(1, "n90", "n91");
		graph.addAresta(1, "n91", "n90");
		graph.addAresta(1, "n91", "n92");
		graph.addAresta(1, "n92", "n91");
		graph.addAresta(1, "n92", "n93");
		graph.addAresta(1, "n93", "n92");
		graph.addAresta(1, "n93", "n94");
		graph.addAresta(1, "n94", "n93");
		graph.addAresta(1, "n94", "n95");
		graph.addAresta(1, "n95", "n94");
		graph.addAresta(1, "n95", "n96");
		graph.addAresta(1, "n96", "n95");
		graph.addAresta(1, "n96", "n97");
		graph.addAresta(1, "n97", "n96");
		graph.addAresta(1, "n97", "n98");
		graph.addAresta(1, "n98", "n97");
		graph.addAresta(1, "n98", "n99");
		graph.addAresta(1, "n99", "n98");
		graph.addAresta(1, "n99", "n1");
		graph.addAresta(2, "n100", "n79");
		graph.addAresta(2, "n100", "n101");
		graph.addAresta(2, "n101", "n100");
		graph.addAresta(2, "n101", "n102");
		graph.addAresta(2, "n102", "n101");
		graph.addAresta(2, "n102", "n103");
		graph.addAresta(2, "n103", "n102");
		graph.addAresta(2, "n103", "n104");
		graph.addAresta(2, "n104", "n103");
		graph.addAresta(2, "n104", "n105");
		graph.addAresta(2, "n105", "n104");
		graph.addAresta(2, "n105", "n106");
		graph.addAresta(2, "n107", "n106");
		graph.addAresta(2, "n107", "n108");
		graph.addAresta(2, "n108", "n107");
		graph.addAresta(2, "n108", "n109");
		graph.addAresta(2, "n109", "n108");
		graph.addAresta(2, "n109", "n110");
		graph.addAresta(2, "n110", "n109");
		graph.addAresta(2, "n110", "n111");
		graph.addAresta(2, "n111", "n110");
		graph.addAresta(2, "n111", "n112");
		graph.addAresta(2, "n112", "n111");
		graph.addAresta(2, "n112", "n113");
		graph.addAresta(2, "n113", "n112");
		graph.addAresta(2, "n113", "n114");
		graph.addAresta(2, "n114", "n113");
		graph.addAresta(2, "n114", "n115");
		graph.addAresta(2, "n115", "n114");
		graph.addAresta(2, "n115", "n116");
		graph.addAresta(2, "n116", "n115");
		graph.addAresta(2, "n116", "n1");
		graph.addAresta(2, "n117", "n106");
		graph.addAresta(2, "n117", "n118");
		graph.addAresta(2, "n118", "n117");
		graph.addAresta(2, "n118", "n119");
		graph.addAresta(2, "n119", "n118");
		graph.addAresta(2, "n119", "n120");
		graph.addAresta(2, "n120", "n119");
		graph.addAresta(2, "n120", "n121");
		graph.addAresta(2, "n121", "n120");
		graph.addAresta(2, "n121", "n122");
		graph.addAresta(2, "n122", "n121");
		graph.addAresta(2, "n122", "n123");
		graph.addAresta(2, "n123", "n122");
		graph.addAresta(2, "n123", "n124");
		graph.addAresta(2, "n124", "n123");
		graph.addAresta(2, "n124", "n125");
		graph.addAresta(2, "n125", "n124");
		graph.addAresta(2, "n125", "n126");
		graph.addAresta(2, "n126", "n125");
		graph.addAresta(2, "n126", "n69");
		graph.addAresta(2, "n128", "n127");
		graph.addAresta(2, "n128", "n129");
		graph.addAresta(2, "n129", "n128");
		graph.addAresta(2, "n129", "n130");
		graph.addAresta(2, "n130", "n129");
		graph.addAresta(2, "n130", "n131");
		graph.addAresta(2, "n131", "n130");
		graph.addAresta(2, "n131", "n132");
		graph.addAresta(2, "n132", "n131");
		graph.addAresta(2, "n132", "n133");
		graph.addAresta(2, "n133", "n132");
		graph.addAresta(2, "n133", "n134");
		graph.addAresta(2, "n134", "n133");
		graph.addAresta(2, "n134", "n135");
		graph.addAresta(2, "n135", "n134");
		graph.addAresta(2, "n135", "n136");
		graph.addAresta(2, "n136", "n135");
		graph.addAresta(2, "n136", "n137");
		graph.addAresta(2, "n137", "n136");
		graph.addAresta(2, "n137", "n1");
		graph.addAresta(2, "n138", "n127");
		graph.addAresta(2, "n138", "n139");
		graph.addAresta(2, "n139", "n138");
		graph.addAresta(2, "n139", "n140");
		graph.addAresta(2, "n140", "n139");
		graph.addAresta(2, "n140", "n141");
		graph.addAresta(2, "n141", "n140");
		graph.addAresta(2, "n141", "n142");
		graph.addAresta(2, "n142", "n141");
		graph.addAresta(2, "n142", "n143");
		graph.addAresta(2, "n143", "n142");
		graph.addAresta(2, "n143", "n144");
		graph.addAresta(2, "n144", "n143");
		graph.addAresta(2, "n144", "n145");
		graph.addAresta(2, "n145", "n144");
		graph.addAresta(2, "n145", "n146");
		graph.addAresta(2, "n146", "n145");
		graph.addAresta(2, "n146", "n147");
		graph.addAresta(2, "n147", "n146");
		graph.addAresta(2, "n147", "n11");
		graph.addAresta(2, "n149", "n148");
		graph.addAresta(2, "n149", "n150");
		graph.addAresta(2, "n150", "n149");
		graph.addAresta(2, "n150", "n151");
		graph.addAresta(2, "n151", "n150");
		graph.addAresta(2, "n151", "n152");
		graph.addAresta(2, "n152", "n151");
		graph.addAresta(2, "n152", "n153");
		graph.addAresta(2, "n153", "n152");
		graph.addAresta(2, "n153", "n154");
		graph.addAresta(2, "n154", "n153");
		graph.addAresta(2, "n154", "n155");
		graph.addAresta(2, "n155", "n154");
		graph.addAresta(2, "n155", "n156");
		graph.addAresta(2, "n156", "n155");
		graph.addAresta(2, "n156", "n157");
		graph.addAresta(2, "n157", "n156");
		graph.addAresta(2, "n157", "n158");
		graph.addAresta(2, "n158", "n157");
		graph.addAresta(2, "n158", "n159");
		graph.addAresta(2, "n159", "n158");
		graph.addAresta(2, "n159", "n1");
		graph.addAresta(2, "n148", "n160");
		graph.addAresta(2, "n148", "n149");
		graph.addAresta(2, "n160", "n148");
		graph.addAresta(2, "n160", "n161");
		graph.addAresta(2, "n161", "n160");
		graph.addAresta(2, "n161", "n162");
		graph.addAresta(2, "n162", "n161");
		graph.addAresta(2, "n162", "n163");
		graph.addAresta(2, "n163", "n162");
		graph.addAresta(2, "n163", "n164");
		graph.addAresta(2, "n164", "n163");
		graph.addAresta(2, "n164", "n165");
		graph.addAresta(2, "n165", "n164");
		graph.addAresta(2, "n165", "n166");
		graph.addAresta(2, "n166", "n165");
		graph.addAresta(2, "n166", "n167");
		graph.addAresta(2, "n167", "n166");
		graph.addAresta(2, "n167", "n168");
		graph.addAresta(2, "n168", "n167");
		graph.addAresta(2, "n168", "n169");
		graph.addAresta(2, "n169", "n168");
		graph.addAresta(2, "n169", "n170");
		graph.addAresta(2, "n170", "n169");
		graph.addAresta(2, "n170", "n171");
		graph.addAresta(2, "n171", "n170");
		graph.addAresta(2, "n171", "n172");
		graph.addAresta(2, "n172", "n171");
		graph.addAresta(2, "n172", "n173");
		graph.addAresta(2, "n173", "n172");
		graph.addAresta(2, "n173", "n174");
		graph.addAresta(2, "n174", "n173");
		graph.addAresta(2, "n174", "n175");
		graph.addAresta(2, "n175", "n174");
		graph.addAresta(2, "n175", "n176");
		graph.addAresta(2, "n176", "n175");
		graph.addAresta(2, "n176", "n177");
		graph.addAresta(2, "n177", "n176");
		graph.addAresta(2, "n177", "n178");
		graph.addAresta(2, "n178", "n177");
		graph.addAresta(2, "n178", "n179");
		graph.addAresta(2, "n1", "n2");
		graph.addAresta(2, "n1", "n59");
		graph.addAresta(2, "n1", "n60");
		graph.addAresta(2, "n1", "n99");
		graph.addAresta(2, "n1", "n116");
		graph.addAresta(2, "n1", "n137");
		graph.addAresta(2, "n1", "n159");
		graph.addAresta(2, "n11", "n10");
		graph.addAresta(2, "n11", "n12");
		graph.addAresta(2, "n11", "n40");
		graph.addAresta(2, "n11", "n147");
		graph.addAresta(2, "n11", "nIceCream3");
		graph.addAresta(2, "n21", "n20");
		graph.addAresta(2, "n21", "n22");
		graph.addAresta(2, "n31", "n30");
		graph.addAresta(2, "n31", "n32");
		graph.addAresta(2, "n31", "n41");
		graph.addAresta(2, "n31", "n70");
		graph.addAresta(2, "n79", "n78");
		graph.addAresta(2, "n79", "n80");
		graph.addAresta(2, "n79", "n80");
		graph.addAresta(2, "n79", "n100");
		graph.addAresta(2, "n106", "n105");
		graph.addAresta(2, "n106", "n107");
		graph.addAresta(2, "n106", "n117");
		graph.addAresta(2, "n127", "n128");
		graph.addAresta(2, "n127", "n138");
		graph.addAresta(2, "n69", "n68");
		graph.addAresta(2, "n69", "n126");
		
		graph.addAresta(2, "nIceCream3", "n11");
		graph.addAresta(2, "nIceCream3", "nIceCream2");

		graph.addAresta(2, "nIceCream2", "n11");
		graph.addAresta(2, "nIceCream2", "nIceCream1");
		
		graph.addAresta(2, "nIceCream1", "nIceCream2");
		graph.addAresta(2, "nIceCream1", "nIceCream");

		graph.addAresta(2, "nIceCream", "nIceCream1");
		graph.addAresta(2, "nIceCream", "n14");
		graph.addAresta(2, "nIceCream", "n12");
		graph.addAresta(2, "nIceCream", "nCoffee1");

		graph.addAresta(2, "nCoffee1", "nIceCream1");
		graph.addAresta(2, "nCoffee1", "nCoffee2");
		
		graph.addAresta(2, "nCoffee2", "nCoffee1");
		graph.addAresta(2, "nCoffee2", "nCoffee3");

		graph.addAresta(2, "nCoffee3", "nCoffee2");
		graph.addAresta(2, "nCoffee3", "nCoffee");
		
		graph.addAresta(2, "nCoffee", "nCoffee3");

  	    map = new HashMap<String, Point>();
		map.put("n1", new Point(270, 356));
		map.put("n2", new Point(266, 344));
		map.put("n3", new Point(262, 332));
		map.put("n4", new Point(258, 320));
		map.put("n5", new Point(254, 308));
		map.put("n6", new Point(250, 296));
		map.put("n7", new Point(246, 284));
		map.put("n8", new Point(242, 272));
		map.put("n9", new Point(238, 260));
		map.put("n10", new Point(234, 248));
		map.put("n11", new Point(230, 240));
		map.put("n12", new Point(218, 243));
		map.put("n13", new Point(206, 246));
		map.put("n14", new Point(194, 249));
		map.put("n15", new Point(182, 252));
		map.put("n16", new Point(170, 255));
		map.put("n17", new Point(158, 258));
		map.put("n18", new Point(146, 261));
		map.put("n19", new Point(134, 264));
		map.put("n20", new Point(122, 267));
		map.put("n21", new Point(110, 276));
		map.put("n22", new Point(110, 284));
		map.put("n23", new Point(110, 292));
		map.put("n24", new Point(110, 300));
		map.put("n25", new Point(110, 308));
		map.put("n26", new Point(110, 316));
		map.put("n27", new Point(110, 324));
		map.put("n28", new Point(110, 332));
		map.put("n29", new Point(110, 340));
		map.put("n30", new Point(110, 348));
		map.put("n31", new Point(110, 356));
		map.put("n32", new Point(122, 345));
		map.put("n33", new Point(134, 334));
		map.put("n34", new Point(146, 323));
		map.put("n35", new Point(158, 312));
		map.put("n36", new Point(170, 301));
		map.put("n37", new Point(182, 290));
		map.put("n38", new Point(194, 279));
		map.put("n39", new Point(206, 268));
		map.put("n40", new Point(218, 257));
		map.put("n41", new Point(118, 356));
		map.put("n42", new Point(126, 356));
		map.put("n43", new Point(134, 356));
		map.put("n44", new Point(142, 356));
		map.put("n45", new Point(150, 356));
		map.put("n46", new Point(158, 356));
		map.put("n47", new Point(166, 356));
		map.put("n48", new Point(174, 356));
		map.put("n49", new Point(182, 356));
		map.put("n50", new Point(190, 356));
		map.put("n51", new Point(198, 356));
		map.put("n52", new Point(206, 356));
		map.put("n53", new Point(214, 356));
		map.put("n54", new Point(222, 356));
		map.put("n55", new Point(230, 356));
		map.put("n56", new Point(238, 356));
		map.put("n57", new Point(246, 356));
		map.put("n58", new Point(254, 356));
		map.put("n59", new Point(262, 356));
		map.put("n60", new Point(271, 364));
		map.put("n61", new Point(272, 372));
		map.put("n62", new Point(273, 380));
		map.put("n63", new Point(274, 388));
		map.put("n64", new Point(275, 396));
		map.put("n65", new Point(276, 404));
		map.put("n66", new Point(277, 412));
		map.put("n67", new Point(278, 420));
		map.put("n68", new Point(279, 428));
		map.put("n69", new Point(284, 438));
		map.put("n70", new Point(110, 364));
		map.put("n71", new Point(110, 372));
		map.put("n72", new Point(110, 380));
		map.put("n73", new Point(110, 388));
		map.put("n74", new Point(110, 396));
		map.put("n75", new Point(110, 404));
		map.put("n76", new Point(110, 412));
		map.put("n77", new Point(110, 420));
		map.put("n78", new Point(110, 428));
		map.put("n79", new Point(110, 436));
		map.put("n80", new Point(118, 432));
		map.put("n81", new Point(126, 428));
		map.put("n82", new Point(134, 424));
		map.put("n83", new Point(142, 420));
		map.put("n84", new Point(150, 416));
		map.put("n85", new Point(158, 412));
		map.put("n86", new Point(166, 408));
		map.put("n87", new Point(174, 404));
		map.put("n88", new Point(182, 400));
		map.put("n89", new Point(190, 396));
		map.put("n90", new Point(198, 392));
		map.put("n91", new Point(206, 388));
		map.put("n92", new Point(214, 384));
		map.put("n93", new Point(222, 380));
		map.put("n94", new Point(230, 376));
		map.put("n95", new Point(238, 372));
		map.put("n96", new Point(246, 368));
		map.put("n97", new Point(254, 364));
		map.put("n98", new Point(262, 360));
		map.put("n99", new Point(270, 356));
		map.put("n100", new Point(125, 433));
		map.put("n101", new Point(140, 430));
		map.put("n102", new Point(155, 427));
		map.put("n103", new Point(170, 424));
		map.put("n104", new Point(185, 421));
		map.put("n105", new Point(200, 418));
		map.put("n106", new Point(200, 416));
		map.put("n107", new Point(207, 410));
		map.put("n108", new Point(214, 404));
		map.put("n109", new Point(221, 398));
		map.put("n110", new Point(228, 392));
		map.put("n111", new Point(235, 386));
		map.put("n112", new Point(242, 380));
		map.put("n113", new Point(249, 374));
		map.put("n114", new Point(256, 368));
		map.put("n115", new Point(263, 362));
		map.put("n116", new Point(270, 356));
		map.put("n117", new Point(208, 418));
		map.put("n118", new Point(216, 420));
		map.put("n119", new Point(224, 422));
		map.put("n120", new Point(232, 424));
		map.put("n121", new Point(240, 426));
		map.put("n122", new Point(248, 428));
		map.put("n123", new Point(256, 430));
		map.put("n124", new Point(264, 432));
		map.put("n125", new Point(272, 434));
		map.put("n126", new Point(280, 436));
		map.put("n127", new Point(290, 245));
		map.put("n128", new Point(287, 256));
		map.put("n129", new Point(284, 267));
		map.put("n130", new Point(281, 278));
		map.put("n131", new Point(278, 289));
		map.put("n132", new Point(275, 300));
		map.put("n133", new Point(272, 311));
		map.put("n134", new Point(269, 322));
		map.put("n135", new Point(266, 333));
		map.put("n136", new Point(263, 344));
		map.put("n137", new Point(260, 355));
		map.put("n138", new Point(293, 240));
		map.put("n139", new Point(286, 240));
		map.put("n140", new Point(279, 240));
		map.put("n141", new Point(272, 240));
		map.put("n142", new Point(265, 240));
		map.put("n143", new Point(258, 240));
		map.put("n144", new Point(251, 240));
		map.put("n145", new Point(244, 240));
		map.put("n146", new Point(237, 240));
		map.put("n147", new Point(230, 240));
		map.put("n148", new Point(258, 180));
		map.put("n149", new Point(258, 196));
		map.put("n150", new Point(260, 212));
		map.put("n151", new Point(260, 228));
		map.put("n152", new Point(260, 244));
		map.put("n153", new Point(260, 260));
		map.put("n154", new Point(260, 276));
		map.put("n155", new Point(260, 292));
		map.put("n156", new Point(260, 308));
		map.put("n157", new Point(260, 324));
		map.put("n158", new Point(260, 340));
		map.put("n159", new Point(260, 356));
		map.put("n160", new Point(256, 176));
		map.put("n161", new Point(254, 172));
		map.put("n162", new Point(252, 168));
		map.put("n163", new Point(250, 164));
		map.put("n164", new Point(248, 160));
		map.put("n165", new Point(246, 156));
		map.put("n166", new Point(244, 152));
		map.put("n167", new Point(242, 148));
		map.put("n168", new Point(240, 144));
		map.put("n169", new Point(236, 144));
		map.put("n170", new Point(232, 144));
		map.put("n171", new Point(228, 144));
		map.put("n172", new Point(224, 144));
		map.put("n173", new Point(220, 144));
		map.put("n174", new Point(216, 144));
		map.put("n175", new Point(212, 144));
		map.put("n176", new Point(208, 144));
		map.put("n177", new Point(204, 144));
		map.put("n178", new Point(200, 144));
		
		map.put("nIceCream", new Point(200, 240));
		map.put("nIceCream1", new Point(208, 240));
		map.put("nIceCream2", new Point(215, 240));
		map.put("nIceCream3", new Point(223, 240));
		map.put("nCoffee", new Point(152, 240));
		map.put("nCoffee1", new Point(176, 240));
		map.put("nCoffee2", new Point(188, 240));
		map.put("nCoffee3", new Point(164, 240));
		
	}

}
