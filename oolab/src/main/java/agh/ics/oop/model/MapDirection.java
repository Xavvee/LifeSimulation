package agh.ics.oop.model;

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
