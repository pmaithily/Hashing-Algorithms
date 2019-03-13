package src;
import java.util.Random;

public class linearHashing {
    static int size = 11;
    static int del = 50;
    static int search = 100;
    static int no = 1000;
    int fill;
    static HashTable[] table;
    public static void main(String[] argv){
        table = new HashTable[size];
        linearHashing map = new linearHashing();

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

        //Random rand = new Random();
        /*long st = System.nanoTime();
        for(int k = 0; k < size; k++){

            hash.put(rand.nextInt(no) , k * 10);
        }
        long end = System.nanoTime();
        System.out.println("Insertions: " + (end - st));
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

    public int get(int key){
      int i = key % size;
      int cnt = 0;
      while(cnt <= size && table[i] != null && table[i].getKey() != key){
          i = (i + 1) % size;
          cnt++;
      }
      if(table[i] != null){
          //System.out.println("Value present: " + table[i].getValue());
          return table[i].getValue();
      }
      if(cnt > size) {
          //System.out.println("Key Not Found: " + key);
          return -1;
      }
      else {
          //System.out.println("Key Not Found: " + key);
          return -1;
      }
    }

    public void put(int key, int value){
        if(size == fill){
            //System.out.println("Load Factor 1 => Table Full");
            return;
        }
        int i = key % size;
        int cnt = 0;
        if(table[i] == null)
            table[i] = new HashTable(key, value);
        while(table[i] != null && table[i].getKey() != key && cnt < size){
            i = (i + 1) % size;
            cnt++;
        }
        table[i] = new HashTable(key, value);
        fill++;
    }

    public void remove(int key){
        int i = key % size;
        int cnt = 0;
        while(cnt < size && table[i] != null && table[i].getKey() != key){
            i = (i + 1) % size;
            cnt++;
        }
        if(table[i] == null) {
            //System.out.println("Key Not Found: " + key);
            return;
        }
        if(cnt > size)
            return;
        int j = (i + 1) % size;
        cnt = 0;
        while(j < size && cnt <= size && table[j] != null){
            if(table[j].getKey() % size <= i || table[j].getKey() % size > j){
                table[i] = table[j];
                i = j;
            }
            j = (j + 1) % size;
            cnt++;
        }
        table[i] = null;
        fill--;
    }

    public void print(){
        if(fill == 0)
            return;
        int i = 0;
        while(i < size){
            System.out.print("Cell " + i + ":");
            if(table[i] != null)
                System.out.print(table[i].getKey());
            System.out.println();
            i++;
        }
    }

    public void input(){
        for(int i = 0; i < size; i++){
            put(i, i * 10);
        }
        for(int i = 0; i < size; i = i + 2){
            remove(i);
        }
        for(int i = 0; i < size; i++){
            get(i);
        }
    }
}

class HashTable{
    private int key;
    private int value;
    public HashTable(int key, int value){
        this.key = key;
        this.value = value;
    }
    public int getKey(){
        return key;
    }
    public int getValue(){
        return value;
    }
}
