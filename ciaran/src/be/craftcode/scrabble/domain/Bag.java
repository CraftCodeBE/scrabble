package be.craftcode.scrabble.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {
    List<Letter> bag = new ArrayList<>();


    public void startTiles(Player player){
        for(int i = 0; i < 7; i ++){
            player.addTile(this.bag.remove(ThreadLocalRandom.current().nextInt(98-i)));
        }
    }

    public void fillBag(){
        this.bag = new Stack<>();
        for(int i = 0; i < 12; i ++){
            this.bag.add(Letter.E);
        }
        for(int i = 0; i < 9; i ++){
            this.bag.add(Letter.A);
            this.bag.add(Letter.I);
        }
        for(int i = 0; i < 8; i ++){
            this.bag.add(Letter.O);
        }

        for(int i = 0; i < 6; i ++){
            this.bag.add(Letter.R);
            this.bag.add(Letter.N);
            this.bag.add(Letter.T);
        }
        for(int i = 0; i < 4; i ++){
            this.bag.add(Letter.L);
            this.bag.add(Letter.S);
            this.bag.add(Letter.U);
            this.bag.add(Letter.D);
        }
        for(int i = 0; i < 3; i ++){
            this.bag.add(Letter.G);
        }
        for(int i = 0; i < 2; i ++){
            this.bag.add(Letter.B);
            this.bag.add(Letter.C);
            this.bag.add(Letter.M);
            this.bag.add(Letter.P);
            this.bag.add(Letter.F);
            this.bag.add(Letter.H);
            this.bag.add(Letter.V);
            this.bag.add(Letter.W);
            this.bag.add(Letter.Y);
        }
        for(int i = 0; i < 1; i ++){
            this.bag.add(Letter.K);
            this.bag.add(Letter.J);
            this.bag.add(Letter.X);
            this.bag.add(Letter.Q);
            this.bag.add(Letter.Z);
        }
    }


}
