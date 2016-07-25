package ch01;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Darshan on 7/10/16.
 */
public class StringSorter {
    public List<String> sortLexicographically(List<String> strings) {
        Collections.sort(strings);
        return strings;
    }

    public List<String> sortByDecreasingLength(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        return strings;
    }
}
