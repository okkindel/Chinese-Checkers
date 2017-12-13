package core.model;

import core.view.AbstractView;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Assets {
    //this uses singleton pattern
    private static Assets instance = new Assets();
    //to be sure to not load twice one resource
    private static HashMap<String, Object> assets = new HashMap<>();

    private Assets() {
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    //if no asset in hash map - add it here
    public static Object load(String file, String assetType) {
        if (!assets.containsKey(file)) {
            AssetsFactory.createAsset(assetType).loadAsset(file);
        }
        return assets.get(file);
    }

    //interface for which of inner class for each type for files should use
    private interface Load {
        abstract void loadAsset(String resource);
    }

    //creates asset depending on the type
    private static class AssetsFactory {

        private static Load createAsset(String type) {
            Load asset = null;
            if (type.equals("image")) {
                asset = instance.new LoadImage();
            } else if (type.equals("imageIcon")) {
                asset = instance.new LoadIcon();
            } else if (type.equals("textFile")) {
                asset = instance.new LoadText();
            }
            return asset;
        }
    }

    private class LoadImage implements Load {
        @Override
        public void loadAsset(String resource) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(resource));
            } catch (IOException ex) {
                AbstractView.showError("Problem with loading an image.");
            }
            assets.put(resource, image);
        }
    }

    private class LoadIcon implements Load {
        @Override
        public void loadAsset(String resource) {
            ImageIcon icon;
            icon = new ImageIcon(resource);
            assets.put(resource, icon);
        }
    }

    private class LoadText implements Load {
        @Override
        public void loadAsset(String resource) {
            File text = null;
            text = new File(resource);
            assets.put(resource, text);
        }
    }
}
