/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.death.translate.yandex.load;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import ru.death.translate.yandex.Main;

/**
 * FXML Controller class
 *
 * @author all
 */
public class LoadAppController implements Initializable{

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        LoadThreadLanguage loader = new LoadThreadLanguage(Main.MAIN);
        loader.start();
    }
}
