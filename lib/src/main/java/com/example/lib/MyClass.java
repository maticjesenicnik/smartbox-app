package com.example.lib;

import java.time.LocalDateTime;
import java.util.Date;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Statistics statistics = new Statistics("00541", new Date(), true);
        System.out.println(statistics.toString());
    }
}
