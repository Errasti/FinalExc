package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        generateLine();
    }

    /**
     * Метод записывающий сгенерированную строку в файл. Ввод доступен только на латинице.
     */
    public static void generateLine(){
        String path;
        String[] input = null;
        while (input == null){
            try {
                input = userInput();
            } catch (MyArraySizeException e) {
                System.out.printf("%s.Внесено %d поля(ей), а нужно %d",e.getMessage(), e.getInputSize(),
                        MyArraySizeException.correctSize);
            } catch (MyBirthDayException | MyPhoneException | MyGenderException e){
                System.out.println(e.getMessage());
            }
        }
        path = input[0] + ".txt";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length; i++){
            sb.append(input[i] + " ");
        }
        sb.append("\n");
        System.out.println(path);
        System.out.println(sb);
        try(FileWriter writer = new FileWriter(path, true)){
            writer.write(sb.toString());
            writer.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Метод проверяющий ввод пользователя по количеству введенных полей и значениям даты, номера телефона, и пола
     * @return массив с данными о человеке
     * @throws MyArraySizeException - исключение в случае переизбытка или недостатка информации
     */
    public static String[] userInput() throws MyArraySizeException{
        String[] userData = null;
        Scanner scan = new Scanner(System.in);
        System.out.println(TxtData.inputInvite);
        String[] inputData = scan.nextLine().split(" ");
        if (inputData.length != MyArraySizeException.correctSize){
            throw new MyArraySizeException(TxtData.incorrectData, inputData.length);
        } else {
            userData = inputData.clone();
        }
        Pattern patternDate = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)");
        Matcher matcher = patternDate.matcher(userData[3]);
        if (!matcher.find()){
            throw new MyBirthDayException(TxtData.incorrectDate);
        }
        Pattern patternPhone = Pattern.compile("((\\+?7)(\\d{10}))$");
        Matcher matcherPhone = patternPhone.matcher(userData[4]);
        if (!matcherPhone.find()){
            throw new MyPhoneException(TxtData.incorrectPhone);
        }
        if (!userData[5].toLowerCase().equals("f") && !userData[5].toLowerCase().equals("m")){
            throw new MyGenderException(TxtData.incorrectGender);
        }
        return userData;
    }

}

/**
 * Собственные классы исключений
 */
class MyArraySizeException extends Exception{

    public static final int correctSize = 6;
    private final int inputSize;

    MyArraySizeException(int inputSize) {
        this.inputSize = inputSize;
    }

    public MyArraySizeException(String message, int inputSize) {
        super(message);
        this.inputSize = inputSize;
    }

    public int getInputSize() {
        return inputSize;
    }
}

class MyBirthDayException extends InputMismatchException{

    public MyBirthDayException(String s) {
        super(s);
    }

}


class MyPhoneException extends InputMismatchException{
    public MyPhoneException(String s) {
        super(s);
    }
}


class MyGenderException extends InputMismatchException{
    public MyGenderException(String s) {
        super(s);
    }
}
