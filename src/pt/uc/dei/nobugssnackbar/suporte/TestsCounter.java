package pt.uc.dei.nobugssnackbar.suporte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class TestsCounter extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image img;

	private int maxRuns;

	private int curRun;

	public TestsCounter(int maxRuns) throws Exception {
		super();
		this.img = LoadImage.getInstance().getImage("imagens/tests.png");
		this.setPreferredSize(new Dimension(32, 32));
		this.maxRuns = maxRuns;
		this.curRun = 0;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (maxRuns > 0) {
			g.setColor(Color.black);
			g.drawImage(img, 0, 0, null);
			g.drawString(maxRuns+"", 15, 20);
			
			if (this.curRun > 0) {
				g.setColor(Color.red);
				g.fillRect(22, 24, 36, 16);
				
				g.setColor(Color.white);
				g.drawString(curRun + "", 24, 35);
			}
		}
	}
	
	public void addCurRun() {
		this.curRun++;
		repaint();
	}
	
	public void resetCurRun() {
		this.curRun = 0;
		repaint();
	}
}
