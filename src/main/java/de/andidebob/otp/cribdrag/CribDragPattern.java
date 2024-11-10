package de.andidebob.otp.cribdrag;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CribDragPattern {
    AND("and"),
    ARE("are"),
    BUT("but"),
    FOR("for"),
    NOT("not"),
    THE("the"),
    YOU("you"),
    FROM("from"),
    HAVE("have"),
    THAT("that"),
    THIS("this"),
    WITH("with"),
    HELLO("hello"),
    SORRY("sorry"),
    WORLD("world"),
    PLEASE("please"),
    I_AM("i am"),
    YOU_ARE("you are"),
    IT_IS("it is");

    private final String pattern;

    public String asFiller() {
        return String.format(" %s ", pattern);
    }

    public String asStarter() {
        return String.format("%s%s ", pattern.substring(0, 1).toUpperCase(), pattern.substring(1));
    }
}
