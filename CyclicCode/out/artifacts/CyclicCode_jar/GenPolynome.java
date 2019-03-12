package sample.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 18.12.2017.
 */
public class GenPolynome {
    List<String> genPolynomes = new ArrayList<>();
    List<String> genPolynomes1 = new ArrayList<>();
    List<String> polynomes = new ArrayList<>();
    List<String> cyclePolynomes = new ArrayList<>();
    CycleCode cc = new CycleCode();

    private boolean compare(String line, List<String> lines)
    {
        for(int i=0; i<lines.size(); i++)
        {
            if(line.equals(lines.get(i)))
                return true;
        }
        return false;
    }

    public void driver(String errPolynome)
    {
        errPolynome = cc.cutLine(errPolynome);
        int matrixSize = errPolynome.length();
        String nullLine = "0";
        /**
         * Формируем первые комбинации
         */
        for(int i=0;i<matrixSize-1;i++)
            errPolynome+="0";
        for(int i=0;i<errPolynome.length()-1;i++)
            nullLine+="0";

        for(int i=0;i<matrixSize;i++)
        {
            genPolynomes.add(errPolynome);
            errPolynome = cc.shiftCodeRight(errPolynome);
        }
        polynomes.addAll(genPolynomes);
        genPolynomes1.addAll(genPolynomes);

        /**
         * Складываем по модулю два и ищем все комбинации
         */
        for(int k=0; k<matrixSize;k++) {
            for (int i = 0; i < genPolynomes.size(); i++) {
                for (int j = 0; j < genPolynomes1.size(); j++) {
                    String line = cc.additionModuleTwo(genPolynomes.get(i), genPolynomes1.get(j));
                    if (!compare(line, polynomes) && !line.equals(nullLine)) {
                        polynomes.add(line);
                        cyclePolynomes.add(line);
                    }
                }
            }
            genPolynomes1.clear();
            genPolynomes1.addAll(cyclePolynomes);
            cyclePolynomes.clear();
        }
    }

    public List<String> getPolynomes(){return polynomes;}
}
