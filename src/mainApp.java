import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import Parser.ParserAnmat;

public class mainApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainApp window = new mainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnParsear = new JButton("Parsear");
		btnParsear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {

				List<String> code= new ArrayList<String>();
				String line = null;

				ParserAnmat.tablaPresentaciones.setDefault("8");
				
				
				
//				ParserAnmat.tablaEquivalenciasToGramos.put("UI", ((double) 0.0));
//				ParserAnmat.tablaEquivalenciasToGramos.put("MILL", ((double) 0.0));
//				ParserAnmat.tablaEquivalenciasToGramos.put("MILLONES", ((double) 0.0));
						
				ParserAnmat.initTablasEquivalencias();
				ParserAnmat.initModelosDeDatos();				
				
				List<Integer> listaArchivosOmitidos = new ArrayList<Integer>();
/*				listaArchivosOmitidos.add(2626); // MOLIBDATO DE SODIO  250 A 2000 MCI _ 1.0 U
				listaArchivosOmitidos.add(4854); // FLUORDEOXIGLUCOSA (18F)  20 A 100 MCI _ 1.0 U
				listaArchivosOmitidos.add(5092); // PALMITATO DE VITAMINA A  500000 UI/100 G _ 100.0 ML
				listaArchivosOmitidos.add(16543);
				listaArchivosOmitidos.add(33478);
				listaArchivosOmitidos.add(33403);
				listaArchivosOmitidos.add(40987);
				listaArchivosOmitidos.add(41301);
				listaArchivosOmitidos.add(44130);
				listaArchivosOmitidos.add(48152);
				listaArchivosOmitidos.add(53771);
	*/		
				
				
				
				// Archivos inparseables por falta de datos
				listaArchivosOmitidos.add(1014);
				listaArchivosOmitidos.add(2640);
				listaArchivosOmitidos.add(3559);
				listaArchivosOmitidos.add(3572);
				listaArchivosOmitidos.add(3574);
				listaArchivosOmitidos.add(3575);
				listaArchivosOmitidos.add(5092);
				listaArchivosOmitidos.add(3576);
				listaArchivosOmitidos.add(33478);
				listaArchivosOmitidos.add(40987);
				listaArchivosOmitidos.add(41301);			
				listaArchivosOmitidos.add(44130);

			
				
				List<Integer> listaArchivosEspeciales = new ArrayList<Integer>();
				listaArchivosEspeciales.add(4972);
				//listaArchivosEspeciales.add(49247);
				/*	listaArchivosEspeciales.add(31021);
				listaArchivosEspeciales.add(29552);
				listaArchivosEspeciales.add(30795);
				listaArchivosEspeciales.add(26765);
				listaArchivosEspeciales.add(32178);
				listaArchivosEspeciales.add(16565);
				listaArchivosEspeciales.add(23131);
				listaArchivosEspeciales.add(33441);
				listaArchivosEspeciales.add(19965);
				listaArchivosEspeciales.add(32180);
				listaArchivosEspeciales.add(31967);
			*/
				
				
				
				
				//85292
				
				//36555
				
				int i=41823;
			//	for (int i = 0; i < 85292 ; i++) 
				{
					if (!listaArchivosOmitidos.contains(i))
					//if (listaArchivosEspeciales.contains(i))
					try
					{	

						// Lee el archivo
						code.clear();
						BufferedReader reader = new BufferedReader(new FileReader("C:/Registros/Pasadosenlimpio/"+i+"archivoAlineado.txt" ));
						while ((line = reader.readLine()) != null)
							code.add(line);
						
						
						
						// Parseo el archivo			
						ParserAnmat.Parsing(code);
						
					}
					catch(Exception e){
						e.printStackTrace();
						}
				}	
			}
		});
		frame.getContentPane().add(btnParsear, BorderLayout.CENTER);
	}

}
