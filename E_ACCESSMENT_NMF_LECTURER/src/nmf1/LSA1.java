/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nmf1;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Oginni
 */
public class LSA1 {

    final Charset ENCODING = StandardCharsets.UTF_8;
    static String n1;
    static String n2;
    String stopWordLarge = "data/english-stop-words-large.txt";
    String stopWordSmall = "data/english-stop-words-small.txt";
    String compoundWord = "data/compound.txt";
    String analogies = "data/corpus1.txt";
    int lsa;
    int nmf;
    int total;
    static String algo;
    int m = 0;
    int wordlength = 0;
    String[][] dimN1;
    String[][] dimN2;
    float[][] matrixN1;
    float[][] matrixN2;
    String wordMatrix2 = "";
    String wordMatrix1 = "";
    String numMatrix2 = "";
    String numMatrix1 = "";
    String redMatrix2 = "";
    String redMatrix1 = "";
    String compoundList = "";
    String corpusList = "";
    String matchWord = "";
    String punt = "";
    String stopword = "";
    String lsaProjectile = "";
    String nmfProjectile = "";
    Variables1 var = new Variables1();
    Vector<String> doc_report = new Vector<String>();
    Vector<String> doc_report1 = new Vector<String>();

    public LSA1(String algo, String n1, String n2) throws IOException {
        lsa = 0;
        nmf = 0;
        total = 0;
        algo = "";
        m = 0;
        wordlength = 0;
        dimN1 = null;
        dimN2 = null;
        matrixN1 = null;
        matrixN2 = null;
        wordMatrix2 = "";
        wordMatrix1 = "";
        numMatrix2 = "";
        numMatrix1 = "";
        redMatrix2 = "";
        redMatrix1 = "";
        compoundList = "";
        corpusList = "";
        matchWord = "";
        punt = "";
        stopword = "";
        lsaProjectile = "";
        nmfProjectile = "";

        this.algo = algo;
        this.n1 = " " + n1 + " ";
        this.n2 = " " + n2 + " ";
        compileCompound(compoundWord);
        stripStopWords(stopWordLarge);
        if (algo.equalsIgnoreCase("nmf")) {
            StripRepeatedWord();
        }
        MatrixMaker(this.n1.trim(), this.n2.trim());
        total = this.n1.trim().split(" ").length;
        compileResult();
        returnResult();
        System.out.flush();
    }

    public String lsa(String algo, String n1, String n2) throws IOException {
        lsa = 0;
        nmf = 0;
        total = 0;
        algo = "";
        m = 0;
        wordlength = 0;
        dimN1 = null;
        dimN2 = null;
        matrixN1 = null;
        matrixN2 = null;
        wordMatrix2 = "";
        wordMatrix1 = "";
        numMatrix2 = "";
        numMatrix1 = "";
        redMatrix2 = "";
        redMatrix1 = "";
        compoundList = "";
        corpusList = "";
        matchWord = "";
        punt = "";
        stopword = "";
        lsaProjectile = "";
        nmfProjectile = "";

        this.algo = "lsa";
        this.n1 = " " + n1 + " ";
        this.n2 = " " + n2 + " ";
        compileCompound(compoundWord);
        stripStopWords(stopWordLarge);
        if (algo.equalsIgnoreCase("nmf")) {
            StripRepeatedWord();
        }
        MatrixMaker(this.n1.trim(), this.n2.trim());
        total = this.n1.trim().split(" ").length;
        compileResult();
        returnResult();
        System.out.flush();
        return returnResult();
    }

    public String nmf(String algo, String n1, String n2) throws IOException {
        lsa = 0;
        nmf = 0;
        total = 0;
        algo = "";
        m = 0;
        wordlength = 0;
        dimN1 = null;
        dimN2 = null;
        matrixN1 = null;
        matrixN2 = null;
        wordMatrix2 = "";
        wordMatrix1 = "";
        numMatrix2 = "";
        numMatrix1 = "";
        redMatrix2 = "";
        redMatrix1 = "";
        compoundList = "";
        corpusList = "";
        matchWord = "";
        punt = "";
        stopword = "";
        lsaProjectile = "";
        nmfProjectile = "";

        this.algo = "nmf";
        this.n1 = " " + n1 + " ";
        this.n2 = " " + n2 + " ";
        compileCompound(compoundWord);
        stripStopWords(stopWordLarge);
        if (algo.equalsIgnoreCase("nmf")) {
            StripRepeatedWord();
        }
        MatrixMaker(this.n1.trim(), this.n2.trim());
        total = this.n1.trim().split(" ").length;
        compileResult();
        returnResult();
        System.out.flush();
        return returnResult();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        algo = "nmf";
        //       n1 = "ingredient of crescent roll".toLowerCase();
//        n2 = "roll music drum demonstration composition bread ingredient crescent recipe dough rock".toLowerCase();
//        n1 = "Recipe for White Bread".toLowerCase();
//        n2 = "Recipe for White Bread".toLowerCase();
        n1 = "Computer science is the study of computer hardware, and it software for the purpose, of creating network".toLowerCase();
        n2 = "Computer science deals with the study of how data is being process, using hardware and software".toLowerCase();

        new LSA1(algo, n1, n2);
    }

    public void MatrixMaker(String n1, String n2) {

        String[] arrN1 = n1.split(",");
        String[] arrN2 = n2.split(",");
        m = Math.max(arrN1.length, arrN2.length);
        prepareLocation(m);
        String word2[];
        String word1[];
        for (int i = 0; i < m; i++) {
            if (arrN1.length > i) {

                word1 = arrN1[i].trim().split(" ");
                //  System.out.println("1 :->"+word1.length);
                dimN1[i] = word1;
                if (word1.length > wordlength) {
                    wordlength = word1.length;
                }
            }
            if (arrN2.length > i) {
                word2 = arrN2[i].trim().split(" ");
                //  System.out.println("2 :->"+word2.length);
                dimN2[i] = word2;
                if (word2.length > wordlength) {
                    wordlength = word2.length;
                }
            }
        }

        accessArray();
    }

    void prepareLocation(int m) {
        dimN1 = new String[m][m];
        dimN2 = new String[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                dimN1[i][j] = "0->";
                dimN2[i][j] = "0->";
            }
        }

    }

    private void accessArray() {
        int dimension = Math.max(m, wordlength);
        matrixN1 = new float[dimension][dimension];
        matrixN2 = new float[dimension][dimension];
        //System.out.println(wordlength);
        //System.out.println(wordlength);
        for (int i = 0; i <= wordlength; i++) {
            for (int j = 0; j <= m; j++) {
                // System.out.println();
                try {
                    stringToMatrix(dimN1[j][i], "", j, i);
                    //  System.out.print(dimN1[j][i] + "\t");
                } catch (ArrayIndexOutOfBoundsException e) {
                    try {
                        matrixN1[j][i] = -1;
                        //  System.out.println(1 + "[" + j + "][" + i + "]===>" + matrixN1[j][i]);
                        //  System.out.print("empty:  " + matrixN1[j][i] + "\t");
                    } catch (ArrayIndexOutOfBoundsException t) {
                        //   t.printStackTrace();
                    }
                    //  System.out.print("\t");
                    // continue;
                }

                try {
                    stringToMatrix("", dimN2[j][i], j, i);
                    // System.out.print(dimN2[j][i] + "\t");
                } catch (ArrayIndexOutOfBoundsException e) {
                    try {
                        matrixN2[j][i] = -1;

                        //  System.out.println(2 + "[" + j + "][" + i + "]===>" + matrixN2[j][i]);
                        //  System.out.print("empty:  " + matrixN2[j][i] + "\t");
                    } catch (ArrayIndexOutOfBoundsException t) {
                        //  t.printStackTrace();
                    }
                    //System.out.print("\t");
                    //continue;
                }
            }
        }
        for (String[] str : dimN1) {
            for (String str2 : str) {
                trainSimilarity2(str2);
            }
        }
        for (String[] str : dimN2) {
            for (String str2 : str) {
                trainSimilarity(str2);
            }
        }
    }

    void stringToMatrix(String n1, String n2, int i, int j) {
        // System.out.println("");
        try {
            n1 = stripeSpecialChar(n1, i, j, 1);
            n2 = stripeSpecialChar(n2, i, j, 2);
            if (!n1.isEmpty()) {
                matrixN1[j][i] = stringToInt(n1);
                //System.out.println(1 + "[" + i + "][" + j + "] " + n1 + "===>" + matrixN1[j][i]);

            } else {
                if (!n2.isEmpty()) {
                    matrixN2[j][i] = stringToInt(n2);
                    //  System.out.println(2 + "[" + i + "][" + j + "] " + n2 + "===>" + matrixN1[j][i]);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int stringToInt(String n1) {
        wordlength = -1;
        char[] a = n1.toCharArray();
        for (char c : a) {
            wordlength += c;
        }
        //System.out.print(n1 + ":  " + wordlength + "\t");
        return wordlength;
    }

    void writeFile(String aFileName, String text) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(aFileName, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String readFile(String aFileName, String testString) throws IOException {
        Path path = Paths.get(aFileName);
        String eachLine = "";
        try {
            Scanner scanner = new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()) {
                eachLine = scanner.nextLine();
                if (eachLine.equalsIgnoreCase(testString) || (eachLine + ",").equalsIgnoreCase(testString) || (eachLine + ".").equalsIgnoreCase(testString)) {
                    stopword += eachLine + "   ";
                    return "";
                }
            }
        } catch (Exception e) {
            return testString;
        }
        return testString;
    }

    String stripStopWords(String path) throws IOException {
        for (String str : n1.split(" ")) {
            n1 = n1.replaceAll(" " + str + " ", " " + readFile(path, str) + " ");
            n1 = n1.replaceAll("  ", " ");
            n1 = n1.replaceAll(" . ", " ");
            n1 = n1.replaceAll(" , ", ",");
            n2 = n2.replaceAll(" " + str + " ", " " + readFile(path, str) + " ");
            n2 = n2.replaceAll("  ", " ");
            n2 = n2.replaceAll(" . ", " ");
            n2 = n2.replaceAll(" , ", ",");
        }
        for (String str : n2.split(" ")) {
            n2 = n2.replaceAll(" " + str + " ", " " + readFile(path, str) + " ");
            n2 = n2.replaceAll("  ", " ");
            n2 = n2.replaceAll(" . ", " ");
            n2 = n2.replaceAll(" , ", ",");
        }

        return null;
    }

    private void compileCompound(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        String eachLine = "";
        String WordN1[] = n1.split(" ");
        String WordN2[] = n2.split(" ");
        try {
            Scanner scanner = new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()) {
                eachLine = scanner.nextLine();

                for (int i = 0; i < n1.split(" ").length - 1; i++) {
                    if (eachLine.equalsIgnoreCase(WordN1[i] + WordN1[i + 1])) {
                        n1 = n1.replaceAll(WordN1[i] + " " + WordN1[i + 1], eachLine);
                        compoundList += eachLine + "\t";
                    }
                }
                for (int i = 0; i < n2.split(" ").length - 1; i++) {
                    if (eachLine.equalsIgnoreCase(WordN2[i] + WordN2[i + 1])) {
                        n2 = n2.replaceAll(WordN2[i] + " " + WordN2[i + 1], eachLine);
                        compoundList += eachLine + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String stripeSpecialChar(String stripeStr, int i, int j, int wch) {
        String specialChar = " .,!?;:%(){}[]\n";
        for (char str : specialChar.toCharArray()) {
            if (stripeStr.endsWith(str + "")) {
                punt += str + "  ";
                stripeStr = stripeStr.replace(str + "", "");
            }
        }
        return stripeStr;
    }

//    private  void trainSimilarity(String trim) {
//        CosineAlgo cos = new CosineAlgo();
//        // System.out.println(cos.Cosine_Similarity_Score(n1, n2));
//        for (String[] str : dimN1) {
//            for (String str2 : str) {
//                if (cos.Cosine_Similarity_Score(str2, trim) > 0) {
//                    System.out.println(trim + "=>>>>" + cos.Cosine_Similarity_Score(str2, trim));
//                }
//
//            }
//        }
//
//    }
    private void trainSimilarity2(String trim) {
        CosineAlgo cos = new CosineAlgo();
        for (String[] str : dimN2) {
            for (String str2 : str) {
                if (cos.Cosine_Similarity_Score(str2, trim) > 0) {
                    // System.out.println(trim + "LSA =>>>>" + cos.Cosine_Similarity_Score(str2, trim));

                    if (matchWord.contains(trim + " :") && !trim.contains("0->")) {
                        matchWord = matchWord.replace(trim + " : " + 1.0 + " : 0.0 : ", trim + " : " + 1.0 + " : " + cos.Cosine_Similarity_Score(str2, trim) + ": ");
                    } else {
                        matchWord += trim + " : " + 1.0 + " : " + cos.Cosine_Similarity_Score(str2, trim) + ": " + "\n";
                    }
                    lsa++;
                    nmf++;
                    break;
                } else if (algo.equalsIgnoreCase("nmf")) {
                    if (testCorpus(analogies, str2, trim) > 0) {
                        //   System.out.println(trim + "NMF =>>>>" + testCorpus(analogies, str2, trim));

                        if (matchWord.contains(trim + " :") && !trim.contains("0->")) {
                            matchWord = matchWord.replaceAll(trim + " : " + 1.0 + " : 0.0 : ", trim + " : " + 1.0 + " : " + cos.Cosine_Similarity_Score(str2, trim) + ": ");
                        } else {
                            matchWord += trim + " : " + 1.0 + " : " + cos.Cosine_Similarity_Score(str2, trim) + ": " + "\n";
                        }
                        nmf++;
                        break;
                    }
                } else {
                    if (!matchWord.contains(trim + " :") && !trim.contains("0->")) {
                        matchWord += trim + " : " + 1.0 + " : 0.0 : " + "\n";
                    }

                }

            }
        }
    }

    private void trainSimilarity(String trim) {
        CosineAlgo cos = new CosineAlgo();
        for (String[] str : dimN1) {
            for (String str2 : str) {

                if (!matchWord.contains(trim + " :") && !trim.contains("0->")) {
                    matchWord += trim + " : " + 0.0 + " : 1.0 : " + "\n";
                }

            }
        }
    }

    float testCorpus(String aFileName, String wordA, String wordB) {
        Path path = Paths.get(aFileName);
        String eachLine = "";
        try {
            Scanner scanner = new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()) {
                eachLine = scanner.nextLine() + ":";
                if (eachLine.contains(wordA + ":") && eachLine.contains(wordB + ":")) {
                    corpusList += eachLine + "\n";
                    return 1;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void StripRepeatedWord() {
        for (String str : n1.split(" ")) {
            n1 = n1.replaceAll(str, "") + str + " ";
            //  n2 = n2.replaceAll(str + " ", "") + str + " ";
        }
        for (String str : n2.split(" ")) {
            n2 = n2.replaceAll(str, "") + str + " ";
        }
        n2 = n2.trim();
        n1 = n1.trim();
    }

    private void compileResult() {
        for (String[] strings : dimN1) {
            for (String st : strings) {
                wordMatrix1 += st + "\n";
            }
        }
        for (String[] strings : dimN2) {
            for (String st : strings) {
                wordMatrix2 += st + "\n";
            }
        }
        for (float[] in : matrixN1) {
            numMatrix1 += "\n";
            for (float st : in) {
                numMatrix1 += st/1000 + "\n";
                if (st > 0) {
                    redMatrix1 += st/1000 + "\n";
                }
            }
            // numMatrix1 += "\n";
        }
        for (float[] in : matrixN2) {
            // numMatrix2 += "\n";
            for (float st : in) {
                numMatrix2 = st + "\n" + numMatrix2;
                if (st > 0) {
                    redMatrix2 += st/1000 + "\n";
                }
            }
            // numMatrix2 += "\n";
        }
        System.out.println(lsa);
        System.out.println(nmf);
        System.out.println(total);

        lsaProjectile += "[" + (lsa / (double) total) + ",";
        lsaProjectile += (total / (double) lsa) + "]";
        nmfProjectile += "[" + (nmf / (double) total) + ",";
        nmfProjectile += (total / (double) nmf) + "]";
//        System.out.println(nmfProjectile);
//        System.out.println(lsaProjectile);
        System.out.println(returnResult());

    }

    private String returnResult() {
        String combineResult = "\n\n";

        combineResult += "COMPOUND WORD LIST \n";
        combineResult += compoundList + "\n";
        var.setCompoundList(compoundList);
        combineResult += "\nSTOP WORD LIST \n";
        combineResult += stopword + "\n";
        var.setStopword(stopword);
        if (algo.equalsIgnoreCase("nmf")) {
            combineResult += "\nSTRIPPED SYMBOLIC CHARACTER \n";
            combineResult += punt + "\n";
            var.setPunt(punt);
        }
        combineResult += "\nbefore weighting DOC1 DOC2\n";
          combineResult += matchWord + "\n";
        combineResult += "DOC 1\n";
        combineResult += wordMatrix1 + "\n";
        var.setWordMatrix1(wordMatrix1);
        combineResult += "DOC 2\n";
        wordMatrix2=wordMatrix2.replaceAll("0->\n", "");
        combineResult += wordMatrix2 + "\n";
        var.setWordMatrix2(wordMatrix2);
        combineResult += "\nORIGINAL VALUE MATRIX \n";
        combineResult += "\t\t" + numMatrix1 + "\n";
        var.setNumMatrix1(numMatrix1);
        combineResult += numMatrix2 + "\n";
        var.setNumMatrix2(numMatrix2);
        combineResult += "\nafter weighting DOC1 DOC2\n";
        combineResult += redMatrix1 + "\n";
        var.setRedMatrix1(redMatrix1);
        combineResult += redMatrix2 + "\n";
        var.setRedMatrix1(redMatrix1);
        combineResult += "\nCORPUS LIST \n";
        combineResult += corpusList + "\n";
        var.setCorpusList(corpusList);
        combineResult += "\nWORD MATCH LIST \n";
        combineResult += matchWord + "\n";
        var.setMatchWord(matchWord);

        if (algo.equalsIgnoreCase("nmf")) {
            combineResult += "\nNMF PROJECTILE \n";
            combineResult += nmfProjectile + "\n";
            var.setNmfProjectile(nmfProjectile);
        } else {
            combineResult += "\nLSA PROJECTILE \n";
            combineResult += lsaProjectile + "\n";
            var.setLsaProjectile(lsaProjectile);
        }
        var.setCombineString(combineResult);
       //  System.out.println(combineResult);
        return combineResult;

    }

    String readFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        String eachLine = "";
        try {
            Scanner scanner = new Scanner(path, ENCODING.name());

            while (scanner.hasNextLine()) {
                eachLine += scanner.nextLine();
            }
        } catch (Exception e) {
        //    e.printStackTrace();
        }
        return eachLine;
    }
}
