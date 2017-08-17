package Phecda.BasePlugin.Utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ObjectSerialization<T> {
	
	public ObjectSerialization() {}
	
	public String toBase64(T obj) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(os);
		
			boos.writeObject(obj);
			
			boos.close();
			return Base64Coder.encodeLines(os.toByteArray()).replaceAll(System.lineSeparator(), new String());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public T fromBase64(String data) {
		try {
            ByteArrayInputStream is = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream bois = new BukkitObjectInputStream(is);

			@SuppressWarnings("unchecked")
			T obj = (T) bois.readObject();
            
            bois.close();
            
            return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
