package kernel.frontend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * assets Class
 * Singleton and factory yay ^^
 */
public class AssetsLoader {

    private static AssetsLoader instance = new AssetsLoader();
    private static HashMap<String, Object> resources;

    static {
        resources = new HashMap<>();
    }

    /**
     * Private constructor, class - singleton.
     */
    private AssetsLoader() {
    }

    /**
     * Loads a resource.
     *
     * @param fileName name of file
     * @param type     type
     */
    public static Object load(String fileName, String type) {
        if (!resources.containsKey(fileName)) {
            ResourceFactory.createResource(type).load(fileName);
        }
        return resources.get(fileName);
    }

    /**
     * Factory pattern to load different things.
     */
    private static class ResourceFactory {
        /**
         * Resource into the hashmap
         */
        private static Load createResource(String type) {
            Load resource = null;
            switch (type) {
                case "image":
                    resource = instance.new LoadImage();
                    break;
                case "imageIcon":
                    resource = instance.new LoadIcon();
                    break;
                case "textFile":
                    resource = instance.new LoadText();
                    break;
            }
            return resource;
        }
    }

    /**
     * Interface for each type of file
     */
    private interface Load {
        void load(String id);
    }

    /**
     * Load Image.
     */
    private class LoadImage implements Load
    {

        @Override
        public void load(String id) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(id));
            } catch (IOException e) {
                AbstractView.displayError("Problem with image loading.");
            }
            resources.put(id, image);
        }
    }
    /**
     * Load Icon.
     */
    private class LoadIcon implements Load {

        @Override
        public void load(String id) {
            ImageIcon image;
            image = new ImageIcon(id);
            resources.put(id, image);
        }
    }

    /**
     * Loading Text.
     */
    private class LoadText implements Load {
        @Override
        public void load(String id) {
            File file;
            file = new File(id);
            resources.put(id, file);
        }
    }

    /**
     * Loading options.
     */
    public static void loadProperties() {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("src/assets/options.json");
        } catch (FileNotFoundException e) {
            AbstractView.displayError("There is no: 'src/assets/options.json' ");
            return;
        }
        Properties options = new Properties(System.getProperties());
        try {
            options.load(fileInputStream);
        } catch (IOException e) {
            AbstractView.displayError("Problem loading in the options file!");
            return;
        }
        System.setProperties(options);
    }
}