package agh.ics.oop.model;

import java.util.Arrays;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    @Override
    public String toString(){
        return switch (this){
            case NORTHEAST -> "Północny wschód";
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTHEAST -> "Południowy wschód";
            case SOUTH -> "Południe";
            case SOUTHWEST -> "Południowy zachód";
            case NORTHWEST -> "Północny zachód";
        };
    }

    public MapDirection next(){
        int next = (this.ordinal() + 1) % MapDirection.values().length;
        return MapDirection.values()[next];
    }

    public MapDirection previous(){
        int previous = (this.ordinal() + MapDirection.values().length - 1) % MapDirection.values().length;
        return MapDirection.values()[previous];
    }

    public MapDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTHEAST -> SOUTHWEST;
            case EAST -> WEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTH -> NORTH;
            case SOUTHWEST -> NORTHEAST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }

    public MapDirection rotateBy(MapDirection gene) {
        int numberOfDirections = MapDirection.values().length;
        int currentDirectionIndex = Arrays.asList(MapDirection.values()).indexOf(this);
        int geneIndex = Arrays.asList(MapDirection.values()).indexOf(gene);
        int newDirectionIndex = (currentDirectionIndex + geneIndex) % numberOfDirections;
        return MapDirection.values()[newDirectionIndex];
    }


    public Vector2d toUnitVector(){
        switch (this){
            case NORTH -> {
                return new Vector2d(0,1);
            }
            case SOUTH -> {
                return new Vector2d(0, -1);
            }
            case EAST -> {
                return new Vector2d(1,0);
            }
            case WEST -> {
                return new Vector2d(-1,0);
            }
            case SOUTHEAST -> {
                return new Vector2d(1,-1);
            }
            case NORTHEAST -> {
                return new Vector2d(1,1);
            }
            case NORTHWEST -> {
                return new Vector2d(-1,1);
            }
            case SOUTHWEST -> {
                return new Vector2d(-1,-1);
            }
        }
        return null;
    }

}
