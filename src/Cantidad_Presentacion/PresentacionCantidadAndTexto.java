package Cantidad_Presentacion;

import java.util.ArrayList;
import java.util.List;

import Dosis.CantidadSimple;
import Dosis.Dosis;
import Dosis.GrammarElement;

public class PresentacionCantidadAndTexto extends Presentacion {

	
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
		
		Dosis.consumir_NOMBRE(aux);
			
		return CantidadSimple.consumir(aux) &&   Dosis.consumir_NOMBRE(aux) && (aux.isEmpty());
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
		
		List <Object> descriptores = CantidadSimple.getDescriptors(aux);
		descriptores.add(0);
		
		return descriptores;	}
	
	
	
	
}
