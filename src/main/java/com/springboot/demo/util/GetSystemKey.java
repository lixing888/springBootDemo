package com.springboot.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author lixing
 */
public class GetSystemKey {

    public static void main(String[] args) {

        File sysFile = new File("d:/sysFile", "sysFile.properties");
        Properties sysPro = System.getProperties();
        try {
            sysPro.store(new FileOutputStream(sysFile), "Properties ClASS");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
