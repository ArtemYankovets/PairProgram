package ua.com.juja;

public class PairScheduleMatrix {
    final static int MEMBERS = 8;
    final static String[] groupNameArray = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    private static String[][] schedule = new String[MEMBERS][MEMBERS];

    public static void main(String[] args) {
        fillMatrix();
        printMartix();
    }

    private static void printMartix() {

        System.out.print("Week is: \t");
        for (int i = 0; i < schedule.length; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println();

        for (int i = 0; i < schedule.length; i++) {
            int num = i + 1;
            System.out.print("Member " + num + "\t");
            for (int j = 0; j < schedule[i].length; j++) {
                System.out.print(String.valueOf(schedule[i][j]) + "\t");
            }
            System.out.println();
        }
    }


    private static void fillMatrix() {
        int delta = 1;

        for (int i = 0; i < schedule.length; i++) {
            int index = 0;
            for (int j = 0; j < schedule[i].length; j++) {
                if ((schedule[j][i]) == null) {
                    schedule[j][i] = groupNameArray[index];
                    if ((j + delta) < schedule.length) schedule[j + delta][i] = groupNameArray[index];
                    index++;
                }
            }
            delta++;
        }
    }
}
