package com.example.smart_box_app;

import android.app.Application;
import android.util.Log;

import com.example.lib.ListStatistics;
import com.example.lib.Statistics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MyApplication extends Application {
    public ListStatistics listStatistics;

    public static final String TAG = MyApplication.class.getName();
    public static final String MY_FILE_NAME = "statistics.json";
    static private Gson gson;
    static private File file;

    private File getFile(){
        if(file == null){
            File filesDir = getFilesDir();
            file = new File(filesDir, MY_FILE_NAME);
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    public static Gson getGson(){
        if(gson == null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }

    public void saveToFile(){
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(listStatistics));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readFromFile(){
        if(!getFile().exists())
            return false;
        try {
            listStatistics = getGson().fromJson(FileUtils.readFileToString(getFile()), ListStatistics.class);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        init();
    }

    private void init(){
        if(!readFromFile()){
            listStatistics = new ListStatistics();
        }
    }

    public void test(){
        for(int i = 0; i < 16; i++){
            if(i % 2 == 0)
                this.listStatistics.addStatistics(new Statistics("000" + i, new Date(), true));
            else
                this.listStatistics.addStatistics(new Statistics("000" + i, new Date(), false));
        }
    }

    public ListStatistics getListStatistics() {
        return listStatistics;
    }

    public void setListStatistics(ListStatistics listStatistics) {
        this.listStatistics = listStatistics;
    }

    public void addStatistics(Statistics statistics){
        this.listStatistics.addStatistics(statistics);
    }

    public Statistics getStatistics(int position){
        return this.listStatistics.getSpecificStatistics(position);
    }


}
