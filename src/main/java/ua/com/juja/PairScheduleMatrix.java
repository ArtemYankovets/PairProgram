package ua.com.juja;

public class PairScheduleMatrix {
    private static int membersNum = 7;
    private static int weeksNum;
    private static String[][] schedule;

    final static int TABLE_GROUPE_NAMES_CYCLE = 676;    //26*26 = 676
    final static int EN_ALFABET_AMOUNT = 26;
    final static int ASCII_CODE_A = 65;                 //65 - ASCII code of capital 'A'
    private static int firstCopyPoint;
    private static String[] groupNameArray;
    private static int letterNumber = 0;

    public static void main(String[] args) {
        createMatrix(membersNum);
        fillMatrix();
        printMartix();
        System.out.println(weeksNum);
    }

    private static void createMatrix(int membersNum) {

        //culculated amout of weeks with unique range of groups
        //log2(membersNum) = log10(membersNum)/log10(2)
        //result rounded to positive infinity
        weeksNum = (int) Math.round(Math.log10(membersNum) / Math.log10(2));

        //created schedule table
        schedule = new String[membersNum][weeksNum];

        //culculated groups (pairs) amount
        int groupNum = (membersNum >> 1);

        //created and filled groupNameArray
        while (groupNum > 0) {
            fillGoupeNameArray();
            groupNum -= 26;
        }
    }

    private static void printMartix() {
        System.out.print("Week is: \t\t");
        for (int i = 0; i < schedule[0].length; i++) {
            System.out.print((i + 1) + "\t\t");
        }
        System.out.println();

        for (int i = 0; i < schedule.length; i++) {
            int num = i + 1;
            System.out.print("Member " + num + "\t\t");
            for (int j = 0; j < schedule[i].length; j++) {
                System.out.print(String.valueOf(schedule[i][j]) + "\t\t");
            }
            System.out.println();
        }
    }

    private static void fillMatrix() {
        int delta = 1;
        int columns = 0;
        while (columns < weeksNum) {
            int index = 0;
            int rows = 0;
            while (rows < schedule.length) {
                if ((schedule[rows][columns]) == null) {
                    schedule[rows][columns] = groupNameArray[index];
                    if ((rows + delta) < schedule.length) schedule[rows + delta][columns] = groupNameArray[index];

                    index++;

                    //todo: check groups name's amount with weeks amount
                    if (index >= groupNameArray.length) fillGoupeNameArray();

                }
                rows++;
            }
            delta++;
            columns++;
        }
    }

    private static void fillGoupeNameArray() {
        //Memorized point for making further new groups of names
        if ((groupNameArray != null) && (groupNameArray.length % TABLE_GROUPE_NAMES_CYCLE == 0)) {
            firstCopyPoint = groupNameArray.length;
        }

        if (groupNameArray == null) {
            int oldSize = 0;
            //Created new array of groups names
            String[] alpha = new String[EN_ALFABET_AMOUNT];

            letterNumber = 0;
            for (int i = 0; i < EN_ALFABET_AMOUNT; i++) {
                alpha[oldSize + i] = Character.toString((char) (ASCII_CODE_A + (letterNumber++))); //65 - ASCII code of capital 'A'
            }
            groupNameArray = alpha;
        } else {
            while (letterNumber >= EN_ALFABET_AMOUNT) {
                letterNumber -= EN_ALFABET_AMOUNT;
            }
            int oldSize = groupNameArray.length;
            String[] alpha = new String[groupNameArray.length + EN_ALFABET_AMOUNT];

            //Copied old array to new larger array
            System.arraycopy(groupNameArray, 0, alpha, 0, oldSize);

            //Fill additional part of new array by mask for making new names of groups
            System.arraycopy(alpha, firstCopyPoint, alpha, oldSize, EN_ALFABET_AMOUNT);

            //Make new names of groups
            for (int i = 0; i < EN_ALFABET_AMOUNT; i++) {
                alpha[oldSize + i] = Character.toString((char) (ASCII_CODE_A + letterNumber)) + alpha[oldSize + i];
            }
            groupNameArray = alpha;
            letterNumber++;
        }
    }
}
