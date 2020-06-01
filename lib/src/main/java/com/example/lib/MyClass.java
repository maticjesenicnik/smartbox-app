package com.example.lib;

import java.time.LocalDateTime;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Statistics statistics = new Statistics("00541", LocalDateTime.now(), true);
        System.out.println(statistics.toString());
    }
}
