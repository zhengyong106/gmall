package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MyTest2 {
    @Test
    public void test1(){
        String basePath = "C:/Users/Adminstrator/PycharmProjects/untitled/data5";
        for(File firstPath1: new File(basePath).listFiles()){
            for(File file: firstPath1.listFiles()){
                Map<String, Object> mapObj;
                try {
                    String jsonString = FileUtils.readFileToString(file, "UTF-8");
                    mapObj = JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                task1(mapObj);
            }
        }
    }

    private void task1(Map<String, Object> mapObj) {
        mapObj.get("");
    }
}
