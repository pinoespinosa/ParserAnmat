package Dosis;

import java.util.List;

public abstract class GrammarElement {

	public abstract boolean parse(List<String> string);
	//public abstract GrammarElement getMatchingElement(List<String> string);
	
	public static boolean consumir_NUMERO (List<String> f){

		String valor;
		if (!f.isEmpty())
			valor = f.get(0);
		else
			return false;

		if ( GrammarTool.isNumeric(valor) ){
			f.remove(0);
			return true;}
		else
			return false;
	}
	
	public static boolean consumir_PALABRA_ESPECIFICA(List<String> f, String caracter){

		if (!f.isEmpty() && f.get(0).equals(caracter)){
			f.remove(0);
			return true;
		}
		else
			return false;

	}
	
	/**
	 * Consume de la lista file, todas los conjuntos de caracteres que no son
	 * numeros
	 * 
	 * @param file
	 * @return
	 */
	public static boolean consumir_NOMBRE(List<String> file){
				
		String valor;
		if (!file.isEmpty())
		 valor = file.get(0);
		else
			return false;
		
		while ( GrammarTool.isPalabra(valor) )
		{
			file.remove(0);
			if (!file.isEmpty())
				 valor = file.get(0);
			else
				return true;
		}
			
		return true;
	}
	

	/**
	 * Se da por sentado que la cantidad es la ultima estructura de una
	 * gramatica, es decir luego de que venga una cantidad si hace matching me
	 * como toda la oracion que queda, porque ademas solo hace matching si es
	 * vacia la lista luego de el parse
	 * 
	 * @param file
	 * @return
	 */
	public boolean consumir_CANTIDAD(List<String> file){
		
		if (GrammarTool.madeMatching( Dosis.modelosCantidad , file ))
		{
			file.clear();
			return true;
		}
		else
			return false;
	}
	public boolean consumir_FIN_ORACION(List<String> file){
		return file.isEmpty();
		
	}
}