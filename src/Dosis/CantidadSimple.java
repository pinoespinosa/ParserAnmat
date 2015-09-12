package Dosis;

import java.util.ArrayList;
import java.util.List;

import Parser.ParserAnmat;

public class CantidadSimple extends Cantidad{

	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}

	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		return consumir_NUMERO(aux) && consumir_MAGNITUD(aux) && (aux.isEmpty());
	}


	public static List<Object> getDescriptors(List<String> file) {
		
		
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		
		List<Object> descriptores = new ArrayList<Object>();
			
		double val = Float.parseFloat(aux.get(0));
		
		
		
		Cantidad.consumir_NUMERO(aux);
					
		String mag = aux.get(0);
		
		double multip = ParserAnmat.tablaEquivalenciasToGramos.get(mag);
		
		if (multip!=-1)
		{
			val = val * multip;
			descriptores.add( val );
			descriptores.add("G");
		}
		else
		{	
			multip = ParserAnmat.tablaEquivalenciasToMililitros.get(mag);
			if (multip != -1)
			{
				val = val * multip;
				descriptores.add( val );
				descriptores.add("ML");
			}
			else
			{
				descriptores.add( val );
				descriptores.add(mag);
				
				int i=0;
				while (i<1)
					i--;
			}
		}	
			
		
		
		return descriptores;
		
	}
	
	
	public List<Object> getDescriptors(List<String> file,float cantidadPresentacion, String magnitudPresentacion, int unidades) {		
		return this.getDescriptors(file);
		
	}

	public static boolean consumir(List<String> file){
		return consumir_NUMERO(file) && consumir_MAGNITUD(file);
	}
}
