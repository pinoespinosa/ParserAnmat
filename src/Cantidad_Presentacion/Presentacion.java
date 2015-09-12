package Cantidad_Presentacion;

import java.util.List;

import Dosis.GrammarElement;
import Parser.ParserAnmat;

public abstract class Presentacion extends GrammarElement{

	public abstract List<Object> getDescriptoresPresentacion(List<String> f); 
	
	public static boolean consumir_UNIDAD(List<String> file){

		String valor;
		
		if (!file.isEmpty()){
			valor = file.get(0);

			if (  ParserAnmat.listaUnidades.contains(valor) )
			{
				file.remove(0);
				return true;
				
				
			}
			else
				return false;
		}
		else
			return false;																													

	}
}
