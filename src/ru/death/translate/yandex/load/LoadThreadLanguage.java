package ru.death.translate.yandex.load;

import java.util.Arrays;
import javafx.application.Platform;
import ru.death.translate.yandex.Main;
import ru.death.translate.yandex.MyHttpClient;

/**
 * @author Death
 */
public class LoadThreadLanguage implements Runnable{

    private Thread thread;
    private Main main;

    public LoadThreadLanguage(Main main){
        thread = new Thread(this);
        this.main = main;
    }
    
    public void start(){
        thread.start();
    }

    @Override
    public void run(){
        MyHttpClient.init();
        MyHttpClient.loadingLanguage();
        String[] arr = new String[MyHttpClient.LANGUAGES.size()];
        Arrays.sort(MyHttpClient.LANGUAGES.toArray(arr));
        MyHttpClient.LANGUAGES.clear();
        for(String str : arr){
            MyHttpClient.LANGUAGES.add(str);
        }
        Platform.runLater(() -> {
            main.initMain();
        });
    }
}
