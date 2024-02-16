import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;

public class LexicalAnalyzer {
    // Data fields
    private static BufferedReader reader; //private static Scanner scanner;
    private static char nextChar;
    private static String lexeme;
    private static int charClass;
    private static int lexLen;
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
    private static void getChar() throws IOException {

        int nextCharInt = reader.read();
        if(nextCharInt != -1){
            nextChar = (char)nextCharInt;
            if(Character.isAlphabetic(nextChar)){ charClass = LETTER; }
            else if (Character.isDigit(nextChar)){charClass = DIGIT;}
            else {charClass = UNKNOWN;}
        } else { charClass = -1; }
    }

    /**
     * A simple lexical analyzer for arithmetic expression
     * @return
     */
    private static int lex() throws IOException {
        lexeme = "";
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            /* Parse identifiers */
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                if (lexeme.equals("int")){ nextToken = INT_LIT; }
                else {  nextToken = IDENT; }
                break;

            /* Parse integer literals */
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            /* Parentheses and operators */
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            /* EOF */
            case -1:
                nextToken = -1;
                lexeme = "EOF";
                break;
        } /* End of switch */
        System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken, lexeme);
        return nextToken;
    } /* End of function lex */

    /** A function to call getChar() until it returns a non-Whitespace character */
    private static void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar))
            getChar();
    }

    /** A function to add nextChar to lexeme */
    private static void addChar() {
        if (lexLen <= 98) {
            lexeme += nextChar;
            lexLen++;
        } else {
            System.err.println("Error - lexeme is too long");
        }
    }

    /** A function to lookup operators and parentheses
     * @param ch: the target to look up
     * @return the token
     */
    private static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            case ',':
                addChar();
                nextToken = COMMA;
                break;
            case ';':
                addChar();
                nextToken = SEMICOLON;
                break;
            default:
                addChar();
                nextToken = -1;
                break;
        }
        return nextToken;
    }
    public static void main(String[] args) throws IOException {
        // Open the input data file and process its contents
        FileReader inputFile = new FileReader("front.in");
        reader = new BufferedReader(inputFile);
        getChar();
        do {
            lex();
        } while (nextToken != -1);

        // Closing the files
        reader.close();
        inputFile.close();

    }
}