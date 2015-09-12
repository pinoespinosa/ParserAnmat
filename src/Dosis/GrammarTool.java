package Dosis;

import java.util.List;

public class GrammarTool {

	public static boolean madeMatching( List<GrammarElement> lista_instancias, List<String> elemento ){
		return getMatchingElement(lista_instancias, elemento) != null;
		
	}
	
	public static GrammarElement getMatchingElement( List<GrammarElement> lista_instancias, List<String> elemento ){
	
		boolean resultado=false;
		GrammarElement aux=null;
		
		for (GrammarElement tipo : lista_instancias) {
			if (!resultado && tipo.parse(elemento))
			{
				resultado=true;
				aux=tipo;
			}
		}
		return aux;
		
	}	
	
	private static boolean isNumericInteger(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (Exception nfe){
			return false;
		}
	}
	private static boolean isNumericDouble(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (Exception nfe){
			return false;
		}
	}
	
	public static boolean isNumeric(String cadena){
		return isNumericDouble(cadena) || isNumericInteger(cadena);	
	}
	
	public static boolean isPalabra(String cadena){
		return !isNumeric(cadena);
	}
	



}
