/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nmf;

import tml.corpus.CorpusParameters;
import tml.vectorspace.TermWeighting.GlobalWeight;
import tml.vectorspace.TermWeighting.LocalWeight;
import tml.corpus.SearchResultsCorpus;
import tml.storage.Repository;
import tml.vectorspace.operations.PassagesSimilarity;

/**
 *
 *
 *
 * @author Holus
 */
public class Nmf {

    Variables var;

    public Nmf() throws Exception {
        var = new Variables();
        // trainData("data/Answer");
    }

    /**
     * @param args the command line arguments
     */
    public String trainData(String ans) throws Exception {

        Repository repository = new Repository("C:/tml-3.2/tml/corpora/myrepo");
        repository.cleanStorage("C:/tml-3.2/tml/corpora/myrepo");
        repository.addDocumentsInFolder(ans);
        var.setCollection(testData());
        return testData();
        //System.out.println(testData());
        // TODO code application logic here
    }

    public String testData() throws Exception {
        Repository repository = new Repository("C:/tml-3.2/tml/corpora/myrepo");
        SearchResultsCorpus corpus = new SearchResultsCorpus("type:document");
        corpus.getParameters().setTermSelectionThreshold(0);
       
        
        corpus.getParameters().setDimensionalityReduction(CorpusParameters.DimensionalityReduction.NO);
        
        corpus.getParameters().setDimensionalityReductionThreshold(50);
        corpus.getParameters().setTermWeightGlobal(GlobalWeight.None);
        corpus.getParameters().setTermWeightLocal(LocalWeight.TF);
        corpus.load(repository);
        // System.out.println("Corpus Loaded and Semantic space Calculated");
        // System.out.println("Total documents: "+corpus.getPassages().length);

       // System.out.println(corpus.parametersSummary());
        PassagesSimilarity distances = new PassagesSimilarity();
        distances.setCorpus(corpus);
        distances.start();
        String a = "";
        for (String str : corpus.getPassages()) {
            a += str + "\n";
        }
        var.setAnswer(a);
         a = "H\n";
        for (double dob[] : corpus.getTermDocMatrix().getArray()) {
            for (double dob1 : dob) {
                a += dob1 + " ";
            }
            a += "\n";
        }
        var.setA(a);
//          a = "S\n";
//        for (double dob[] : corpus.getTermDocMatrix().svd().getS().getArray()) {
//            for (double dob1 : dob) {
//                a += dob1 + " ";
//            }
//            a += "\n";
//        }
//        var.setE(a);
        
        var.setWordFreq(corpus.printFrequencies());
        a = "V\n";
        for (double dob[] : corpus.getTermDocMatrix().svd().getU().getArray()) {
            for (double dob1 : dob) {
                a += dob1 + " ";
            }
            a += "\n";
        }
        var.setV(a);
        a = "E\n";
        for (double dob[] : corpus.getTermDocMatrix().svd().getU().transpose().getArray()) {
            for (double dob1 : dob) {
                a += dob1 + " ";
            }
            a += "\n";
        }
        var.setE(a);
        a = "W\n";
        for (double dob[] : corpus.getTermDocMatrix().svd().getV().getArray()) {
            for (double dob1 : dob) {
                a += dob1 + " ";
            }
            a += "\n";
        }
        var.setU(a);
      
       
        a = "R\n" + distances.getResultsCSVString();
        String a1[] = a.split("\n");
        String y = "";
        for (String string : a1) {
            if (string.startsWith("lecturer")) {
                y += string+"\n";
            }
        }
        var.setRel("R\n" +y);
        // distances.printResults(); 
        return var.getAnswer() + "\n" + var.getWordFreq() + "\n"  + var.getU() + "\n"+ var.getA() +  "\n" + var.getV()
                + "\n" + var.getRel();
    }

//    public String rep() throws Exception {
//        Repository repository = new Repository("C:/tml-3.2/tml/corpora/myrepo");
//        SearchResultsCorpus corpus = new SearchResultsCorpus("type:document");
//        corpus.load(repository);
//        String a = "";
//        for (String str : corpus.getPassages()) {
//            a += str + "\n";
//        }
//        var.setAnswer(a);
//        return a;
//    }

    public static void main(String[] args) throws Exception {
        new Nmf();
    }
}
