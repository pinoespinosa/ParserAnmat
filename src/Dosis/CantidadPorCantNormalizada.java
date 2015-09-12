package Dosis;

import java.util.ArrayList;
import java.util.List;

import Parser.ParserAnmat;

public class CantidadPorCantNormalizada extends Cantidad{

	public GrammarElement getMatchingElement(List<String> string) {
		return null;
	}

	public boolean parse(List<String> file) {
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		return consumir_NUMERO(aux) && consumir_MAGNITUD(aux) && consumir_PALABRA_ESPECIFICA(aux , "/") && consumir_NUMERO(aux) && consumir_MAGNITUD(aux) && (aux.isEmpty());
	}

	public List<Object> getDescriptors(List<String> file, float cantidadPresentacion, String magPresentacion, int unidades) {


		List<String> aux = new ArrayList<String>();
		aux.addAll(file);

		List<Object> descriptores = new ArrayList<Object>();


		double cant, cantidadEnvase;
		String magCant, magEnvase;

		cant = Float.parseFloat(aux.get(0)); aux.remove(0);
		magCant = aux.get(0); aux.remove(0);aux.remove(0);
		cantidadEnvase = Float.parseFloat(aux.get(0)); aux.remove(0);
		magEnvase = aux.get(0);

		
		if (!magPresentacion.equals(magEnvase)){
				
			if(		ParserAnmat.tablaEquivalenciasToMililitros.containsKey(magEnvase) && 
					ParserAnmat.tablaEquivalenciasToMililitros.containsKey(magPresentacion) && 
					!magEnvase.equals("null") && 
					!magPresentacion.equals("null")	  )
			{
				cantidadEnvase=cantidadEnvase * ParserAnmat.tablaEquivalenciasToMililitros.get(magEnvase);
				cantidadPresentacion= cantidadPresentacion * Float.parseFloat(ParserAnmat.tablaEquivalenciasToMililitros.get(magPresentacion).toString());
				magEnvase="ML";
				magPresentacion="ML";				
			}	
			
			else
				
				if(		ParserAnmat.tablaEquivalenciasToGramos.containsKey(magEnvase) && 
						ParserAnmat.tablaEquivalenciasToGramos.containsKey(magPresentacion) && 
						!magEnvase.equals("null") && 
						!magPresentacion.equals("null")	  )
				{
					cantidadEnvase=cantidadEnvase * ParserAnmat.tablaEquivalenciasToGramos.get(magEnvase);
					cantidadPresentacion= cantidadPresentacion * Float.parseFloat(ParserAnmat.tablaEquivalenciasToGramos.get(magPresentacion).toString());
					magEnvase="G";
					magPresentacion="G";				
				}
		
		
		}
		
		
		if (magPresentacion.equals(magEnvase) && !magEnvase.equals("null")){
			cant=cant * (cantidadPresentacion/cantidadEnvase);

			double multip = ParserAnmat.tablaEquivalenciasToGramos.get(magCant);

			if (multip!=-1)
			{
				cant = cant * multip;
				descriptores.add( cant );
				descriptores.add("G");
			}
			else
			{	
				multip = ParserAnmat.tablaEquivalenciasToMililitros.get(magCant);
				if (multip != -1)
				{
					cant = cant * multip;
					descriptores.add( cant );
					descriptores.add("ML");
				}
				else
				{
					descriptores.add(cant);
					descriptores.add(magCant);

					int i=0;
					while (i<1)
						i--;
				}
			}	







		}		

		else 
		{
			
			
			
			descriptores.add( ((float)0) );
			descriptores.add("null");
		}

		//	Cantidad.consumir_NUMERO(aux);

		//	descriptores.add(aux.get(0));

		return descriptores;



	}



}
