package org.example.Inteface;

import java.util.ArrayList;

public interface IDB {
    void save_user(long user_id, String user_name, boolean isFirst);
    void save_game_user_stats(String user_name_winner, String user_name_loser);
    ArrayList<String> loadData() ;
    void update(String oldData, String newData);
    void delete(String data);
}
