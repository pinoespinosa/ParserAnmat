package Dosis;

import java.util.ArrayList;
import java.util.List;

public class CantidadPorcentual extends CantidadPorCantNormalizada{

	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}

	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		return consumir_NUMERO(aux) && (consumir_MAGNITUD(aux) || true) && 	( 	consumir_PALABRA_ESPECIFICA(aux, ".%") ||  
																				consumir_PALABRA_ESPECIFICA(aux, "%.") || 
																				consumir_PALABRA_ESPECIFICA(aux, ".")  || 
																				consumir_PALABRA_ESPECIFICA(aux, "%")   ) && (aux.isEmpty());
	}

	public List<Object> getDescriptors(List<String> file,float cantidadPresentacion, String magnitudPresentacion, int unidades) {

	
		
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);

		if (aux.get(aux.size()-1).equals(".%"))
			aux.remove(aux.size()-1);
		else		
		{
			if (aux.get(aux.size()-1).equals("%."))
				aux.remove(aux.size()-1);
			else
			{
				if ( (aux.get(aux.size()-1).equals(".") && aux.get(aux.size()-2).equals("%"))  ||  (aux.get(aux.size()-1).equals("%") && aux.get(aux.size()-2).equals(".")) )
				{
					aux.remove(aux.size()-1);
					aux.remove(aux.size()-2);
				}
				else
					if (aux.get(aux.size()-1).equals("%"))
						aux.remove(aux.size()-1);
			}
		}

		if ( GrammarTool.isNumeric(aux.get(aux.size()-1)) )
			aux.add(magnitudPresentacion);

		aux.add("/");
		aux.add("100");
		aux.add( magnitudPresentacion );
		
		return super.getDescriptors(aux, cantidadPresentacion, magnitudPresentacion, unidades);

	}

}
