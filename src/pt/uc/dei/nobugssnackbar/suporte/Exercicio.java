package pt.uc.dei.nobugssnackbar.suporte;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Adilson Vahldick
 *
 */
public class Exercicio
{

	private String classExercicio;
	private String explanation;
	private String cooker;
	private int tests;
	private List<CustomerDefinition> customers = new ArrayList<CustomerDefinition>();
	private List<ObjectiveConf> objectives = new ArrayList<ObjectiveConf>();
	
    public void setClassExercicio(String classExercicio)
    {
         this.classExercicio = classExercicio;
    }

	public Exercicio(String nomeArquivo) {
		
		String[] pedacos = nomeArquivo.split("[.]");
		
		this.classExercicio = "";
		for (int x=0;x<pedacos.length-1;x++) {
			this.classExercicio += pedacos[x];
			if (x <pedacos.length-2)
				this.classExercicio += ".";
		}
		
	}
	
	 public void finalizar() {
		 
	 }
	 
	 public String getExplanation() {
		return explanation;
	}
	 
	 public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	 
	 public String getCooker() {
		return cooker;
	}
	 
	 public void setCooker(String cooker) {
		this.cooker = cooker;
	}
	 
	public int getTests() {
		return tests;
	}
	
	public void setTests(int tests) {
		this.tests = tests;
	}
	 
	public void addCustomer(CustomerDefinition customer) {
		this.customers.add(customer);
	}
	 
	public List<CustomerDefinition> getCustomers() {
		return customers;
	}
	
	public void addObjective(ObjectiveConf obj) {
		this.objectives.add(obj);
	}
	
	public List<ObjectiveConf> getObjectives() {
		return objectives;
	}
	 /*
    public void addRobo(ElementoExercicio robo)
    {
        addElemento(robo);
        if(contaRobo == 0)
            robo.setId("robo");
        else
            robo.setId((new StringBuilder("robo")).append(contaRobo++).toString());
        robo.setClazz(classExercicio);
        robo.setBloqueado(false);
    }

    public Mundo criarMundo() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        hashObjsMundo.clear();
        ArrayList<String> posUsadas = new ArrayList<String>();
        calcularQtdadeLinCol();
        Mundo m = new Mundo(qtdadeLin, qtdadeCol);
        m.setExplodir(explodir);
        m.setUsarLinhasNaGrade(usarLinhasNaGrade);
        m.setTamCell(getTamanhoCel());
        List<ElementoExercicio> elemsAUsar = new ArrayList<ElementoExercicio>();
        elemsAUsar.addAll(getElementos());
        List<GrupoObjetos> grupos = getGruposObjetos();
        if(grupos.size() > 0) {
            
        	addElementsOfGroups(elemsAUsar, grupos);
        }
        List<ElementoExercicio> novosElems = new ArrayList<ElementoExercicio>();
        for(ElementoExercicio elemExerc:elemsAUsar)
        {
            int qtosObjs = sorteio.nextInt(elemExerc.getQtdade());
            for(int x = 0; x < qtosObjs; x++)
                novosElems.add(elemExerc.clonar());

        }

        elemsAUsar.addAll(novosElems);
        adicionarObjetosMundo(false, false, m, posUsadas, elemsAUsar);
        adicionarObjetosMundo(true, false, m, posUsadas, elemsAUsar);
        adicionarObjetosMundo(false, true, m, posUsadas, elemsAUsar);
        adicionarObjetosMundo(true, true, m, posUsadas, elemsAUsar);
        return m;
    }

    private void addElementsOfGroups(List<ElementoExercicio> elemsAUsar, List<GrupoObjetos> grupos) {
    	GrupoObjetos g = ((GrupoObjetos)grupos.get(sorteio.nextInt(grupos.size())));
    	elemsAUsar.addAll(g.getElementos());
    	if (g.getGruposObjetos().size() > 0) {
    		addElementsOfGroups(elemsAUsar, g.getGruposObjetos());
    	}
	}


    private void adicionarObjetosMundo(boolean random, boolean dependentes, Mundo m, List<String> posUsadas, List<ElementoExercicio> elems)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        ArrayList<ElementoExercicio> elementosExtraidos = extrairElementosPosicao(random, dependentes, elems);
        for(ElementoExercicio elem:elementosExtraidos) {
        	m.addObjetoMundoImpl(criarObjetoMundoImpl(m, posUsadas, elem, elem.getClazz()));
        }

    }

    private ArrayList<ElementoExercicio> extrairElementosPosicao(boolean random, boolean dependentes, List<ElementoExercicio> elemsOrigem)
    {
        ArrayList<ElementoExercicio> elems = new ArrayList<ElementoExercicio>();
        for(ElementoExercicio elem:elemsOrigem)
        {
            if(elem.getRandom().isRandom() == random && elem.isDependente() == dependentes)
                elems.add(elem);
        }

        return elems;
    }

    private ObjetoMundoImpl criarObjetoMundoImpl(Mundo mundo, List<String> posUsadas, ElementoExercicio elemento, String clazz)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        ObjetoDoMundo objMundo = (ObjetoDoMundo)Class.forName(clazz).newInstance();
        ObjetoMundoImpl obj = objMundo.getObjetoMundoImpl();
        obj.setMundo(mundo);
        obj.setBloqueado(elemento.isBloqueado());
        int x = getX(mundo, elemento);
        int y = getY(mundo, elemento);
        if(elemento.getRandom().isRandom())
        {
            FurbotRandom random = elemento.getRandom();
            int limiteSupCol = random.getLimiteSupRandomX() + 1;
            int limiteSupLin = random.getLimiteSupRandomY() + 1;
            int limiteInfCol = random.getLimiteInfRandomX();
            int limiteInfLin = random.getLimiteInfRandomY();
            if(limiteSupCol == 0 || limiteSupCol > qtdadeCol)
                limiteSupCol = qtdadeCol;
            if(limiteSupLin == 0 || limiteSupLin > qtdadeLin)
                limiteSupLin = qtdadeLin;
            limiteSupLin -= limiteInfLin;
            limiteSupCol -= limiteInfCol;
            do
            {
                if(random.isRandomX())
                    x = sorteio.nextInt(limiteSupCol);
                else
                if(random.isRandomY())
                {
                    y = sorteio.nextInt(limiteSupLin);
                } else
                {
                    x = sorteio.nextInt(limiteSupCol);
                    y = sorteio.nextInt(limiteSupLin);
                }
                x += limiteInfCol;
                y += limiteInfLin;
                java.awt.Point p = new java.awt.Point();
                p.x = x;
                p.y = y;
                refazerPosicaoDependente(p, mundo, elemento);
                x = p.x;
                y = p.y;
            } while(posUsadas.indexOf((new StringBuilder(String.valueOf(x))).append("=").append(y).toString()) != -1);
        } else
        {
            java.awt.Point p = new java.awt.Point();
            p.x = x;
            p.y = y;
            refazerPosicaoDependente(p, mundo, elemento);
            x = p.x;
            y = p.y;
        }
        obj.setX(x);
        obj.setY(y);
        if(elemento.isUsarEnergia())
            obj.setMaxEnergia(elemento.getEnergia());
        hashObjsMundo.put(elemento.getId(), obj);
        elemento.outrasAtribuicoes(obj, objMundo);
        posUsadas.add((new StringBuilder(String.valueOf(x))).append("=").append(y).toString());
        return obj;
    }

    private void refazerPosicaoDependente(java.awt.Point p, Mundo mundo, ElementoExercicio elemento)
    {
        if(elemento.isDependente())
        {
            if(elemento.getIdDependeX() != null)
            {
                p.x = getObjetoMundoImpl(mundo, elemento.getIdDependeX()).getX() + elemento.getX();
                if(p.x > qtdadeCol - 1)
                    p.x = qtdadeCol - 2;
                else
                if(p.x < 0)
                    p.x = 0;
            }
            if(elemento.getIdDependeY() != null)
            {
                p.y = ((ObjetoMundoImpl)hashObjsMundo.get(elemento.getIdDependeY())).getY() + elemento.getY();
                if(p.y > qtdadeLin)
                    p.y = qtdadeLin - 2;
                else
                if(p.y < 0)
                    p.y = 0;
            }
        }
    }


    private ObjetoMundoImpl getObjetoMundoImpl(Mundo mundo, String id)
    {
        return (ObjetoMundoImpl)hashObjsMundo.get(id);
    }

 

	*/
}