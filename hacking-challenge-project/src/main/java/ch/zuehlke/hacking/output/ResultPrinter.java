package ch.zuehlke.hacking.output;

public class ResultPrinter {
    public void print(int score) {
        System.err.println("******************************************************");
        System.err.println("\t\tScore: " + score + "\t\t");
        System.err.println("******************************************************");
    }

    public void print(int score, String name) {
        System.err.println("******************************************************");
        System.err.println("\t\t"+name+"\tScore: " + score + "\t\t");
        System.err.println("******************************************************");
    }
}
