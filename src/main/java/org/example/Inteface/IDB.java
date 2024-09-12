package org.example.Inteface;

import java.util.ArrayList;

public interface IDB {
    void save(String data);
    ArrayList<String> loadData() ;
    void update(String data);
    void delete(String data);
}
