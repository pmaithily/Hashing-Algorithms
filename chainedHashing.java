package src;

import java.util.Random;

public class chainedHashing {
    static LinkedListNode[] table;
    static int size = 11;
    static int del = 50;
    static int search = 100;
    static int no = 1000;
    public static void main(String[] argv) {
        chainedHashing map = new chainedHashing(size);
       /*long st = System.nanoTime();
        chain.put(12, 10);
        chain.put(26, 20);
        chain.put(92, 30);
        chain.put(23, 40);
        chain.remove(12);
        chain.put(28, 50);
        chain.put(94, 60);
        chain.put(15, 70);
        chain.put(27, 80);
        chain.put(27, 90);
        chain.remove(15);
        chain.get(15);
        chain.get(27);
        long end = System.nanoTime();
        //System.out.println(end - st);
       chain.print();*/
        Random rand = new Random();
        long st = System.nanoTime();
        for(int k = 0; k < size; k++){

            map.put(rand.nextInt(no) , k * 10);
        }
        long end = System.nanoTime();
        System.out.println("iNSERTIONS : "+ (end - st));
        st = System.nanoTime();
        for(int k = 0; k < del; k++){

            map.remove(rand.nextInt(no));
        }
        end = System.nanoTime();
        System.out.println("Deletions: " + (end - st));
        st = System.nanoTime();
        for(int k = 0; k < search; k++){

            map.get(rand.nextInt(no));
        }
        end = System.nanoTime();
        System.out.println("Search: " + (end - st));


    }
    chainedHashing(int input){
        size = input;
        table = new LinkedListNode[size];
        int i = 0;
        while(i < size){
            table[i] = null;
            i++;
        }
    }
    public void put(int key, int value){
        int hash = key % size;
        if(table[hash] == null){
            table[hash] = new LinkedListNode(key, value);
        }
        else{
            LinkedListNode node = table[hash];
            while(node.next != null && node.getKey() != key){
                node = node.next;
            }
            if(node.getKey() == key)
                node.setValue(value);
            else{
                node.next = new LinkedListNode(key, value);
            }
        }
    }

    public int get(int key){
        int hash = key % size;
        if(table[hash] == null) return -1;
        else{
            LinkedListNode node = table[hash];
            while(node.next != null && node.getKey() != key)
                node = node.next;
            if(node == null) {
                //System.out.println("Key Not found: " + key);
                return -1;
            }
            else {
                //System.out.println("Value: " + node.getValue());
                return node.getValue();
            }
        }
    }

    public void remove(int key){
        int hash = key % size;
        if(table[hash] == null) {
            //System.out.println("Key Not found: " + key);
            return;
        }
        else{
            LinkedListNode prev = null;
            LinkedListNode node = table[hash];
            while(node.next != null && node.getKey() != key){
                prev = node;
                node = node.next;
            }
            if(node.getKey() == key){
                if(prev == null){
                    LinkedListNode head = node.next;
                    table[hash] = head;
                }
                else
                    prev.next = node.next;
            }
        }
    }

    public void print(){
        int i = 0;
        while(i < size){
            System.out.print("Cell " + i + ":");
            LinkedListNode node = table[i];
            while(node != null){
                System.out.print(node.getKey() + " ");
                node = node.next;
            }
            System.out.println();
            i++;
        }
    }
}

class LinkedListNode{
    private int key;
    private int value;
    LinkedListNode next;
    LinkedListNode(int key, int value){
        this.key = key;
        this.value = value;
        this.next = null;
    }
    public int getKey(){
        return key;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int val){ this.value = val;}
}


