package pl.pwr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyClassLoader extends ClassLoader {

    private ArrayList<String> classNames = new ArrayList<>();
    private String path;

    public MyClassLoader(String dirPath) {
        path = dirPath;
        if (!path.endsWith("\\"))
            path = path + "\\";
    }

    public void searchInDirectory() {
        try {
            String finalDirPath = path;
            Files.walk(Paths.get(path)).filter(Files::isRegularFile).forEach((f) -> {
                String absolutePath = f.toAbsolutePath().toString();
                if (absolutePath.endsWith(".class")) {
                    classNames.add(absolutePath.replace(finalDirPath, "").replace("\\", ".").replace(".class","" ));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getClassNamesInDirectory()
    {
        return classNames;
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        Path classPath = Paths.get(path + name.replace('.', File.separatorChar) + ".class");
        if (Files.exists(classPath)) {
            try {
                byte[] byteCode = Files.readAllBytes(classPath);
                return this.defineClass(name, byteCode, 0, byteCode.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        } else {
            throw new ClassNotFoundException(name);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}


