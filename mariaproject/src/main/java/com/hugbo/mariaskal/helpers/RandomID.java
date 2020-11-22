package com.hugbo.mariaskal.helpers;

public class RandomID {
    public static String generate() {
        char[] chars = new char[6];
        for (int i = 0; i < 6; i++) {
            int randomNumber = 65 + (int) (Math.random() * 25);
            chars[i] = (char) randomNumber;
        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        System.out.println(RandomID.generate());
    }
}
