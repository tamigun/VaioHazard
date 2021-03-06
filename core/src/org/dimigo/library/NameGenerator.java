package org.dimigo.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juwoong on 15. 11. 13..
 */
public class NameGenerator {
    List<String> list;
    private static NameGenerator ourInstance = new NameGenerator();

    public static NameGenerator getInstance() {
        return ourInstance;
    }

    private NameGenerator() {
        list = new ArrayList<String>();
        File file = new File("resources/conversation/objectname");

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            for(String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
                list.add(line);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        Random rand = new Random();
        String value = list.get(rand.nextInt(list.size()));
        System.out.println("selected : " + value);
        return value;
    }
}
