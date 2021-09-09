package syn.base;

import java.util.Map;

public class CFG {

  // this maps each symbol s to all productions that have s as return symbol
  private Map<String, Production[]> symbolToProductions;
  // start symbol of the grammar
  private String startSymbol;

  public CFG(Map<String, Production[]> symbolToProductions, String startSymbol) {
    this.symbolToProductions = symbolToProductions;
    this.startSymbol = startSymbol;
  }

  public String getStartSymbol() {
    return startSymbol;
  }

  public Production[] getProductions(String symbol) {
    return symbolToProductions.get(symbol);
  }

}
