package nobugsj;

import java.util.ArrayList;

import pt.uc.dei.nobugssnackbar.grafos.Grafo;
import pt.uc.dei.nobugssnackbar.grafos.Vertice;

public class TestGrafo {

	public static void main(String[] args) {
		
		Grafo g = new Grafo();
		g.addVertice("A");
		g.addVertice("B");
		g.addVertice("C");
		g.addVertice("D");
		
		g.addAresta(50, "A", "B");
		g.addAresta(0, "B", "A");
		g.addAresta(0, "A", "C");
		
		g.addAresta(0, "B", "C");
		g.addAresta(0, "B", "D");
		
		ArrayList<Vertice> vertices = g.encontrarMenorCaminhoDijkstra(g.acharVertice("D"), g.acharVertice("A"));
		System.out.println(vertices.size());
	}
	
}
