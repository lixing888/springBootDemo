import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Copyright (C), 2018-2021
 * FileName: JSONObjectTest
 * Author:   ZSB
 * Date:     2021/6/25 15:38
 * Description: json解析
 */
public class JSONObjectTest {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\demo.json");
            String content = FileUtils.readFileToString(file);
            JSONObject obj1 = JSONObject.fromObject(content);
            Iterator iterator = obj1.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = obj1.getString(key);
                Object listArray = new JSONTokener(obj1.getString(key)).nextValue();
                if (listArray instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) listArray;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject everyParam = jsonArray.getJSONObject(i);
                        System.out.println(everyParam);
                    }
                } else if (listArray instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) listArray;
                    System.out.println(jsonObject);
                } else {
                    System.out.println(value);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
