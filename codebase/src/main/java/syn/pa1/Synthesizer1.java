package syn.pa1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.rosuda.REngine.Rserve.RserveException;

import syn.base.AST;
import syn.base.ASTNode;
import syn.base.CFG;
import syn.base.Dataframe;
import syn.base.Production;
import syn.base.Synthesizer;

public class Synthesizer1 extends Synthesizer {

  public Synthesizer1(CFG cfg) throws RserveException {
    super(cfg);
  }

  // this counter counts the number of iterations of the worklist algorithm
  public int iterCounter = 0;
  // running time of the algorithm in seconds
  public int runTime = 0;

  @Override
  public AST run(Dataframe inEx, Dataframe outEx) {

    long start = System.currentTimeMillis();

    LinkedList<AST> worklist = new LinkedList<>();

    worklist.add(mkInitialAST());

    AST ret = null;

    // a worklist algorithm that performs top-down search
    while (!worklist.isEmpty()) {

      iterCounter++;

      ret = worklist.removeFirst();

        if(selectOpenNode(ret) == null && interp.eval(ret,inEx) != null && (interp.eval(ret, inEx)).equals(outEx)){
          return ret;
        }
        else if(selectOpenNode(ret) == null && interp.eval(ret,inEx) != null && !(interp.eval(ret, inEx)).equals(outEx)){
          continue;
        }
        else if(selectOpenNode(ret) == null){
          continue;
        }

      ASTNode node = selectOpenNode(ret);

      Production[] symbols = cfg.getProductions(node.getSymbol());

      for(int i = 0; i < symbols.length; ++i){
        AST temp =  ret.expand(node, symbols[i]);
        if(temp.numOfOperators() <= bound) {
          worklist.addLast(temp);
        }
      }

      //
      // add your code here
      //

    }

    long end = System.currentTimeMillis();
    runTime = (int) (end - start) / 1000;

    return ret;
  }

  private ASTNode deepSearch(AST ast, ASTNode cur) {
    if (cur.isHole()) {
      return cur;
    } else {
      ASTNode[] children = ast.getChildren(cur);
      for (int i = 0; i < children.length; i++) {
        ASTNode n = deepSearch(ast, children[i]);
        if (n != null) {
          return n;
        }
      }
    }
    return null;
  }

  protected ASTNode selectOpenNode(AST ast) {
    return deepSearch(ast, ast.getRoot());
  }

  protected AST mkInitialAST() {
    ASTNode root = new ASTNode(cfg.getStartSymbol(), null);
    Map<ASTNode, ASTNode[]> nodeToChildren = new HashMap<>();
    Map<ASTNode, ASTNode> nodeToParent = new HashMap<>();
    nodeToParent.put(root, null);
    return new AST(root, nodeToChildren, nodeToParent);
  }

}
