import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class LexicalAnalyzer {
    // Data fields
    private static Scanner scanner;
    private static char nextChar;
    private static String lexeme;
    private static int charClass;
    private static int lexLen;
    private static int token;
    private static int nextToken;

    // Character classes
    private static final int LETTER = 0;
    private static final int DIGIT = 1;
    private static final int UNKNOWN = 99;

    // Token codes
    private static final int INT_LIT = 10;
    private static final int IDENT = 11;
    private static final int ASSIGN_OP = 20;
    private static final int ADD_OP = 21;
    private static final int SUB_OP = 22;
    private static final int MULT_OP = 23;
    private static final int DIV_OP = 24;
    private static final int LEFT_PAREN = 25;
    private static final int RIGHT_PAREN = 26;
    private static final int COMMA = 27;
    private static final int SEMICOLON = 28;


    // Class Methods

    /** Gets the next charater of input and determine its character class */
    private static void getChar() {
        int nextCharInt = scanner.nextInt();
        if(nextCharInt != -1){
            nextChar = (char)nextCharInt;
        }

    }
    public static void main(String[] args) throws IOException {
        // Open the input data file and process its contents
        FileInputStream inputFile = new FileInputStream("front.in");
        Scanner scanner = new Scanner(inputFile);

        getChar();
        do {
            lex();
        } while (nextToken != -1);
    }
}