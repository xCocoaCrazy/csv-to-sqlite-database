package com.example;

import com.example.classes.Datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Datasource datasource = new Datasource("Enterprise", "bank", "./Resources/enterprise.csv");
        Datasource datasource1 = new Datasource("data", "data", "./Resources/data.csv");
    }
}
