package Dosis;
import java.util.ArrayList;
import java.util.List;


public class Dosis extends GrammarElement{

	public static List<GrammarElement> modelosCantidad = new ArrayList<GrammarElement>();

	
	public boolean parse(List<String> file){
					
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
		
		return (consumir_NOMBRE(aux) && consumir_CANTIDAD(aux) && (consumir_FIN_ORACION(aux)));
			
	};



	
	/**
	 * Retorna una lista con los elementos que describen una dosis bajo una
	 * forma estandarizada
	 * 
	 * @param file
	 * @param cantidadPresentacion
	 * @param magnitudPresentacion
	 * @param unidades
	 * @return
	 */
	public List<Object> getDescriptors(List<String> file, float cantidadPresentacion, String magnitudPresentacion, int unidades){
		
		List<Object> descriptores = new ArrayList<Object>();
				
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
		
		
		
		consumir_NOMBRE(aux);
		
		String fraseNombre="";
		
		int i=0;
		while (i<file.size() && !aux.get(0).equals(file.get(i))){
			if (!fraseNombre.equals(""))
				fraseNombre+=" "+file.get(i);
			else
				fraseNombre+=file.get(i);
			i++;}
		
		descriptores.add(fraseNombre);
		
		Cantidad aa = (Cantidad) GrammarTool.getMatchingElement( Dosis.modelosCantidad , aux );
		System.out.println(aa.getClass());	
		descriptores.addAll(aa.getDescriptors(aux, cantidadPresentacion, magnitudPresentacion,unidades));
		
		return descriptores;
	}
	
	public String getMagnitud(List<String> file){
		
		List<String> aux = new ArrayList<String>();
		aux.addAll(file);
		
		Cantidad auxil = (Cantidad) GrammarTool.getMatchingElement( Dosis.modelosCantidad , file );
		
		return null;		
	};
	
	public static String get_NOMBRE(List<String> file){
		
		List<String> nombDroga = new ArrayList<String>();
		List<String> nombDroga2 = new ArrayList<String>();
		
		nombDroga.addAll(file);
		nombDroga2.addAll(file);
		
		consumir_NOMBRE(nombDroga);
		
		nombDroga2.removeAll(nombDroga);
		
		String fraaase="";
		
		for (int i=0; i<nombDroga2.size(); i++)
			if (i<nombDroga2.size()-1)
				fraaase+=nombDroga2.get(i)+" ";
			else
				fraaase+=nombDroga2.get(i);
		
		if ( fraaase.indexOf(" (") >1) 	
			fraaase=fraaase.substring(0, fraaase.indexOf(" ("));
		
		
	return fraaase;}
	
	public float getCantidadGramos(){
		return 5;
		
	};
	
	
}
