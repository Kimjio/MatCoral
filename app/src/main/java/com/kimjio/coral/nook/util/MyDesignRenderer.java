package com.kimjio.coral.nook.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.common.primitives.Bytes;

public final class MyDesignRenderer {
    public static final int CANVAS_SIZE = 240;

    private static final String[] colorCodes = {
            "#FEF", "#F9A", "#E59", "#F6A", "#F06", "#B47", "#C05", "#903", "#523",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#FBC", "#F77", "#D31", "#F54", "#F00", "#C66", "#B44", "#B00", "#822",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#EEE", "#DCB", "#FC6", "#D62", "#FA2", "#F60", "#B85", "#D40", "#B40", "#631",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#DDD", "#FED", "#FDC", "#FCA", "#FB8", "#FA8", "#D86", "#B64", "#953", "#842",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#CCC", "#FCF", "#E8F", "#C6D", "#B8C", "#C0F", "#969", "#80A", "#507", "#304",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#BBB", "#FBF", "#F9F", "#D2B", "#F5E", "#F0C", "#857", "#B09", "#806", "#504",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#AAA", "#DB9", "#CA7", "#743", "#A74", "#930", "#732", "#520", "#310", "#210",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#999",
            "#FFC", "#FF7", "#DD2", "#FF0", "#FD0", "#CA0", "#990", "#870", "#550",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#888", "#DBF", "#B9E", "#63C", "#95F", "#60F", "#548", "#409", "#206", "#213",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#777", "#BBF", "#89F", "#33A", "#35E", "#00F", "#338", "#00A", "#116", "#002",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#666", "#9EB", "#6C7", "#261", "#4A3", "#083", "#575", "#250", "#132", "#021",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#555", "#DFB", "#CF8", "#8A5", "#AD8", "#8F0", "#AB9", "#6B0", "#590", "#360",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#444", "#BDF", "#7CF", "#359", "#69F", "#17F", "#47A", "#247", "#027", "#014",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#333", "#AFF", "#5FF", "#08B", "#5BC", "#0CF", "#49A", "#068", "#045", "#023",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#222", "#CFE", "#AED", "#3CA", "#5EB", "#0FC", "#7AA", "#0A9", "#087", "#043",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF",
            "#000", "#AFA", "#7F7", "#6D4", "#0F0", "#2D2", "#5B5", "#0B0", "#080", "#242",
            "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF", "#FFF"
    };

    public static Bitmap renderNormalToCanvas(byte[] paletteColors, byte[] colorData) {
        Bitmap bitmap = Bitmap.createBitmap(CANVAS_SIZE, CANVAS_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        final int squareSize = CANVAS_SIZE / 32;

        if (colorData == null || paletteColors == null) {
            throw new IllegalArgumentException("missing colorData or paletteColors");
        }

        final byte[] squareData = new byte[32 * 32];
        for (int i = 0; i < colorData.length; i++) {
            byte b = colorData[i];
            squareData[i * 2] = (byte) (b & 0xf);
            squareData[i * 2 + 1] = (byte) ((b >> 4) & 0xf);
        }

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                int colorCodeIndex = paletteColors[squareData[i * 32 + j]];
                Paint paint = new Paint();
                String color = colorCodes[colorCodeIndex & 0xFF];
                String r = Character.toString(color.charAt(1));
                String g = Character.toString(color.charAt(2));
                String b = Character.toString(color.charAt(3));
                paint.setColor(Color.parseColor("#" + r + r + g + g + b + b));
                Rect rect = new Rect();
                int x = Math.round(j * squareSize - 0.5f);
                int y = Math.round(i * squareSize - 0.5f);
                int size = Math.round(squareSize + 0.5f);
                rect.left = x;
                rect.top = y;
                rect.right = x + size;
                rect.bottom = y + size;
                canvas.drawRect(rect, paint);
            }
        }
        return bitmap;
    }

    /**
     * FIXME
     *
     * @param paletteColors
     * @param colorDataGroup
     * @param usage
     * @return
     */
    public static Bitmap renderProToCanvas(byte[] paletteColors, byte[][] colorDataGroup, int usage) {
        if (colorDataGroup.length != 4) {
            throw new IllegalArgumentException("4 QR data required for rendering PRO");
        }

        Bitmap bitmap = Bitmap.createBitmap(CANVAS_SIZE, CANVAS_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        final int squareSize = CANVAS_SIZE / 64;
        final int size = (int) Math.round(squareSize + 0.5);

        byte[] colorData = Bytes.concat(colorDataGroup[0], colorDataGroup[1], colorDataGroup[2], colorDataGroup[3]);
        byte[] squareData = new byte[colorData.length * 2];
        for (int i = 0; i < colorData.length; i++) {
            int b = colorData[i];
            squareData[i * 2] = (byte) (b & 0xF);
            squareData[i * 2 + 1] = (byte) ((b >> 4) & 0xF);
        }

        // back
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 32; j++) {
                if (isMaskedFrontBack(usage, j, i)) {
                    continue;
                }
                int index;
                if (i < 32) {
                    index = (i + 32) * 32 + j;
                } else {
                    index = (i - 32 + 112) * 32 + j;
                }
                int paletteColorIndex = squareData[index];
                if (paletteColorIndex >= 15) continue;
                int colorCodeIndex = paletteColors[paletteColorIndex] & 0xFF;
                Paint paint = new Paint();
                String color = colorCodes[colorCodeIndex];
                String r = Character.toString(color.charAt(1));
                String g = Character.toString(color.charAt(2));
                String b = Character.toString(color.charAt(3));
                paint.setColor(Color.parseColor("#" + r + r + g + g + b + b));
                Rect rect = new Rect();
                int x = Math.round(j * squareSize - 0.5f);
                int y = Math.round(i * squareSize - 0.5f);
                rect.left = x;
                rect.top = y;
                rect.right = x + size;
                rect.bottom = y + size;
                canvas.drawRect(rect, paint);
            }
        }

        // front
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 32; j++) {
                if (isMaskedFrontBack(usage, j, i)) {
                    continue;
                }
                int index;
                if (i < 32) {
                    index = i * 32 + j;
                } else {
                    index = (i - 32 + 96) * 32 + j;
                }
                int paletteColorIndex = squareData[index];
                if (paletteColorIndex >= 15) paletteColorIndex = 0;
                int colorCodeIndex = paletteColors[paletteColorIndex] & 0xFF;
                Paint paint = new Paint();
                String color = colorCodes[colorCodeIndex];
                String r = Character.toString(color.charAt(1));
                String g = Character.toString(color.charAt(2));
                String b = Character.toString(color.charAt(3));
                paint.setColor(Color.parseColor("#" + r + r + g + g + b + b));
                Rect rect = new Rect();
                int x = Math.round((j + 32) * squareSize - 0.5f);
                int y = Math.round(i * squareSize - 0.5f);
                rect.left = x;
                rect.top = y;
                rect.right = x + size;
                rect.bottom = y + size;
                canvas.drawRect(rect, paint);
            }
        }

        // right
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 32; j++) {
                if (isMaskedLeftRight(usage, j, i)) {
                    continue;
                }
                int index = (i + 64) * 32 + j;
                int paletteColorIndex = squareData[index];
                if (paletteColorIndex >= 15) continue;
                int colorCodeIndex = paletteColors[paletteColorIndex] & 0xFF;
                Paint paint = new Paint();
                String color = colorCodes[colorCodeIndex];
                String r = Character.toString(color.charAt(1));
                String g = Character.toString(color.charAt(2));
                String b = Character.toString(color.charAt(3));
                paint.setColor(Color.parseColor("#" + r + r + g + g + b + b));
                Rect rect = new Rect();
                int x = Math.round(j * squareSize - 0.5f);
                int y = Math.round((i + 48) * squareSize - 0.5f);
                rect.left = x;
                rect.top = y;
                rect.right = x + size;
                rect.bottom = y + size;
                canvas.drawRect(rect, paint);
            }
        }

        // left
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 32; j++) {
                if (isMaskedLeftRight(usage, j, i)) {
                    continue;
                }
                int index = (i + 80) * 32 + j;
                int paletteColorIndex = squareData[index];
                if (paletteColorIndex >= 15) continue;
                int colorCodeIndex = paletteColors[paletteColorIndex] & 0xFF;
                Paint paint = new Paint();
                String color = colorCodes[colorCodeIndex];
                String r = Character.toString(color.charAt(1));
                String g = Character.toString(color.charAt(2));
                String b = Character.toString(color.charAt(3));
                paint.setColor(Color.parseColor("#" + r + r + g + g + b + b));
                Rect rect = new Rect();
                int x = Math.round((j + 32) * squareSize - 0.5f);
                int y = Math.round((i + 48) * squareSize - 0.5f);
                rect.left = x;
                rect.top = y;
                rect.right = x + size;
                rect.bottom = y + size;
                canvas.drawRect(rect, paint);
            }
        }

        return bitmap;
    }

    private static boolean isMaskedFrontBack(int usage, int x, int y) {
        switch (usage) {
            case 3:
            case 4:
            case 5:
                return y >= 32;
            default:
                return false;
        }
    }

    private static boolean isMaskedLeftRight(int usage, int x, int y) {
        switch (usage) {
            case 1:
            case 4:
                return x >= 16;
            case 2:
            case 5:
                return true;
            default:
                return false;
        }
    }
}
