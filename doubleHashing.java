package src;

import java.util.Random;

public class doubleHashing {
    static Htdouble[] table;
    static int size = 727;
    static int del = 50;
    static int search = 100;
    static int no = 1000;
    int fill;
    public static void main(String[] argv) {
        table = new Htdouble[size];
        doubleHashing map = new doubleHashing();

        int i = 0;
        while(i < size){
            table[i] = null;
            i++;
        }
        long st = System.nanoTime();
        map.put(4, 10);
        map.put(15, 20);
        map.put(26, 30);
        map.put(37, 40);
        map.put(114, 50);
        map.put(103, 60);
        map.put(59, 70);
        map.put(81, 80);
        map.put(224, 80);
        map.remove(15);
        map.remove(37);
        map.remove(26);
        map.remove(224);
        long end = System.nanoTime();
        //System.out.println(end - st);
        map.print();
        /*Random rand = new Random();
        long st = System.nanoTime();
        for(int k = 0; k < size; k++){
            //System.out.println("map.put(" + rand.nextInt(no) + "," + k * 10+ ")");
            map.put(rand.nextInt(no) , k * 10);
        }
        long end = System.nanoTime();
        //System.out.println("iNSERTIONS: " + (end - st));
        st = System.nanoTime();
        for(int k = 0; k < del; k++){
            //System.out.println("map.remove(" + rand.nextInt(no) + ")");
            map.remove(rand.nextInt(no));
        }
        end = System.nanoTime();
        //System.out.println("Deletions: " + (end - st));
        st = System.nanoTime();
        for(int k = 0; k < search; k++){
            //System.out.println("map.get(" + rand.nextInt(no) + ")");
            map.get(rand.nextInt(no));
        }
        end = System.nanoTime();
        //System.out.println("Search: " + (end - st));*/
    }
    public void put(int key, int value){
        if(size == fill){
            //System.out.println("Load Factor 1 => Table Full");
            return;
        }
        int h1 = getHashValue(key, 0);
        int h2 = getHashValue(key, 1);
        while(table[h1] != null && table[h1].isdeleted == false && table[h1].getKey() != key){
            h1 = (h1 + h2) % size;
        }
        table[h1] = new Htdouble(key, value);
        table[h1].isdeleted = false;
        fill++;

    }

    public int get(int key){
        int h1 = getHashValue(key, 0);
        int h2 = getHashValue(key, 1);
        int count = 0;
        while(table[h1] != null && table[h1].getKey() != key && count < size){
            h1 = (h1 + h2) % size;
            count++;
        }
        if(table[h1] == null || count == size || table[h1].isdeleted){
            //System.out.println("No element Found");
            return -1;
        }
        else {
            //System.out.println(table[h1].getValue());
            return table[h1].getValue();
        }
    }

    public void remove(int key){
        int h1 = getHashValue(key, 0);
        int h2 = getHashValue(key, 1);
        int count = 0;
        while(table[h1] != null && table[h1].getKey() != key && count < size){
            h1 = (h1 + h2) % size;
            count++;
        }
        if(table[h1] == null) {
            //System.out.println("No element found");
            return;
        }
        table[h1].isdeleted = true;
        table[h1].setKey(-1);
        fill--;
    }

    public void print(){
        int i = 0;
        while(i < size){
            System.out.print("Cell " + i + ":");
            if(table[i] != null || table[i] != null && table[i].getKey() != -1)
                System.out.print(table[i].getKey());
            System.out.println();
            i++;
        }
    }

    public int getHashValue(int key, int table_id){
        if(table_id == 0){
            return key % size;
        }
        else if(table_id == 1){
            return (11 - (key % 11));
        }
        return -1;
    }
}


class Htdouble{
    private int key;
    private int value;
    boolean isdeleted;
    public  Htdouble(){

    }
    public Htdouble(int key, int value){
        this.key = key;
        this.value = value;
    }
    public int getKey(){
        return key;
    }
    public int getValue(){
        return value;
    }
    public void setKey(int k){ this.key = k; }
}