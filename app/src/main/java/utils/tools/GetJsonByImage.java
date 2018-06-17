package utils.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class GetJsonByImage {
    public static String Json = null;    //Json值
    public static String oldJson = null;   //Json的上一次值

    /*
    一个APP如果在主线程中请求网络操作，将会抛出异常。Android这个设计是为了防止网络请求时间过长而导致界面假死的情况发生。
    解决方案有两个，一个是使用StrictMode，二是使用线程来操作网络请求。
    这里使用线程来操作网络请求。
     */

    public static HashMap<String,String> getJsonByFaceApi(final File file) throws InterruptedException, JSONException {     //开启一个线程，用来进行网络请求，并返回Json串，Json串为String格式

        new Thread(new Runnable(){
            @Override
            public void run() {
                Json = FaceExtract.GetJsonByFaceApi(file);   //这个函数调用比较耗时
            }
        }).start();

        while(Json == oldJson || Json == null){    //等待网络Json串返回，然后继续执行，保证Json不为空，同时还不能与上一次Json串一致
            Thread.sleep(500);
            //System.out.println("Json is null,waiting");
        }

        //此时Json != oldJson
        oldJson = Json;

        System.out.println("we get the response Json:" + Json);
        HashMap<String,String> resultMap = resolveJson();

        return resultMap;
    }

    public static HashMap<String,String> resolveJson() throws JSONException {    //解析Json
        HashMap<String,String> resultMap = new HashMap<>();
        JSONObject obj = new JSONObject(Json);
        JSONArray array = obj.getJSONArray("faces");
        for(int i = 0;i<array.length();++i){
            JSONObject faces = array.getJSONObject(i);   //索引值，获取数组中包含的值
            JSONObject attributes = faces.getJSONObject("attributes");

            JSONObject emotion = attributes.getJSONObject("emotion");
            String sadness = emotion.getString("sadness");
            String neutral = emotion.getString("neutral");
            String disgust = emotion.getString("disgust");
            String anger = emotion.getString("anger");
            String surprise = emotion.getString("surprise");
            String fear = emotion.getString("fear");
            String happiness = emotion.getString("happiness");
            //System.out.println("emotion:" + sadness + " " + neutral + " " + disgust+ " " + anger + " " + surprise+ " " + fear + " " + happiness);

            JSONObject gender = attributes.getJSONObject("gender");
            String gendervalue = gender.getString("value");
            //System.out.println("gender:" + gendervalue );

            JSONObject age = attributes.getJSONObject("age");
            String agevalue = age.getString("value");
            System.out.println("age:" + agevalue );

            JSONObject skinstatus = attributes.getJSONObject("skinstatus");
            String dark_circle = skinstatus.getString("dark_circle");
            String stain = skinstatus.getString("stain");
            String acne = skinstatus.getString("acne");
            String health = skinstatus.getString("health");
            System.out.println("skinstatus:" + health + " " + stain + " " + acne+ " " + dark_circle);

            JSONObject smile = attributes.getJSONObject("smile");
            String smilethreshold = smile.getString("threshold");
            String smilevalue= smile.getString("value");
            //System.out.println("smile:" + smilethreshold + " " + smilevalue);

            JSONObject ethnicity = attributes.getJSONObject("ethnicity");
            String ethnicityvalue = ethnicity.getString("value");
            //System.out.println("ethnicity:" + ethnicityvalue);

            resultMap.put("sadness",sadness);
            resultMap.put("neutral",neutral);
            resultMap.put("disgust",disgust);
            resultMap.put("anger",anger);
            resultMap.put("surprise",surprise);
            resultMap.put("fear",fear);
            resultMap.put("happiness",happiness);

            resultMap.put("gendervalue",gendervalue);

            resultMap.put("agevalue",agevalue);

            resultMap.put("dark_circle",dark_circle);
            resultMap.put("stain",stain);
            resultMap.put("acne",acne);
            resultMap.put("health",health);

            resultMap.put("smilethreshold",smilethreshold);
            resultMap.put("smilevalue",smilevalue);

            resultMap.put("ethnicityvalue",ethnicityvalue);
        }
        return resultMap;
    }


}
