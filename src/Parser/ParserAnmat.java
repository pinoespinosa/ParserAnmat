package Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import Auxiliars.ExtendedHashMap;
import Auxiliars.SimpleFile;
import Auxiliars.ToolBox;
import Cantidad_Presentacion.Presentacion;
import Cantidad_Presentacion.PresentacionCantidadAndTexto;
import Cantidad_Presentacion.PresentacionCantidadXCantidadEnUnidades;
import Cantidad_Presentacion.PresentacionCantidadXNUnidades;
import Cantidad_Presentacion.PresentacionCantidadXUnidad;
import Cantidad_Presentacion.PresentacionSoloCantidad;
import Cantidad_Presentacion.PresentacionSoloUnidad;
import Cantidad_Presentacion.PresentacionUnidadesCantidadUnidades;
import Cantidad_Presentacion.PresentacionUnidadesXCantidad;
import Dosis.CantidadPorCantNormalizada;
import Dosis.CantidadPorUnidadMagnitud;
import Dosis.CantidadPorcentual;
import Dosis.CantidadRango;
import Dosis.CantidadSimple;
import Dosis.Dosis;
import Dosis.GrammarElement;
import Dosis.GrammarTool;


public class ParserAnmat {

	public static ExtendedHashMap <String,String> tablaPresentaciones = new ExtendedHashMap<String,String>();
	public static ExtendedHashMap <String,Double> tablaEquivalenciasToGramos = new ExtendedHashMap<String,Double>();
	public static ExtendedHashMap <String,Double> tablaEquivalenciasToMililitros = new ExtendedHashMap<String,Double>();
	public static List<String> tablaSinConversion = new ArrayList<String>();
	public static HashSet<String> list = new HashSet<String>();
	public static List<GrammarElement> modelosDosis = new ArrayList<GrammarElement>();
	public static List<GrammarElement> modelosPresentaciones= new ArrayList<GrammarElement>();
	public static CantidadSimple cantSimple = new CantidadSimple(); 
	public static List<String> listaUnidades;
	
	public static boolean debugTypes=false;
	
	public static Hashtable<String,String> listaNombresExpresionesEspecialesEnDrogas, listaNombresExpresionesEspecialesEnPresentaciones = new Hashtable<String, String>(), listaNombresExpresionesEspecialesEnMagnitudes;
	public static int aciertos=0, fallos =0,count=0;
	
	public static void initTablasEquivalencias(){
		
		
		// Cargo la lista de unidades
		listaUnidades=SimpleFile.readFile("", "Unidades.conf");
		listaNombresExpresionesEspecialesEnDrogas = SimpleFile.readHashTableFile("", "NombresExpresionesEspecialesEnDrogas.conf");
		listaNombresExpresionesEspecialesEnPresentaciones = SimpleFile.readHashTableFile("", "NombresExpresionesEspecialesEnPresentaciones.conf");
		
	
		
		
		// Inicializa la hash ToGramos
		tablaEquivalenciasToGramos.setDefault(((double) -1.0));
		tablaEquivalenciasToGramos.put("MG", ((double) 0.001));
		tablaEquivalenciasToGramos.put("G", ((double) 1.0));
		tablaEquivalenciasToGramos.put("GR.", ((double) 1.0));
		tablaEquivalenciasToGramos.put("GR", ((double) 1.0));
		tablaEquivalenciasToGramos.put("GRAMOS", ((double) 1.0));
		tablaEquivalenciasToGramos.put("null", ((double) 0.0));
		tablaEquivalenciasToGramos.put("MCG", ((double) 0.000001));
		
		// Inicializa la hash ToMililitros
		tablaEquivalenciasToMililitros.setDefault(((double) -1.0));
		tablaEquivalenciasToMililitros.put("L", ((double) 1000.0));
		tablaEquivalenciasToMililitros.put("LITRO", ((double) 1000.0));
		tablaEquivalenciasToMililitros.put("LITROS", ((double) 1000.0));
		tablaEquivalenciasToMililitros.put("M3", ((double) 1000000.0));
		tablaEquivalenciasToMililitros.put("ML", ((double) 1.0));
		tablaEquivalenciasToMililitros.put("MILILITROS", ((double) 1.0));
		tablaEquivalenciasToMililitros.put("CM3", ((double) 1.0));
		tablaEquivalenciasToMililitros.put("C.C", ((double) 1.0));
		tablaEquivalenciasToMililitros.put("C.C.", ((double) 1.0));
		
		tablaSinConversion.add("UI");
		tablaSinConversion.add("MCI");
		tablaSinConversion.add("UF");
		tablaSinConversion.add("MILL_GER");
		tablaSinConversion.add("MEQ");
	}
		public static void initModelosDeDatos(){
		
		modelosDosis.add(new Dosis());
		
		Dosis.modelosCantidad.add(new CantidadSimple());
		Dosis.modelosCantidad.add(new CantidadPorCantNormalizada());
		Dosis.modelosCantidad.add(new CantidadPorcentual());
		Dosis.modelosCantidad.add(new CantidadPorUnidadMagnitud());
		Dosis.modelosCantidad.add(new CantidadRango());
		
		modelosPresentaciones.add(new PresentacionSoloCantidad());
		modelosPresentaciones.add(new PresentacionUnidadesCantidadUnidades());
		modelosPresentaciones.add(new PresentacionSoloUnidad());
		modelosPresentaciones.add(new PresentacionCantidadXCantidadEnUnidades());
		modelosPresentaciones.add(new PresentacionCantidadXNUnidades());
		modelosPresentaciones.add(new PresentacionCantidadXUnidad());
		modelosPresentaciones.add(new PresentacionUnidadesXCantidad());
		modelosPresentaciones.add(new PresentacionCantidadAndTexto());


		

		
	}
	
	
	public static String Parsing( List<String> archivo){
	
		float a=0,b=0;
		
		String nroCertif, nombreMed, FormaFarm;
		List<String> listaPresentaciones, Drogas;

		nroCertif = getCertificado(archivo);

		int posicionCertificado=0;
		
		while (!archivo.isEmpty())
		{
			nombreMed = getNombre(archivo);
			FormaFarm = getFormaFarm(archivo);
			listaPresentaciones = getPresentacion(archivo);
			Drogas = getDrogas(archivo);

			
			if ( !esUnMedicamentoVacio(nroCertif, nombreMed, FormaFarm, listaPresentaciones) )	
			{	

				// Por cada presentacion, por cada drogas presente, genero un row.
				for (String pres : listaPresentaciones) {
					pres=ToolBox.reemplazarPalabraDeDiccionario(pres, listaNombresExpresionesEspecialesEnPresentaciones);
					Presentacion presentation = (Presentacion) GrammarTool.getMatchingElement(modelosPresentaciones,buildPresentation(pres));
										
					double dosisPresentacion=0;
					String magnitudPresentacion="null";
					int cantidadUnidades=0;
					if (presentation!=null)
					{
						if (debugTypes)
							System.out.println(presentation.getClass());
						List<Object> prov = presentation.getDescriptoresPresentacion(buildPresentation(pres));
						dosisPresentacion=(Double) Double.valueOf(prov.get(0).toString());
						magnitudPresentacion = (String) prov.get(1);
						cantidadUnidades = Integer.parseInt(prov.get(2).toString());
						
		
					}

					
					for (String dro : Drogas) {
						
						String droga = dro;
						
						// Reemplazo los nombres de drogas que incluyen nombres
						droga=ToolBox.reemplazarPalabraDeDiccionario(droga, listaNombresExpresionesEspecialesEnDrogas);					
						droga=ToolBox.quitarFrasesEntreParéntiesis(droga);
						
						// Desarmo la frase "droga", que indica la droga, la cant, y la magnitud y la parseo
						List<String> listaPalabraDroga = ToolBox.split(droga, " ");					
						String nombreDroga = Dosis.get_NOMBRE(listaPalabraDroga);
						
						
						Dosis docificacion = (Dosis) GrammarTool.getMatchingElement(modelosDosis,listaPalabraDroga);
						
						
						if (   docificacion!=null )
						{
							
							List<Object> datos = docificacion.getDescriptors(listaPalabraDroga, (float) dosisPresentacion, magnitudPresentacion,0);
							Double cantidadDroga = Double.parseDouble(datos.get(1).toString());
							String magnitudDroga =  (String) datos.get(2);
							
							if (magnitudDroga.equals("MILLONES") || magnitudDroga.equals("MILL"))
								magnitudDroga="MILL_GER";
							
							if (ParserAnmat.tablaSinConversion.contains(magnitudDroga) && debugTypes)
								System.out.println("SinConv");
							
							if (!magnitudDroga.equals("null"))
							{
								aciertos++;
								System.out.println(nroCertif + "	" + posicionCertificado + "	-1	" + nombreMed + "	" + FormaFarm+"	"+ dosisPresentacion +"	"+ magnitudPresentacion +"	"+ cantidadUnidades+ "	" + nombreDroga +"	"+ String.format("%.12f", cantidadDroga) +"	"+ magnitudDroga+"	"+ ToolBox.substring(pres, 250) + "	" + droga );
								
							}
							else
							{
								fallos++;
								System.out.println(nroCertif + "	" + posicionCertificado + "	-1	" + nombreMed +"	" + FormaFarm +"	"+ dosisPresentacion +"	"+ magnitudPresentacion +"	"+ cantidadUnidades+"	"+ nombreDroga +"	0	null	"+ ToolBox.substring(pres, 250) + "	" + droga );					
							}
						}
						else				
						{	
							fallos++;
							System.out.println(nroCertif + "	" + posicionCertificado + "	-1	" + nombreMed +"	" + FormaFarm +"	"+ dosisPresentacion +"	"+ magnitudPresentacion +"	"+ cantidadUnidades+"	"+ nombreDroga +"	0	null	"+ ToolBox.substring(pres, 250) + "	" + droga );					
							
						}
						count++;
					
						
						if (count==50)
						{
					//		count=0;
							
					//		a= aciertos;
					//		b =(aciertos+fallos);
					//		System.out.print(a/b) ;
							System.out.println(" --> " + aciertos + "_SI " + fallos + "_NO " + b);
						}
						
					}
					posicionCertificado++;
				}	
					


			}

		}
	
		a= aciertos;
		b =(aciertos+fallos);
		//		System.out.print(a/b) ;
		//		System.out.println(" --> " + aciertos + "_SI " + fallos + "_NO " + b);
		
		return "";
	}




	private static String getCertificado(List<String> archivo){

		String nroCertif = "";

		// Borro las lineas de la lista hasta hallar el certificado
		while (!archivo.isEmpty() && !archivo.get(0).contains(" - certificado: "))
			archivo.remove(0);

		// Corto el resto de la frase para retornar el numero
		if (!archivo.isEmpty())
			nroCertif=archivo.get(0).replace(" - certificado: ","");

		return nroCertif;
	}
	private static String getNombre(List<String> archivo){

		String nombreMed = "";

		// Borro las lineas de la lista hasta hallar el nombre
		while (!archivo.isEmpty() && !archivo.get(0).contains(")"))
			archivo.remove(0);

		// Borro las lineas de la lista hasta hallar el nombre
		while (archivo.size()>1 && !archivo.get(1).contains("Imprimir detalle de "))
			archivo.remove(0);

		// Corto el resto de la frase para retornar el numero
		if (archivo.size()>1)
			nombreMed=archivo.get(0).replaceAll("  \\(.*\\)","");
		
		// Saco todo lo que hay luego del parentesis		
		if ( nombreMed.indexOf(" (") >1) 	
			nombreMed=nombreMed.substring(0, nombreMed.indexOf(" ("));

		return nombreMed;
	}
	private static String getFormaFarm(List<String> archivo){

		String FormaFarm = "";

		// Borro las lineas de la lista hasta hallar la forma farmaceutica
		while (!archivo.isEmpty() && !archivo.get(0).contains("Forma Farmacéutica:"))
			archivo.remove(0);

		// Corto el resto de la frase para retornar el numero
		if (!archivo.isEmpty())
			FormaFarm= archivo.get(0).replaceAll(" \\[[1,2,3,4,5]\\]","").replaceAll(" \\([1,2,3,4,5]\\)","").replaceAll("Forma Farmacéutica: ","");
		
		return FormaFarm;
	}
	private static List<String> getPresentacion(List<String> archivo){

		List<String> presentacion = new ArrayList<String>();
		
		// Borro las lineas de la lista hasta hallar la presentacion
		while (!archivo.isEmpty() && !archivo.get(0).contains("Presentaciones"))
			archivo.remove(0);

		// Quito la linea de presentacion porque el dato se halla en la siguiente
		if (!archivo.isEmpty())
			archivo.remove(0);

		while (!archivo.isEmpty() && !archivo.get(0).contains("Composiciones") && !archivo.get(0).equals(""))
		{
			// Corto el resto de la frase para retornar la presentacion sin los espacios
			String aux = archivo.get(0).replaceFirst("  ", "");
								
			if(!aux.contains("Medicamento Trazable"))
				presentacion.add(aux);
			archivo.remove(0);
		}


		return presentacion;
	}	
	private static List<String> getDrogas(List<String> archivo){

		List<String> Drogas = new ArrayList<String>();

		// Borro las lineas de la lista hasta hallar las composiciones
		while (!archivo.isEmpty() && !archivo.get(0).contains("Composiciones") && !archivo.get(0).equals(""))
			archivo.remove(0);

		// Quito la linea de presentacion porque el dato se halla en la siguiente
		if (!archivo.isEmpty())
			archivo.remove(0);

		while (!archivo.isEmpty() && archivo.get(0).contains("-  ") )
		{
			// Corto el resto de la frase para retornar la droga sin la viñeta
			String aux = archivo.get(0).replaceFirst("-  ", "");
				
			Drogas.add(aux);
			archivo.remove(0);
		}
		
		


		return Drogas;
	}	
	private static boolean esUnMedicamentoVacio(String cert, String nomb, String forma, List<String> present){
		return !(!cert.equals("") && !nomb.equals("") &&  !forma.equals("")  && !present.isEmpty());
	}

	/**
	 * Algunos nombres de las presentaciones son casos especiales que contienen
	 * numeros o elementos dificiles de generalizar por lo que se construyo una
	 * lista con los casos especiales, los cuales son suplantados por otros con
	 * la misma información pero escrito de forma distinta
	 * 
	 * @param presentacion
	 * @return
	 */
	private static List<String> buildPresentation(String presentacion) {
		presentacion = ToolBox.reemplazarPalabraDeDiccionario(presentacion,	listaNombresExpresionesEspecialesEnPresentaciones);
		presentacion = ToolBox.quitarFrasesEntreParéntiesis(presentacion);
		return ToolBox.split(presentacion, " ");
	}



}
