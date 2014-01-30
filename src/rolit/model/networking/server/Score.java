package rolit.model.networking.server;

import java.util.Date;
/**
 * De scores in het leaderboard.
 * @author Martijn de Bijl
 */
public class Score <T extends Number & Comparable<T>> implements Comparable<T> {

    private T score;
    private String user;
    private Date date;

    public Score(T score, String user, Date date){
        this.score = score;
        this.user = user;
        this.date = date;
    }

    public T getScore(){
        return score;
    }

    public String getUser(){
        return user;
    }

    public Date getDate(){
        return date;
    }

    @Override
    public int compareTo(T o){
        return this.score.compareTo(o);
    }

}
