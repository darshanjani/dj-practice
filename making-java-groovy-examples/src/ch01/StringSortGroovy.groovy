package ch01
/**
 * Created by Darshan on 7/10/16.
 */
class StringSortGroovy {
    public static void main(String[] args) {
        def strings = ['this', 'is', 'a', 'list', 'of', 'strings']
//        Collections.sort(strings, {s1, s2 -> s1.size() - s2.size()} as Comparator)
        strings.sort  { -it?.size() }
        println (strings)
        assert strings*.size() == [7, 4, 4, 2, 2, 1]
    }
}
