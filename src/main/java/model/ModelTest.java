package model;

import org.apache.maven.model.Model;

import java.util.Properties;

public class ModelTest {
    public static void main(String[] args) {
        Model model = new Model();

        boolean b = model.equals("");
        System.out.println(b);

        System.exit(-1);
        model.setArtifactId("MOFWK_31");
        System.out.println("ArtifactId is " + model.getArtifactId());

        Properties properties = new Properties();

        properties.setProperty("version", "0.0.1-SNAPSHOT");

        String version = properties.getProperty("version");
        System.out.println("version is " + version);
    }
}
