package sample.classes;

/**
 * Created by User on 10.12.2017.
 */
public class CycleCode {
    private String correctedPolynome;
    private String correctedString;

    /**
     * Сложение по модулю два
     * @param a - первый символ
     * @param b - второй символ
     * @return - результат
     */
    private int twoAdd(char a, char b)
    {
        int c = Character.digit(a, 2)^Character.digit(b, 2);
        return c;
    }

    /**
     * Функция обрезания нулей в начале строки
     * @param line - строка
     * @return строка с обрезанными нулями
     */
    String cutLine(String line)
    {
        int end = line.length();
        int check=0;
        for(int i=0; i<end;i++){
            if(line.charAt(i)=='0')
                check++;
            else
                break;
        }
        line = line.substring(check, line.length());
        return line;
    }

    /**
     * Вес строки. Подсчёт единиц
     * @param line строка
     * @return вес строки
     */
    private int weightBalance(String line)
    {
        int weight=0;
        for(int i=0; i<line.length();i++){
            if(line.charAt(i)=='1')
                weight++;
        }
        return weight;
    }

    /**
     * Сдвиг кода влево
     * @param line - строка кода
     * @return сдвинутый код
     */
    String shiftCodeLeft(String line)
    {
        String shiftLine = line+line.substring(0,1);
        line = shiftLine.substring(1,line.length()+1);
        return line;
    }

    /**
     * Сдвиг кода вправо
     * @param line - строка кода
     * @return сдвинутый код
     */
    String shiftCodeRight(String line)
    {
        String shiftLine = line.substring(line.length()-1,line.length())+line;
        line = shiftLine.substring(0,line.length());
        return line;
    }

    /**
     * Деление кодовых строк в столбик
     * @param dividend - делимое
     * @param divider - делитель
     * @return результат
     */
    private String dividet(String dividend, String divider)
    {
        String divide;
        dividend = cutLine(dividend);
        String S1 = dividend.substring(0, divider.length());
        String S2 = divider;
        int shift = dividend.length() - divider.length();
        while(shift>=0) {
            divide="";
            for (int i = 0; i < S1.length(); i++) {
                divide += Integer.toBinaryString(twoAdd(S1.charAt(i), S2.charAt(i)));
            }
            divide = cutLine(divide);
            int add = divider.length() - divide.length();
            S1=divide;
            try {
                S1 += dividend.substring(dividend.length() - shift, dividend.length() - shift + add);
            }catch (Exception e) {
                S1=S1+dividend.substring(dividend.length()-shift, dividend.length());
            }
            shift -=add;
        }
        return S1;
    }

    /**
     * Рекурсия. Переворачиваем строку
     * @param s - строка
     * @return инвертированная строка
     */
    private String recursiveReverse(String s) {
        if ((null == s) || (s.length() <= 1))
            return s;
        return recursiveReverse(s.substring(1)) + s.charAt(0);
    }

    /**
     * Сложение по модулю два
     * @param line1 - первая строка
     * @param line2 - вторая строка
     * @return результат
     */
    String additionModuleTwo(String line1, String line2)
    {
        String line ="";
        while(line2.length()<line1.length())
            line2 = "0" + line2;
        while(line1.length()<line2.length())
            line1 = "0" + line1;
        for(int i = line2.length()-1; i>=0; i--)
            line += Integer.toBinaryString(twoAdd(line1.charAt(i), line2.charAt(i)));
        return recursiveReverse(line);
    }

    /**
     * Движок класса. Из него вызываются все методы
     * @param errorPolynome - код ошибочного полинома
     * @param generatedPolynome - код образующего полинома
     */
    public void driver(String errorPolynome,  String generatedPolynome) {
        int errlen=0;
        generatedPolynome = cutLine(generatedPolynome);
        correctedString = " Делим " + errorPolynome + " на образующий полином " + generatedPolynome + "\n";
        String S =dividet(errorPolynome, generatedPolynome);
        correctedString += " Ответ: " + S + "\n";
        int weight=weightBalance(S);
        correctedString += " Вес: " + weight + "\n";
        int step = 0;
        correctedString += " =======================================\n";
        while(weight>1){
            correctedString += " ШАГ " + step + "\n";
            errorPolynome = shiftCodeLeft(errorPolynome);
            correctedString += " Сдвигаем код влево " + errorPolynome + "\n";
            S =dividet(errorPolynome, generatedPolynome);
            correctedString += " Делим на образующий плином " + generatedPolynome + "\n";
            correctedString += " Ответ: " + S + "\n";
            weight=weightBalance(S);
            correctedString += " Вес: " + weight + "\n";
            step++;
            correctedString += " =======================================\n";
            errlen++;
            if(errlen>15) break;
        }
        if(errlen<15) {
            errorPolynome = additionModuleTwo(errorPolynome, S);
            correctedString += " Складываем по модулю два: " + errorPolynome + "\n";
            for (int i = 0; i < step; i++)
                errorPolynome = shiftCodeRight(errorPolynome);
            correctedString += " Сдвигаем вправо " + step + " раз(а)\n";
            correctedPolynome = errorPolynome;
            correctedString += " Ответ: " + correctedPolynome + "\n";
        }else correctedString += " Превышено количество итераций!\n";
    }

    /**
     * Возвращаем исправленный код
     * @return - исправленный код
     */
    public String getCorrectedPolynome()
    {
        return correctedPolynome;
    }

    /**
     * Возвращаем ход работы
     * @return - ход работы
     */
    public String getCorrectedString()
    {
        return correctedString;
    }
}