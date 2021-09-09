package syn.pa0;

import org.rosuda.REngine.Rserve.RserveException;

//Added libraries for Dataframe
import syn.base.Dataframe;
import syn.base.Interpreter;

public class Test1 extends Test0 {

  public static void main(String[] args) throws RserveException {
    new Test1().test();
  }

  @Override
  protected void test() throws RserveException {
    // add your code here
    // create input dataframe
    Dataframe input = Dataframe.mkDataframe(new String[] { "id", "name", "key" },
        new Object[][] { { "1", "2", "3","4" }, { "Mark", "John", "Mary", "Joe" }, { "0.94/Y", "0.85/Y", "0.22/N", "0.11/N" }});
    System.out.println("Input in R: \n" + input.toR() + "\n");

    
    // create R program
    String rprog = "separate(x, key, into=c(\"efficiency\", \"rehire\"), sep = \"\\\\/\")";
    System.out.println("R program: \n" + rprog + "\n");

    // run R program on the input dataframe
    Interpreter interp = new Interpreter();
    Dataframe output = interp.eval(rprog, input);
    System.out.println("Produced output in R: \n" + output.toR() + "\n");

    // check the output against the desired output
    Dataframe output1 = Dataframe.mkDataframe(new String[] { "id", "name", "efficiency", "rehire" }, new Object[][] {
        { "1", "2", "3", "4"}, { "Mark", "John", "Mary", "Joe" }, { "0.94", "0.85", "0.22", "0.11" }, {"Y", "Y", "N", "N"} });
    System.out.println("Desired output in R: \n" + output1.toR() + "\n");
    System.out.println("Desired output is the same as the produced output: \n" + output1.equals(output) + "\n");

  }
  

}
