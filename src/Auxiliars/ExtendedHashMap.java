package Auxiliars;




import java.util.HashMap;

public class ExtendedHashMap<K,V> extends HashMap<K, V> {

	/**
	 *  Extiende la clase HashMap a�adiendo un valor de retorno por default
	 */
	
	
	private static final long serialVersionUID = 1L;
	private Object def = null;
	
	public void setDefault(Object d){
		def = d;
		
	}
	
	@SuppressWarnings("unchecked")
	public synchronized V get(Object arg0) {
		
		if (super.containsKey(arg0))
			return (V) super.get(arg0);
		else
			return (V) def;
	}
	
}
