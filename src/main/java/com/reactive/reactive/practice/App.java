package com.reactive.reactive.practice;

public class App {
    public static void main(String[] args) {
        MyPub pub = new MyPub();
        MySub sub = new MySub();

        pub.subscribe(sub);
    }
}
