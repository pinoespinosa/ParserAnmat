package Cantidad_Presentacion;

import java.util.ArrayList;
import java.util.List;

import Dosis.CantidadSimple;
import Dosis.Dosis;

public class PresentacionUnidadesXCantidad extends Presentacion {

	
	// Parse elementos del tipo
	//								6 X 100 G
	//								5 / 100 ML

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
		
		return consumir_NUMERO(aux) && (	consumir_PALABRA_ESPECIFICA(aux, "X") || 
											consumir_PALABRA_ESPECIFICA(aux, "/")   )  && CantidadSimple.consumir(aux) && (aux.isEmpty());
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
		
		int unidades = Integer.parseInt(aux.get(aux.size()-4));
		
		aux.remove(aux.size()-4);
		aux.remove(aux.size()-3);
		
		List <Object> descriptores = CantidadSimple.getDescriptors(aux);
		descriptores.add(unidades);
		
		return descriptores;
	}
}