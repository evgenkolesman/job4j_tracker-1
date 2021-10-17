package ru.job4j.exercises;

public class Dock {

    public static void main(String[] args) {
        String ward = args[0];
        String targetString = "Hello mister, Hello ! I like to say Hello , mister";
        String[] arr = targetString.split(" ");
        if (targetString.contains(ward)) {
            int res = 0;
            for (int i = 0; i < arr.length; i++) {

                for (String s : arr) {
                    if (s.toLowerCase().equalsIgnoreCase(arr[i])) {
                        res++;
                    }
                }
            }
            System.out.println(res);
        }
    }
}
