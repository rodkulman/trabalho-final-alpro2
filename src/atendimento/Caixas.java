package atendimento;

import meta.*;

/**
 * Representa uma coleção de caixas.
 * @author rodkulman@gmail.com
 *
 */
public abstract class Caixas
{
	protected ListLinked<Caixa> caixas;
	
	protected Caixas()
	{
		caixas = new ListLinked<>();
	}
	
	public boolean estaVazio()
	{
		for (Caixa c : caixas)
		{
			if (c.estaVazio()) { return true; }
		}
		
		return false;
	}
}
