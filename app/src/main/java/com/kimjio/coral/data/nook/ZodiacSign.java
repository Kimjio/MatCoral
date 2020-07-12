package com.kimjio.coral.data.nook;

import androidx.annotation.DrawableRes;
import androidx.annotation.StyleRes;

import com.kimjio.coral.R;

public enum ZodiacSign {
    ARIES,
    TAURUS,
    GEMINI,
    CANCER,
    LEO,
    VIRGO,
    LIBRA,
    SCORPIO,
    SAGITTARIUS,
    CAPRICORN,
    AQUARIUS,
    PISCES;
    
    @StyleRes
    public int getTitleTheme() {
        switch (this) {
            case ARIES:
                return R.style.Title_Aries;
            case TAURUS:
                return R.style.Title_Taurus;
            case GEMINI:
                return R.style.Title_Gemini;
            case CANCER:
                return R.style.Title_Cancer;
            case LEO:
                return R.style.Title_Leo;
            case VIRGO:
                return R.style.Title_Virgo;
            case LIBRA:
                return R.style.Title_Libra;
            case SCORPIO:
                return R.style.Title_Scorpio;
            case SAGITTARIUS:
                return R.style.Title_Sagittarius;
            case CAPRICORN:
                return R.style.Title_Capricorn;
            case AQUARIUS:
                return R.style.Title_Aquarius;
            case PISCES:
                return R.style.Title_Pisces;
        }
        throw new IllegalArgumentException();
    }

    @StyleRes
    public int getPatternTheme() {
        switch (this) {
            case ARIES:
                return R.style.Pattern_Aries;
            case TAURUS:
                return R.style.Pattern_Taurus;
            case GEMINI:
                return R.style.Pattern_Gemini;
            case CANCER:
                return R.style.Pattern_Cancer;
            case LEO:
                return R.style.Pattern_Leo;
            case VIRGO:
                return R.style.Pattern_Virgo;
            case LIBRA:
                return R.style.Pattern_Libra;
            case SCORPIO:
                return R.style.Pattern_Scorpio;
            case SAGITTARIUS:
                return R.style.Pattern_Sagittarius;
            case CAPRICORN:
                return R.style.Pattern_Capricorn;
            case AQUARIUS:
                return R.style.Pattern_Aquarius;
            case PISCES:
                return R.style.Pattern_Pisces;
        }
        throw new IllegalArgumentException();
    }

    @DrawableRes
    public int getDrawableRes() {
        switch (this) {
            case ARIES:
                return R.drawable.ic_nook_aries;
            case TAURUS:
                return R.drawable.ic_nook_taurus;
            case GEMINI:
                return R.drawable.ic_nook_gemini;
            case CANCER:
                return R.drawable.ic_nook_cancer;
            case LEO:
                return R.drawable.ic_nook_leo;
            case VIRGO:
                return R.drawable.ic_nook_virgo;
            case LIBRA:
                return R.drawable.ic_nook_libra;
            case SCORPIO:
                return R.drawable.ic_nook_scorpio;
            case SAGITTARIUS:
                return R.drawable.ic_nook_sagittarius;
            case CAPRICORN:
                return R.drawable.ic_nook_capricorn;
            case AQUARIUS:
                return R.drawable.ic_nook_aquarius;
            case PISCES:
                return R.drawable.ic_nook_pisces;
        }
        throw new IllegalArgumentException();
    }
}
