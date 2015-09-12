package Auxiliars;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ToolBox {

	public static List<String> split (String frase, String separador){

		List<String> lista = new ArrayList<String>();
		String[] array =frase.split(separador);

		for (String string : array) {
			string=string.replaceAll(" ","");
			if (!string.isEmpty())
				lista.add(string);
		}

		return lista;
	}

	public static String reemplazarPalabraDeDiccionario(String palabra, Hashtable<String,String> diccionarioEquivalencias ) 	
	{	
		// Reemplazo los nombres de presentaciones que incluyen nombres
		
		for (String clave : diccionarioEquivalencias.keySet()) {

			if (palabra.contains(clave))
				palabra=palabra.replace(clave, diccionarioEquivalencias.get(clave));

		}

		return palabra;
	}
	
	public static String substring (String s, int maxSize){
		
		if (maxSize<s.length() && maxSize>0)
			return s.substring(0, maxSize);
		else
			return s;
	}
	
	public static String quitarFrasesEntreParéntiesis(String aux){
		
		// Saco todo lo que se halla entre parentesis
		while ( aux.indexOf("(") >1) 	
			if (aux.indexOf(")") >1)
			{
				aux=aux.substring(0, aux.indexOf("(")) + aux.substring(aux.indexOf(")")+1, aux.length());
			//	System.out.println(aux);
			}
				else
				aux=aux.substring(0, aux.indexOf("("));
		
		return aux;
		
	}
	
	
	
	
	
}
