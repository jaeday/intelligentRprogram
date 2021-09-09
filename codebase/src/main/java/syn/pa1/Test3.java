package syn.pa1;

import org.rosuda.REngine.Rserve.RserveException;

import syn.base.CFG;
import syn.base.Dataframe;

public class Test3 extends Test2 {

  public static void main(String[] args) throws RserveException {
    new Test3().test();
  }

  @Override
  protected Dataframe mkInEx() {
    // add you code here
    throw new RuntimeException();
  }

  @Override
  protected Dataframe mkOutEx() {
    // add you code here
    throw new RuntimeException();
  }

  @Override
  protected CFG mkCFG() {
    // add your code here
    throw new RuntimeException();
  }

}
