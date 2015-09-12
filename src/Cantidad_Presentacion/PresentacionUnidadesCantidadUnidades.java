package Cantidad_Presentacion;

import java.util.ArrayList;
import java.util.List;

import Dosis.CantidadSimple;
import Dosis.Dosis;
import Dosis.GrammarElement;

public class PresentacionUnidadesCantidadUnidades extends Presentacion {
/**
 * Parser
 */
	
	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		
		// Si hay un caracter pegado a la barra lo separo
		for (String string : file) {
			if (string.contains("/") && string.length()>1){
				string=string.replace("/", " / ");
				String[] palabras=  string.split(" ");
				for (String string2 : palabras) {
					aux.add(string2);
				}
			}
			else
				aux.add(string);
		}
		
		
			
		return consumir_UNIDAD(aux) && consumir_NUMERO(aux) && (	consumir_PALABRA_ESPECIFICA(aux, "UNIDADES")||
																	consumir_PALABRA_ESPECIFICA(aux, "unidades")||
																	consumir_PALABRA_ESPECIFICA(aux, "unidad")||
																	consumir_PALABRA_ESPECIFICA(aux, "UNIDAD")||
																	consumir_PALABRA_ESPECIFICA(aux, "u")||
																	consumir_PALABRA_ESPECIFICA(aux, "U")				) && (aux.isEmpty());
	}
	

	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}
	
	public List<Object> getDescriptoresPresentacion(List<String> file) {
		
		List<String> aux = new ArrayList<String>();
		
		// Si hay un caracter pegado a la barra lo separo
		for (String string : file) {
			if (string.contains("/") && string.length()>1){
				string=string.replace("/", " / ");
				String[] palabras=  string.split(" ");
				for (String string2 : palabras) {
					aux.add(string2);
				}
			}
			else
				aux.add(string);
		}
		
		List<Object> aux2 = new ArrayList<Object>();
		aux2.add(0);
		aux2.add(aux.get(0));
		aux2.add(aux.get(1));		
		
		
		
		return aux2;	}
	
	
	
	
}
