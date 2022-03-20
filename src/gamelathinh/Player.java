/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelathinh;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class Player implements Serializable{
     String name;
    String rank;
    int score;
    public Player() {
    }

    public Player(String name, int srore) {
        this.name = name;
        this.score = srore;
    }

    Player(Player obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void caculateRank(String score) {
        
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    public void input() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Your Name: ");
        setName(input.nextLine());
    }
}
