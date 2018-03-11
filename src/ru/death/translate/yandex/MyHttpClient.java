package ru.death.translate.yandex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Death
 */
public class MyHttpClient {
    
    public static final String KEY_YANDEX = "trnsl.1.1.20180308T161127Z.5025b81bc7c1b279.2f81be5fc0011d7fa3f618f2d22ff9043d35f4fe";

    public static HttpClient httpClient = null;
    
    public static ArrayList<String> LANGUAGES = null;
    
    public static void init(){
        httpClient = HttpClientBuilder.create().build();
        
        LANGUAGES = new ArrayList<>();
    }
    
    public static void loadingLanguage(){
        String reply = null;

        StringBuilder request = new StringBuilder();
        request.append("https://translate.yandex.net/api/v1.5/tr.json/getLangs")
                .append("?key=").append(KEY_YANDEX)
                .append("&ui=").append("ru");

        HttpGet httpGet = new HttpGet(request.toString());
        HttpResponse response;
        try{
            response = httpClient.execute(httpGet);
            reply = new BasicResponseHandler().handleResponse(response);
        }
        catch(IOException ex){
            Logger.getLogger(MyHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(reply == null)
            return;
        JSONObject replyObj = new JSONObject(reply);
        JSONArray arrDirs = replyObj.getJSONArray("dirs");
        JSONObject langs = replyObj.getJSONObject("langs");

        Iterator<String> langs1 = langs.keys();
        while(langs1.hasNext()){
            String lang = langs1.next();
            String name = langs.getString(lang);
            
            String out = name + "/" + lang;
            LANGUAGES.add(out);
        }
    }
    
    public static String getTransfer(String in, String langOut, String text){
        JSONObject obj = new JSONObject(getReply(in, langOut, text));
        return obj.getJSONArray("text").getString(0);
    }
    
    public static String getReply(String lang, String lang1, String textOrig){
        String reply = null;
        String text = "";
        try{
            text = URLEncoder.encode(textOrig, "UTF-8");
        }
        catch(UnsupportedEncodingException ex){
            Logger.getLogger(MyHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
;

        StringBuilder request = new StringBuilder();
        request.append("https://translate.yandex.net/api/v1.5/tr.json/translate")
                .append("?key=").append(KEY_YANDEX)
                .append("&text=").append(text)
                .append("&lang=").append(lang).append("-").append(lang1)
                .append("&format=").append("plain");

//        System.out.println("Request = \"" + request.toString() + "\"");
        HttpGet httpGet = new HttpGet(request.toString());
        HttpResponse response;
        try{
            response = httpClient.execute(httpGet);
            reply = new BasicResponseHandler().handleResponse(response);
        }
        catch(IOException ex){
            Logger.getLogger(MyHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reply;
    }
}
