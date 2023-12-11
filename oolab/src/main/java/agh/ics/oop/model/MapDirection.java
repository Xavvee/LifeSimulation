package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    @Override
    public String toString(){
        return switch (this){
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
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
        }
        return null;
    }

}
