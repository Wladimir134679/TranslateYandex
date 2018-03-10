package ru.death.translate.yandex.mainScene;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ru.death.translate.yandex.Main;
import ru.death.translate.yandex.MyHttpClient;

/**
 * @author Death
 */
public class MainSceneController implements Initializable{

    @FXML
    public CheckBox autoTranslate;
    
    @FXML
    public TextArea textIn, textOut;
    
    @FXML
    public ComboBox<String> langIn, langOut;
    
    @FXML
    public Button translate;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        MyHttpClient.LANGUAGES.forEach((t) -> {
            langIn.getItems().add(t);
            langOut.getItems().add(t);
        });
        langIn.setValue(Main.getLanguageIn());
        langOut.setValue(Main.getLanguageOut());
        
        translate.setOnMouseClicked((MouseEvent event) -> {
            translate();
        });
    }
    
    private KeyEvent backKey = null;
    public void enterCtrl(KeyEvent event){
        if(backKey == null){
            backKey = event;
            return;
        }
        if((event.isControlDown() && backKey.getCode() == KeyCode.ENTER) ||
           (event.getCode() == KeyCode.CONTROL && backKey.isControlDown())){
            translate();
        }
        backKey = event;
    }
    
    public void autoTranslate(){
        if(autoTranslate.isSelected()){
            if(textIn.getText().length() > 0)
                translate();
        }
    }
    
    public void markLang(){
        String lang = langIn.getValue();
        langIn.setValue(langOut.getValue());
        langOut.setValue(lang);
        
        String text = textIn.getText();
        textIn.setText(textOut.getText());
        textOut.setText(text);
    }
    
    private void translate(){
        if(langIn.getValue() == null){
            showInfaNullLang(1);
            return;
        }
        if(langOut.getValue() == null){
            showInfaNullLang(2);
            return;
        }
        Main.setLanguageIn(langIn.getValue());
        Main.setLanguageOut(langOut.getValue());
        String strIn = textIn.getText();
        String in = langIn.getValue().split("/")[1];
        String out = langOut.getValue().split("/")[1];
        String strOut = MyHttpClient.getTransfer(in, out, strIn);
        textOut.setText(strOut);
    }
    
    private void showInfaNullLang(int o){
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(Main.MAIN.stage);
        alert.setTitle("Не выбран язык");
        alert.setHeaderText("Не выбран язык для перевода");
        String infa = "";
        if(o == 1){
            infa = "Выберите язык самого текста";
        }else if(o == 2){
            infa = "Выберите язык на который перевести";
        }
        alert.setContentText(infa);

        alert.showAndWait();
    }

}
