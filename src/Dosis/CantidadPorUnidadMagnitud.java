package Dosis;

import java.util.ArrayList;
import java.util.List;

import Cantidad_Presentacion.Presentacion;
import Parser.ParserAnmat;

public class CantidadPorUnidadMagnitud extends CantidadPorCantNormalizada{

	// Parse elementos del tipo
	//								100 G / COMPRIMIDO
	
	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}

	
	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		
		for (String string : file) {
			if (string.contains("/") && string.length()>1){
				String[] palabras=  string.split("/");
				for (String string2 : palabras) {
					aux.add(string2);
					aux.add("/");
				}
				aux.remove(aux.size()-1);
			}
			else
				aux.add(string);
		}
		
		return consumir_NUMERO(aux) && consumir_MAGNITUD(aux) && consumir_PALABRA_ESPECIFICA(aux, "/") && (	Presentacion.consumir_UNIDAD(aux) || 
																											consumir_MAGNITUD(aux)		) && (aux.isEmpty());
	}
																																																													
	public List<Object> getDescriptors(List<String> file,float cantidadPresentacion, String magnitudPresentacion, int unidades) {
		
		List<String> aux = new ArrayList<String>();
		
		for (String string : file) {
			if (string.contains("/") && string.length()>1){
				String[] palabras=  string.split("/");
				for (String string2 : palabras) {
					aux.add(string2);
					aux.add("/");
				}
				aux.remove(aux.size()-1);
			}
			else
				aux.add(string);
		}
		


		String magFinal = aux.get(aux.size()-1);
		
		if ( ParserAnmat.listaUnidades.contains(magFinal) )
		{
			aux.remove(aux.size()-1);
			aux.remove(aux.size()-1);
			//System.out.println(aux);
			return ParserAnmat.cantSimple.getDescriptors(aux, cantidadPresentacion, magnitudPresentacion, unidades);
		}
		else
		{
			aux.remove(aux.size()-1);		
			aux.add("1");
			aux.add(magFinal);
			return super.getDescriptors(aux, cantidadPresentacion, magnitudPresentacion, unidades);
		}
	}

}
