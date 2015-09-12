package Cantidad_Presentacion;

import java.util.ArrayList;
import java.util.List;

import Dosis.CantidadSimple;
import Dosis.Dosis;

public class PresentacionCantidadXUnidad extends Presentacion {


	// Parse elementos del tipo
	//								100 G / COMPRIMIDO
	
	
	public boolean parse(List<String> f) {
		
		List<String> file = new ArrayList<String>();
		file.addAll(f);
		
		Dosis.consumir_NOMBRE(file);
		
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
		
		return CantidadSimple.consumir(aux) && 	(	consumir_PALABRA_ESPECIFICA(aux, "X") || 
														consumir_PALABRA_ESPECIFICA(aux, "/")   )  && consumir_UNIDAD(aux)  && (aux.isEmpty());
	}


	public List<Object> getDescriptoresPresentacion(List<String> f) {

		List<String> file = new ArrayList<String>();
		file.addAll(f);
		
		Dosis.consumir_NOMBRE(file);
		
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
		
		aux.remove(aux.size()-1);
		aux.remove(aux.size()-1);
		
		
		List <Object> descriptores = CantidadSimple.getDescriptors(aux);
		descriptores.add(0);
		
		return descriptores;	}
}