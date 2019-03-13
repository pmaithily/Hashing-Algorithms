package src;

import java.util.Random;

public class cuckooHashing {
    static int size = 11;
    static int del = 50;
    static int search = 100;
    static int no = 1000;

    static hT[][] table;
    int[] position = new int[2];
    public static void main(String[] argv){
        table = new hT[2][size];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < size; j++){
                table[i][j] = null;
            }
        }
        cuckooHashing map = new cuckooHashing();
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

        Random rand = new Random();
        /*long st = System.nanoTime();
        for(int k = 0; k < size; k++){

            hash.put(rand.nextInt(no) , k * 10);
        }
        long end = System.nanoTime();
        System.out.println("insertions: " + (end - st));
        st = System.nanoTime();
        for(int k = 0; k < del; k++){

            hash.remove(rand.nextInt(no));
        }
        end = System.nanoTime();
        System.out.println("Deletions: " + (end - st));
        st = System.nanoTime();
        for(int k = 0; k < search; k++){

            hash.get(rand.nextInt(no));
        }
        end = System.nanoTime();
        System.out.println("Search: " + (end - st));*/

    }
    public int getHashValue(int key, int table_id){
        if(table_id == 0){
            return key % size;
        }
        else if(table_id == 1){
            return (key / size) % size;
        }
        return -1;
    }

    public int get(int key){
        int t = 0;
        int id = getHashValue(key, t );
        if(table[t][id] != null && table[t][id].getKey() != key){
            t = 1 - t;
            id = getHashValue(key, t);
            if(table[t][id] != null && table[t][id].getKey() != key) {
                //System.out.println("Key not found");
                return -1;
            }
            else if(table[t][id] != null && table[t][id].getKey() == key)
                return table[t][id].getValue();
        }
        else {
            if(table[t][id] != null)
                return table[t][id].getValue();
            else {
                //System.out.println("Key not found");
                return -1;
            }
        }
        return -1;
    }
    public void put(int key, int value){
        insertHelper(key, value, 0, 0, key);
    }

    public void insertHelper(int key, int value, int table_id, int count, int original){
        if(key == original){
            count++;
            if(count == 3) {
                //System.out.println("Infinite Loop");
                return;
            }
        }
        position[0] = getHashValue(key, 0);
        position[1] = getHashValue(key, 1);
        for(int i = 0; i < 2; i++){        //if already present
            if(table[i][position[i]] != null && table[i][position[i]].getKey() == key) {
                table[i][position[i]].setValue(value);
                return;
            }
        }
        if(table[table_id][position[table_id]] != null){
            hT temp = table[table_id][position[table_id]];
            table[table_id][position[table_id]] = new hT(key, value);
            insertHelper(temp.getKey(), temp.getValue(), (table_id + 1) % 2, count, original);
        }
        else{
            table[table_id][position[table_id]] = new hT(key, value);
        }
    }

    public void remove(int key){
        int t = 0;
        int id = getHashValue(key, t);
        if(table[t][id] != null && table[t][id].getKey() != key){
            id = getHashValue(key, 1 - t);
            if(table[1 - t][id] != null && table[1 - t][id].getKey() != key){
                //System.out.println("Key not found");
                return;
            }
            else
                table[1 - t][id] = null;
        }
        else
            table[t][id] = null;
    }

    public void print(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < size; j++){
                if(table[i][j] != null){
                    System.out.print(table[i][j].getKey() + " ");
                }
                else{
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

}
class hT{
    private int key;
    private int value;
    public hT(int key, int value){
        this.key = key;
        this.value = value;
    }
    public int getKey(){
        return key;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int val){this.value = val;}
}
