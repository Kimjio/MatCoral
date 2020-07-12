package com.kimjio.coral.data.nook;

import androidx.annotation.DrawableRes;

import com.kimjio.coral.R;

public class Fruit {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        switch (id) {
            case 2213:
                return Type.APPLE;
            case 2214:
                return Type.ORANGE;
            case 2285:
                return Type.PEAR;
            case 2286:
                return Type.PEACH;
            case 2287:
                return Type.CHERRY;
            default:
                throw new IllegalArgumentException(id + " is invalid id");
        }
    }

    public enum Type {
        APPLE(2213),
        ORANGE(2214),
        PEAR(2285),
        PEACH(2286),
        CHERRY(2287);

        private int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @DrawableRes
        public int getDrawableRes() {
            switch (this) {
                case APPLE:
                    return R.drawable.ic_nook_apple;
                case ORANGE:
                    return R.drawable.ic_nook_orange;
                case PEAR:
                    return R.drawable.ic_nook_pear;
                case PEACH:
                    return R.drawable.ic_nook_peach;
                case CHERRY:
                    return R.drawable.ic_nook_cherry;
            }
            throw new IllegalArgumentException();
        }
    }
}
