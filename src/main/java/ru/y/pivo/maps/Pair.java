package ru.y.pivo.maps;

public class Pair<T extends Object,
        S extends Object> {
    private T x;
    private S y;

    public Pair(T x, S y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public S getY() {
        return y;
    }

    public void setY(S y) {
        this.y = y;
    }
}
