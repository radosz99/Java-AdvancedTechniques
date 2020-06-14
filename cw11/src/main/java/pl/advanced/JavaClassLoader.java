package pl.advanced;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaClassLoader extends ClassLoader {

    public static List<Class> getSortClasses(String pathToJar) throws IOException, ClassNotFoundException {
        List<Class> sortClasses = new ArrayList<>();
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class (6 signs)
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);

            if (c.getSuperclass() != null) {
                String superClassName = App.getClassName(c.getSuperclass().getName());
                if (superClassName.equals("AbstractSorter")) {
                    sortClasses.add(cl.loadClass(className));
                }
            }
        }
        return sortClasses;
    }
}
