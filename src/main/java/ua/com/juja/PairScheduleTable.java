package ua.com.juja;

import java.util.Arrays;

public class PairScheduleTable {
    private int membersNum = 3;

    private int weeksNumber;

    transient String[][] schedule;
    private int[] weekNumArray;

    final static int TABLE_GROUPE_NAMES_CYCLE = 676;    //26*26 = 676
    final static int EN_ALFABET_AMOUNT = 26;
    final static int ASCII_CODE_A = 65;                 //65 - ASCII code of capital 'A'

    private int firstCopyPoint;
    private String[] groupNameArray;
    private int letterNumber = 0;

    @Override
    public String toString() {
        return printPairScheduleMartix();
    }

    /**
     * Simple constructor for creating schedule matrix by competitors number
     * specified by the minimum capacity argument.
     *
     */
    public PairScheduleTable() {
        this.createWeekNumArray(1, calcWeekNum(membersNum));
        createMatrix(membersNum);
        fillMatrix(1, weeksNumber);
    }

    /**
     * Simple constructor for creating schedule matrix by competitors number
     *
     * @param membersNum the competitors number
     */
    public PairScheduleTable(int membersNum) {
        this.membersNum = membersNum;
        this.createWeekNumArray(1, calcWeekNum(membersNum));
        createMatrix(membersNum);
        fillMatrix(1, weeksNumber);
    }

    /**
     * Simple constructor for creating Schadule matrix by competitors number
     *
     * @param membersNum   the competitors number
     * @param startWeekNum the week number for starting
     * @param endWeekNum   the concluding week number
     */
    public PairScheduleTable(int membersNum, int startWeekNum, int endWeekNum) {
        this.membersNum = membersNum;
        this.createWeekNumArray(startWeekNum, endWeekNum);

        createMatrix(membersNum);
        fillMatrix(startWeekNum, weeksNumber);
    }

    private void createWeekNumArray(int statrWeekNum, int endWeekNum) {
        weekNumArray = new int[endWeekNum - statrWeekNum + 1];
        weeksNumber = weekNumArray.length;
        int tmp = statrWeekNum;
        for (int i = 0; i < weeksNumber; i++) {
            weekNumArray[i] = tmp++;
        }
    }

    private int[] getWeekNumArray() {
        return this.weekNumArray;
    }


    /**
     * Culculated amout of weeks with unique range of groups
     * log2(membersNum) = Math.log10(membersNum)/Math.log10(2) and
     * result rounded to positive infinity.
     *
     * @param membersNum the competitors number.
     * @return weeks number.
     */
    private int calcWeekNum(int membersNum) {
        return (int) Math.round(Math.log10(membersNum) / Math.log10(2));
    }

    private void createMatrix(int membersNum) {
        //created schedule table
        schedule = new String[membersNum][weeksNumber];

        //culculated groups (pairs) number
        int groupNum = (membersNum >> 1);

        //created and filled groupNameArray
        fillGoupeNameArray();
        while (groupNum >= 26) {
            fillGoupeNameArray();
            groupNum -= 26;
        }
    }

    /**
     * Returns a string representation of the scheduleMatrix.
     */
    public String printPairScheduleMartixForTest() {
        String result = "Week is: \t\t";
        for (int i = 0; i < weekNumArray.length; i++) {
            result += (weekNumArray[i] + "\t\t");
        }
        result += "\n";

        for (int i = 0; i < schedule.length; i++) {
            int num = i + 1;
            result += "Member " + num + "\t\t";
            for (int j = 0; j < schedule[i].length; j++) {
                result += String.valueOf(schedule[i][j]) + "\t\t";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Returns a string representation of the scheduleMatrix.
     */
    public String printPairScheduleMartix() {
        String result = "";
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                result += String.valueOf(schedule[i][j]) + "\t\t";
            }
            result += "\n";
        }
        return result;
    }

    private void fillMatrix(int statrWeekNum, int endWeekNum) {
        int delta = statrWeekNum;
        int columns = 0;

        while (columns < weeksNumber) {
            int index = -1;
            int d = 1;
            boolean indexChangedBack = false;
            int rows = 0;

            while (rows < schedule.length) {
                if ((schedule[rows][columns]) == null) {

                    //index prepared
                    if ((rows < delta) && (rows > 0)) {
                        index = delta - d;
                        d++;
                    } else {
                        if ((!indexChangedBack) && (rows == (2 * delta))) {
                            index = delta;
                            indexChangedBack = true;
                        } else {

                            index++;
                        }
                    }
                    //filled cells in table by index and index+delta
                    schedule[rows][columns] = groupNameArray[index];
                    if ((rows + delta) < schedule.length) schedule[rows + delta][columns] = groupNameArray[index];
                }
                rows++;
            }
            delta++;
            columns++;
        }
    }

    private void fillGoupeNameArray() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairScheduleTable that = (PairScheduleTable) o;

        if (membersNum != that.membersNum) return false;
        if (weeksNumber != that.weeksNumber) return false;
        return Arrays.deepEquals(schedule, that.schedule);

    }

    @Override
    public int hashCode() {
        int result = membersNum;
        result = 31 * result + weeksNumber;
        result = 31 * result + Arrays.deepHashCode(schedule);
        return result;
    }

    /**
     * Removing the competitor from the schedule matrix by his number
     *
     * @param competitorNum the number of competitor
     */
    public void remove(int competitorNum){
        //Do something
    }

    /**
     * Addition new competitor to the end of schedule matrix
     *
     */
    public boolean add(){
        //Do something
        return false;
    }
}
