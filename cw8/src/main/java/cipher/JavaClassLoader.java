package cipher;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JavaClassLoader extends SecureClassLoader {
    public List<Method> getMethods() {
        return methods;
    }

    public static List<Method> methods = new ArrayList<>();


    public static List<Class> getSortClasses(String pathToJar) throws IOException, ClassNotFoundException {
        List<Class> sortClasses = new ArrayList<>();
        JarFile jarFile = new JarFile(pathToJar, true);
        Enumeration<JarEntry> e = jarFile.entries();
        while(e.hasMoreElements()){
            JarEntry entry = e.nextElement();
            try {
                jarFile.getInputStream(entry);
            } catch(SecurityException s){
                System.err.println("\n"+ s.getMessage());
                return null;
            }
        }

        Manifest manifest = jarFile.getManifest();
        Map<String, Attributes> map = manifest.getEntries();
        boolean signed = false;
        for(Map.Entry<String, Attributes> entry : map.entrySet()){
            String name = entry.getValue().entrySet().toString();
            if(name.contains("-Digest")){
                signed = true;
            }
        }
        if(!signed) {
            System.err.println("\nPlik JAR jest niepodpisany!");
        }

        e = jarFile.entries();
        URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class c = null;
            try {
                c = cl.loadClass(className);
            } catch (SecurityException s){
                System.out.println("\n" + s.getMessage());
                continue;
            }

            if (c.getSuperclass() != null) {
                String superClassName = getClassName(c.getSuperclass().getName());
                if (superClassName.equals("AbstractSorter")) {
                    sortClasses.add(cl.loadClass(className));
                }
            }
        }
        return sortClasses;
    }

    public static String getClassName(String fullClassName) {
        String[] parts;
        parts = fullClassName.split("\\.");
        return parts[parts.length - 1];
    }

    public void loadAlgorithmClasses(String path) throws IOException, ClassNotFoundException, NoSuchMethodException, InterruptedException {
        List<Class> sortClasses;
        sortClasses = JavaClassLoader.getSortClasses(path);
        if(sortClasses==null){
            return;
        }
        loadMethods(sortClasses);
    }

    public static void loadMethods(List<Class> sortClasses) throws NoSuchMethodException {
        for (Class cl : sortClasses) {
            methods.add(cl.getMethod("solve", List.class));
        }
    }
}
