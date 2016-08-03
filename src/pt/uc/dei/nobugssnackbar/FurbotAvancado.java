package pt.uc.dei.nobugssnackbar;

import java.util.List;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;

/**
 * 
 * @author Adilson Vahldick
 */
public abstract class FurbotAvancado extends Furbot {

	public FurbotAvancado() {
	}

	public boolean ehVazio(int x, int y) {
		return objetoMundoImpl.ehVazio(x, y);
	}

	public int getQtdadeLinMundo() {
		return objetoMundoImpl.getQtdadeLin();
	}

	public int getQtdadeColMundo() {
		return objetoMundoImpl.getQtdadeCol();
	}

	public void pararMundo() throws MundoException {
		objetoMundoImpl.pararMundo();
	}

	public <T extends ObjetoDoMundo> List<T> getListaObjetos(Class<T> clazz) {
		return this.objetoMundoImpl.getObjetos(clazz);
	}

	public void executar() throws Exception {
		inteligencia();
	}
}