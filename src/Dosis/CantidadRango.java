package Dosis;

import java.util.ArrayList;
import java.util.List;

import Parser.ParserAnmat;

public class CantidadRango extends Cantidad{

	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}

	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		consumir_NOMBRE(aux);
		return consumir_NUMERO(aux) && consumir_PALABRA_ESPECIFICA(aux, "A") && consumir_NUMERO(aux) && consumir_MAGNITUD(aux) && (aux.isEmpty());
	}


	public static List<Object> getDescriptors(List<String> file) {
		
		
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		
		List<Object> descriptores = new ArrayList<Object>();
		
		consumir_NOMBRE(aux);
		consumir_NUMERO(aux);
		consumir_PALABRA_ESPECIFICA(aux, "A");
		descriptores.add(aux.get(0));
		descriptores.add(aux.get(1));
		
			
		
		
		return descriptores;
		
	}
	
	
	public List<Object> getDescriptors(List<String> file,float cantidadPresentacion, String magnitudPresentacion, int unidades) {		
		return this.getDescriptors(file);
		
	}

	public static boolean consumir(List<String> file){
		return consumir_NUMERO(file) && consumir_MAGNITUD(file);
	}
}
