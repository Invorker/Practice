package classloader;
import java.net.URL;  
import java.net.URLClassLoader;  
  
public class ClassLoaderTest {  
  
    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
        try {  
            URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();  
            for (int i = 0; i < extURLs.length; i++) {  
                System.out.println(extURLs[i]);  
            }  
            URL[] systemURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();  
            for (int i = 0; i < systemURLs.length; i++) {  
                System.out.println(systemURLs[i]);  
            }  
            
            System.out.println(System.getProperty("java.class.path")); 
            
        } catch (Exception e) {  
            //бн  
        }  
    }  
}  