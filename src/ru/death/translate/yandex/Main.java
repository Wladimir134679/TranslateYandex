package ru.death.translate.yandex;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author all
 */
public class Main extends Application{
    
    public static Main MAIN = null;
    
    public Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        MAIN = this;
        this.stage = primaryStage;
        
        stage.setTitle("Translate Yandex");
        stage.getIcons().add(new Image("res/icon.png"));
        stage.setResizable(false);
        
        initLoad();
    }
    
    public void initLoad(){
        try{
            stage.close();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("load/LoadApp.fxml"));
            Parent par = loader.load();
            Scene scene = new Scene(par);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch(IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initMain(){
        try{
            stage.close();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("mainScene/mainScene.fxml"));
            loader.load();
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch(IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getLanguageIn(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String lang = prefs.get("languageIn", null);
        return lang;
    }
    
    public static void setLanguageIn(String str){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.put("languageIn", str);
    }
    
    public static String getLanguageOut(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String lang = prefs.get("languageOut", null);
        return lang;
    }
    
    public static void setLanguageOut(String str){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.put("languageOut", str);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
