package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 15.11.2017.
 */
public class MyException extends Exception{

    public String getException(String v1) throws ErrorException {
        Pattern p = Pattern.compile("[10]+");
        Matcher m = p.matcher(v1);
        String v2 = v1.replaceAll("0", "");
        if(v2.length() >= 13)
            throw new ErrorException("Слишком большое число!!", v1);
        if (!m.matches())
           throw new ErrorException("Неверно введён код!", v1);
        return v1;
    }

    public String getExceptionPolynome(String v1) throws ErrorException {
        Pattern p = Pattern.compile("[10]+");
        Matcher m = p.matcher(v1);
        if(v1.length() > 8)
            throw new ErrorException("Слишком большое число!!", v1);
        if (!m.matches())
            throw new ErrorException("Неверно введён код!", v1);
        return v1;
    }
}

class ErrorException extends Exception
{
    private String r1;
    public String getText(){return r1;}

    public ErrorException (String message, String v1)
    {
        super(message);
        r1=v1;
    }

}