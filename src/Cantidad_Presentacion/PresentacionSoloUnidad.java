package Cantidad_Presentacion;

import java.util.ArrayList;
import java.util.List;

import Dosis.Dosis;
import Dosis.GrammarElement;

public class PresentacionSoloUnidad extends Presentacion {

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
				
		boolean a = Dosis.consumir_NOMBRE(aux);
			
		return consumir_NUMERO(aux) && consumir_UNIDAD(aux) && (aux.isEmpty());
	}
	
	
	public static boolean consumir(List<String> file){
		return consumir_NUMERO(file) && consumir_UNIDAD(file);
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
		
		Dosis.consumir_NOMBRE(aux);
		
		List <Object> descriptores = new ArrayList<Object>();
		descriptores.add(0);
		descriptores.add("null");
		descriptores.add(aux.get(0));
		
		return descriptores;	}
	
	
	
	
}
