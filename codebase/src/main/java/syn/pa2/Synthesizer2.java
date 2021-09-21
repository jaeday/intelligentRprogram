package syn.pa2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

import org.rosuda.REngine.Rserve.RserveException;

import syn.base.AST;
import syn.base.ASTNode;
import syn.base.CFG;
import syn.base.Dataframe;
import syn.base.Production;
import syn.pa1.Synthesizer1;

public class Synthesizer2 extends Synthesizer1 {

  public Synthesizer2(CFG cfg) throws RserveException {
    super(cfg);
    createSpecs();
  }

  // this counter counts the number of attempts for pruning
  public int attmptCounter = 0;
  // this counter counts the actual number of pruned (partial) programs
  public int prunedCounter = 0;

  public AST run(Dataframe inEx, Dataframe outEx) {

    long start = System.currentTimeMillis();

    LinkedList<AST> worklist = new LinkedList<>();

    worklist.add(mkInitialAST());

    AST ret = null;

    while (!worklist.isEmpty()) {

      iterCounter++;

      AST ast = worklist.removeFirst();

      ASTNode openNode = selectOpenNode(ast);

      if (openNode == null) {
        Dataframe output = interp.eval(ast, inEx);
        if (outEx.equals(output)) {
          ret = ast;
          break;
        }
      } else {
        for (Production prod : cfg.getProductions(openNode.getSymbol())) {
          AST expanded = ast.expand(openNode, prod);
          if (expanded.numOfOperators() > bound) {
            continue;
          }
          if (attemptToPrune(expanded)) {
            attmptCounter++;
            if (prune(expanded, inEx, outEx)) {
              prunedCounter++;
              continue;
            }
          }
          worklist.add(expanded);
        }
      }
    }

    long end = System.currentTimeMillis();
    runTime = (int) (end - start) / 1000;

    return ret;
  }

  protected Context ctx = new Context();
  // # input columns
  protected IntExpr xin = ctx.mkIntConst("xin");
  // # input rows
  protected IntExpr yin = ctx.mkIntConst("yin");
  // # output columns
  protected IntExpr xout = ctx.mkIntConst("xout");
  // # output rows
  protected IntExpr yout = ctx.mkIntConst("yout");
  protected Map<String, BoolExpr> operatorToSpec = new HashMap<>();

  protected void createSpecs() {
    {
      BoolExpr b1 = ctx.mkLe(xout, xin);
      BoolExpr b2 = ctx.mkGe(yout, yin);
      BoolExpr b = ctx.mkAnd(b1, b2);
      operatorToSpec.put("gather", b);
    }
    {
      BoolExpr b1 = ctx.mkEq(xout, ctx.mkSub(xin, ctx.mkInt(1)));
      BoolExpr b2 = ctx.mkEq(yout, yin);
      BoolExpr b = ctx.mkAnd(b1, b2);
      operatorToSpec.put("unite", b);
    }
    {
      BoolExpr b1 = ctx.mkGe(xout, xin);
      BoolExpr b2 = ctx.mkLe(yout, yin);
      BoolExpr b = ctx.mkAnd(b1, b2);
      operatorToSpec.put("spread", b);
    }
  }

  protected boolean attemptToPrune(AST ast) {
    // add your code here
    throw new RuntimeException();
  }

  protected boolean prune(AST ast, Dataframe inEx, Dataframe outEx) {
    // add your code here
    throw new RuntimeException();
  }

}
