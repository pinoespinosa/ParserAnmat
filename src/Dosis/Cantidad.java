package Dosis;

import java.util.List;

import Parser.ParserAnmat;

public abstract class Cantidad extends GrammarElement{

	public abstract boolean parse(List<String> string);
	public abstract GrammarElement getMatchingElement(List<String> string);


/**
 * Consume magnitudes de peso o volumen. Ej: ML, MG, L, G
 * @param file
 * @return
 */
	public static boolean consumir_MAGNITUD(List<String> file){

		String valor;
		
		if (!file.isEmpty()){
			valor = file.get(0);

			if (  ParserAnmat.tablaEquivalenciasToGramos.containsKey(valor) || ParserAnmat.tablaEquivalenciasToMililitros.containsKey(valor) || ParserAnmat.tablaSinConversion.contains(valor) )
			{
						
				file.remove(0);
				return true;				
			}
			else
				return false;
		}
		else
			return false;																													

	}
	

	

		
	public abstract List<Object> getDescriptors(List<String> file, float cantidadPresentacion, String magnitudPresentacion, int unidades);
	
}
