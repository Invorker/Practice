package classloader;
public class AntiSingleton implements IAntiSingleton {  
    private static final AntiSingleton instance = new AntiSingleton();  
  
    public static AntiSingleton getInstance() {  
        return instance;  
    }  
}