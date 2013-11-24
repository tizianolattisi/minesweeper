package com.lattisi.minesweeperqt;

import java.util.*;

/**
 * User: tiziano
 * Date: 13/11/13
 * Time: 20:26
 */
public class Game {

    public static final Integer DIM=9;
    public static final Float DENSITY=0.15f;
    private Boolean[][] bombs = new Boolean[DIM][DIM];
    private Boolean[][] flags = new Boolean[DIM][DIM];
    private Boolean[][] digged = new Boolean[DIM][DIM];
    private Random random;

    public void initGame(){
        random = new Random();
        random.setSeed(System.currentTimeMillis());

        for( Integer i=0; i< DIM; i++ ){
            for( Integer j=0; j< DIM; j++ ){
                Float r = random.nextFloat();
                digged[i][j] = Boolean.FALSE;
                flags[i][j] = Boolean.FALSE;
                if( r < DENSITY ){
                    bombs[i][j] = Boolean.TRUE;
                } else {
                    bombs[i][j] = Boolean.FALSE;
                }
            }
        }

    }

    public Boolean thereIsABomb(Integer i, Integer j){
        return bombs[i][j];
    }

    public Integer numOfBombs(){
        Integer c = 0;
        for( Integer i=0; i< DIM; i++ ){
            for( Integer j=0; j< DIM; j++ ){
                if( thereIsABomb(i, j) ){
                    c++;
                }
            }
        }
        return c;
    }

    public Integer numOfFlags(){
        Integer c = 0;
        for( Integer i=0; i< DIM; i++ ){
            for( Integer j=0; j< DIM; j++ ){
                if( isFlagged(i, j) ){
                    c++;
                }
            }
        }
        return c;
    }

    public Boolean isDigged(Integer i, Integer j){
        return digged[i][j];
    }

    public Integer howManyBombs(Integer i, Integer j){
        Integer c=0;
        for( int x=i-1; x<=i+1; x++ ){
            for( int y=j-1; y<=j+1; y++ ){
                if( x>=0 && x<DIM && y>=0 && y<DIM ){
                    if( thereIsABomb(x, y) ){
                        c++;
                    }
                }
            }
        }
        return c;
    }

    public void dig(Integer i, Integer j){
        if( digged[i][j] ){
            return;
        }
        digged[i][j] = Boolean.TRUE;
    }

    public Boolean isFlagged(Integer i, Integer j){
        return flags[i][j];
    }

    public void setFlag(Integer i, Integer j){
        flags[i][j] = Boolean.TRUE;
    }

    public void unSetFlag(Integer i, Integer j){
        flags[i][j] = Boolean.FALSE;
    }

    public List<Integer[]> neighbors(Integer i, Integer j){
        List<Integer[]> coordsList = new ArrayList<Integer[]>();
        for( int x=i-1; x<=i+1; x++ ){
            for( int y=j-1; y<=j+1; y++ ){
                if( x>=0 && x<DIM && y>=0 && y<DIM ){
                    if( !(x==i && y==j) && !digged[x][y]){
                        Integer[] coords = new Integer[2];
                        coords[0] = x;
                        coords[1] = y;
                        coordsList.add(coords);
                    }
                }
            }
        }
        return coordsList;
    }


}
