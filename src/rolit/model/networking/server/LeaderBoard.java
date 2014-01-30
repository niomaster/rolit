package rolit.model.networking.server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Date;
import rolit.model.networking.server.Score;

/**
 * Het leaderboard.
 * @author Martijn de Bijl
 */
public class LeaderBoard<T extends Number & Comparable<T>> {

    LinkedList<Score<T>> scores;

    public LeaderBoard(){
        this.scores = new LinkedList<Score<T>>();
    }

    public int getSize(){
        return scores.size();
    }

    public void add(Score newScore){
        scores.add(newScore);
        Collections.sort(scores, Collections.reverseOrder());
    }

    public Score get(int i){
        return scores.get(i);
    }

    public Score getMax(){
        return scores.getFirst();
    }

    public Score getDay(Date date){
        LeaderBoard leaderBoardDay = new LeaderBoard();
        for (int i = 0; i < getSize(); i++){
            if (get(i).getDate() == date){
                leaderBoardDay.add(scores.get(i));
            }
        }
        return leaderBoardDay.getMax();
    }

    public Score getPlayer(String user){
        LeaderBoard playerLeaderBoard = new LeaderBoard();
        for (int i = 0; i < getSize(); i++){
            if (get(i).getUser().equals(user)){
                playerLeaderBoard.add(get(i));
            }
        }
        return playerLeaderBoard.getMax();
    }

}
