package pt.uc.dei.nobugssnackbar;

import pt.uc.dei.nobugssnackbar.suporte.ObjetoMundoImpl;

/**
 * 
 * @author Adilson Vahldick 
 */
public interface ListenerMundo
{

    public abstract void disse(ObjetoMundoImpl objetomundoimpl, String s);

    public abstract void andou(ObjetoMundoImpl objetomundoimpl, Direcao direcao, int i, int j);

    public abstract void fimExecucao();

    public abstract void repintar();

    public abstract void limparConsole();
}