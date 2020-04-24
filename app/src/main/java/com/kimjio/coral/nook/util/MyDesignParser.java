package com.kimjio.coral.nook.util;

import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.common.base.Splitter;
import com.kimjio.coral.data.nook.MyDesignQR;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MyDesignParser {

    public static MyDesignQR analyzeQrBinary(@NonNull byte[] buffer) {
        final String encodeText = Base64.encodeToString(buffer, Base64.DEFAULT);
        final MyDesignQR.Type type = detectMyDesignType(buffer);
        final byte[] bytes = newByteArr(buffer, type);

        if (type == MyDesignQR.Type.NORMAL) {
            return new MyDesignQR(
                    readString(bytes, 0,42),
                    readLong(bytes, 42, 2),
                    readString(bytes, 44, 18),
                    readInt(bytes, 62, 1),
                    readLong(bytes, 64, 2),
                    readString(bytes, 66, 18),
                    readInt(bytes, 84, 1),
                    readInt(bytes, 86, 1),
                    readInt(bytes, 87, 1),
                    Arrays.copyOfRange(bytes, 88, 103),
                    readInt(bytes, 103, 1),
                    readInt(bytes, 104, 1),
                    readInt(bytes, 105, 1),
                    Arrays.copyOfRange(bytes, 108, 620),
                    encodeText,
                    -1,
                    -1
            );
        } else if (type == MyDesignQR.Type.PRO) {
            long consecutiveId = extractConsecutiveId(buffer);
            int sheetIndex = detectSheets(buffer);

            switch (sheetIndex) {
                case 0:
                    return new MyDesignQR(
                            readString(bytes, 0,42),
                            readLong(bytes, 42, 2),
                            readString(bytes, 44, 18),
                            readInt(bytes, 62, 1),
                            readLong(bytes, 64, 2),
                            readString(bytes, 66, 18),
                            readInt(bytes, 84, 1),
                            readInt(bytes, 86, 1),
                            readInt(bytes, 87, 1),
                            Arrays.copyOfRange(bytes, 88, 103),
                            readInt(bytes, 103, 1),
                            readInt(bytes, 104, 1),
                            readInt(bytes, 105, 1),
                            Arrays.copyOfRange(bytes, 108, 540),
                            encodeText,
                            sheetIndex,
                            consecutiveId
                    );
                case 1:
                case 2:
                    return new MyDesignQR(
                            null,
                            -1,
                            null,
                            -1,
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            Arrays.copyOfRange(bytes, 0, 540),
                            encodeText,
                            sheetIndex,
                            consecutiveId
                    );
                case 3:
                    return new MyDesignQR(
                            null,
                            -1,
                            null,
                            -1,
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            Arrays.copyOfRange(bytes, 0, 536),
                            encodeText,
                            sheetIndex,
                            consecutiveId
                    );
                default:
                    throw new IllegalArgumentException("invalid sheet index");
            }
        }
        throw new IllegalArgumentException("invalid as my design");
    }

    private static int extractConsecutiveId(byte[] buffer) {
        return (buffer[1] << 4) + (buffer[2] >> 4);
    }

    private static int detectSheets(byte[] buffer) {
        String code = padStart(toHexString(buffer[0]), 2, "0");
        if (code.charAt(0) != '3') {
            throw new IllegalArgumentException("invalid as my design pro header format");
        }
        int i = Integer.parseInt(Character.toString(code.charAt(1)), 10);
        if (i < 0 || 3 < i) {
            throw new IllegalArgumentException("invalid as my design pro sheet index");
        }
        return i;
    }

    private static MyDesignQR.Type detectMyDesignType(byte[] buffer) {
        if (isNormal(buffer)) {
            return MyDesignQR.Type.NORMAL;
        } else if (isPro(buffer)) {
            return MyDesignQR.Type.PRO;
        }
        throw new IllegalArgumentException("unknown type");
    }

    private static boolean isNormal(byte[] buffer) {
        if (buffer.length != 627)
            return false;
        return (toHexString(buffer[0]).equals("40") &&
                toHexString(buffer[1]).equals("26") &&
                Character.toString(padStart(toHexString(buffer[2]), 2, "0").charAt(0)).toUpperCase().equals("C"));
    }

    private static boolean isPro(byte[] buffer) {
        if (buffer.length != 563)
            return false;
        return (padStart(toHexString(buffer[0]), 2, "0").charAt(0) == '3' &&
                padStart(toHexString(buffer[1]), 2, "0").charAt(0) == '3' &&
                padStart(toHexString(buffer[2]), 2, "0").charAt(1) == '4' &&
                padStart(toHexString(buffer[3]), 2, "0").equals("02") &&
                toHexString(buffer[4]).toUpperCase().equals("1C"));
    }

    private static byte[] newByteArr(byte[] buffer, MyDesignQR.Type type) {
        if (type == MyDesignQR.Type.PRO) {
            return Arrays.copyOfRange(buffer, 5, 545);
        } else if (type == MyDesignQR.Type.NORMAL) {
            String[] arr = getStringArr(buffer);
            String code = arr[2].toUpperCase();
            String[] rest = Arrays.copyOfRange(arr, 3, buffer.length);
            if (code.charAt(0) != 'C') {
                throw new IllegalArgumentException("invalid as my design normal");
            }
            String x = Character.toString(code.charAt(1));
            List<String> arrayList = new ArrayList<>(Arrays.asList(rest));
            arrayList.add(0, x);
            String strBin = TextUtils.join("", arrayList.toArray(new String[]{}));
            List<String> strBinArray = Splitter.fixedLength(2).splitToList(strBin);

            List<Byte> byteList = new ArrayList<>();
            for (String s : strBinArray) {
                byteList.add((byte) (Integer.parseInt(s, 16) & 0xFF));
            }
            byte[] bytes = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                bytes[i] = byteList.get(i);
            }

            return bytes;
        }

        throw new IllegalArgumentException("invalid as my design");
    }

    private static String readString(byte[] buffer, int offset, int length) {
        byte[] sub = Arrays.copyOfRange(buffer, offset, offset + length);
        String str = new String(sub, StandardCharsets.UTF_16LE);
        return str.contains("\u0000") ? str.split("\\u0000")[0] : str;
    }

    private static int readInt(byte[] buffer, int offset, int length) {
        byte[] sub = Arrays.copyOfRange(buffer, offset, offset + length);
        int num = 0;
        for (byte b : sub) {
            num = (num << 8) + b;
        }
        return num;
    }

    private static long readLong(byte[] buffer, int offset, int length) {
        byte[] sub = Arrays.copyOfRange(buffer, offset, offset + length);
        long num = 0;
        for (byte b : sub) {
            num = (num << 8) + b;
        }
        return num;
    }

    private static String[] getStringArr(byte[] buffer) {
        String[] temp = new String[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            temp[i] = padStart(toHexString(buffer[i]), 2, "0");
        }

        return temp;
    }

    private static String toHexString(int value) {
        return String.format("%02X", (0xFF & value));
    }

    private static String padStart(String string, int length, String fill) {
        return String.format("%" + length + "s", string).replace(" ", fill);
    }
}
