package sample;

public class Calculator {


    public long add(long... operands){

        long result = 0;
        for (long operand : operands) {
            result += operand;
        }
        return result;
    }


}
