package com.kodilla.pacmanv2.pacmanBoard.statistic;

import java.io.*;
import java.util.*;


public class Ranking implements Serializable {


    private static HashMap<String, Integer> ranking = new HashMap<>();


    public String RankingTop10ToString() {

        TreeMap<String, Integer> sortedMap = sortMapByValue(ranking);
        int count = 0;

        StringBuilder rankingBuilder = new StringBuilder();


        for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
            count++;

            if (count <= 10) {
                   rankingBuilder.append("" + count + "    " + e.getKey() + " points:  " + e.getValue() + "\n");
            }
        }
        return rankingBuilder.toString();
    }

    private static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map) {
        Comparator<String> comparator = new RankingValueComparator(map);
        //TreeMap is a map sorted by its key.
        //The comparator is used to sort the TreeMap by key.
        TreeMap<String, Integer> result = new TreeMap<>(comparator);
        result.putAll(map);
        return result;
    }

    public HashMap<String, Integer> getRanking() {
        return ranking;
    }

    public void addToRanking(String userName) {

        if (ranking.containsKey(userName)) {
            ranking.put(userName, (ranking.get(userName) + 1));
        } else {
            ranking.put(userName, 1);
        }


    }

  /*  public void saveRanking() throws FileNotFoundException {
        //write to file : "fileone"
        ClassLoader classLoader = getClass().getClassLoader();
        OutputStream is =  new FileOutputStream(classLoader.getResourceAsStream("assets/text/ranking.txt"));
        try (ObjectOutputStream o = new ObjectOutputStream(is)) {

            o.writeObject(ranking);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }


    }
    */

    public void readRanking() {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("assets/text/ranking.txt");

        //read from file
        try (
                ObjectInputStream oi = new ObjectInputStream(is)) {
            Object readObject = oi.readObject();
            if (!(readObject instanceof HashMap)) {
                throw new IOException("Data is not a hashmap");


            } else ranking = (HashMap) readObject;


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing ranking - NEW RANKIGN IS CRETED");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
