# EECS498

Inductive program synthesis.

Specification is inductive.

Inductive: incomplete, under-specified (test cases, input-output examples, under-constrained logical formulas, etc.)

Counter: complete specifications (will be covered in a few lectures.)

Inductive is simple, A broader class of users can provide, One of the simplest interfaces for program synthesis.

say, the CFG is e::= x|e+e|eXe|1|2|3|4

What are some programs in this CFG that satisfy the spec?

x+1, x X x, x X 2, etc.

say, the CFG is e::= x|e+e|eXe|e-e|1|2|3|4

Does an inductive program synthesizer always give back the correct program?

- Yes, it always gives back a program that satisfies the spec
- No, it may not give back a program that actually meets the users' intent
- This is known as the overfitting or generalization problem
  - Inductive spec is a partial representation of the user's intent.
  - We'll talk about this later in the course.

Problmes:

- Generalization: Is the program you found the one that you're actually looking for? (Constrained search space, ranking, etc.)

- Search: How to find a program that satisfies the spec? (Top-down search, bottom-up search, etc.)

- Efficiency: How to efficiently find a program that satisfies the spec? (Pruning, priortization, etc.)

- Search space: How to define the space of programs in the first place? (Domain-specific languages, CFGs, functional languages, etc.)

- Specs: How to support different inductive specs? (Examples, types, demonstrations, etc.)

- ETc. such as noise, multi-modality, interaction, etc. (Limits to program ability, etc.)

  PBE: programming-by-example

  Still work with SYGUS paradigm

  - spec: examples (could be represented as logical formulas)
  - search space: CFGs, in particular, DSLs (functional)
  - Search: talk about different search techniques.

DSL (Domain specific Languages):

- They are programming lnagues, but more specialized and less universal.
- Program: A description of how to perform a computation.
- Programming language:
  - A description of many computations by compositing individual syntactic elements each with well defined meaning
  - Syntax: How to write a program in a PL?
  - Semantics: What does this program mean?

- Universal PLs: Python, Java, C/C++
  - Describe many computations: add numbers, sort lists, transform trees
- DSLs: PLs specialized to specific tasks and not universal.
  - Describe different computations by composing individual syntactic elements each with well defined meaning 
  - Arithmetic expressions e::= x|e+e|eXe|1|2|3|4
  - Or could be defined by you.
- DSL: 
  - syntax: CFG (operators and compositions)
  - Semantics: what does every operator and composition mean?
    - Enough to use examples to define semantics
    - Or translating into a general-purpose PL
    - But to fully specify semantics, need formal semantics 
    - This course: use informal semantics.
- Functional programming languages:
  - Programming paradigms: functional (e.g Haskell), imperative (e.g. C)
  - We will mainly use functional PLs for synthesis, because:
    - No side effects. Computation in functional PLs is by evaluating pure functions, without side effects or mutations. This greatly simplifies synthesis.
    - Conciseness and Expressiveness.
- Cosider the following DSL:
  - Syntax in CFG
    - df::=x | gather(df,s,s,k,k) | unite(df,sk,k)
    - k::= 1|2|3|4
    - s::= tmp1 | tmp2 | tmp3
  - Some programs in this DSL:
    - Semantics:
      - What does every DSL construct/operator mean? What does it evaluate to?
      - e.g. what gather does, There are examples to give you an idea what gather actually does.
      - Gather is recursive (with df)

Abstract Syntax Trees (ASTs)

- Represent programs with ASTs
  - Ignores the uninteresting details such as spacing, parenthesis, etc.
  - Syntax: No semantic information.
  - Tree: Tree data structure.

Overview of search techniques:

- PBE Problem: Given a DSL with predefined syntax (in a CFG G) and semantics and given a set E of input-output examples, find a program P in G such that P satisfies E (set of SYGUS) Has the interpreter that analyzes the program to see if the program is running well or not.
- E here is the example, without the logical formula
- our DSL has predefined semantics, so does SYGUS but sygus uses background theory T. Theory kinda serves as an interpreter
- Techniques:
  - Enumeration-based techniques: top down and bottom up search.
  - Representation based techniques: version space algebra, finite tree automata
  - Constraint based approaches 
  - Stochastic search: MCMC
  - Many other techniques such as NN, RL, genetic programming, etc.
- Enumeration based approach.
  - How to systematically generate ASTs/enumerate all programs/ASTS in a given CFG?
  - Then, check each AST against examples.
- Key: How to systematically generate an AST:
  - Topdown: first generate parents, then children, then grandchildren, etc.
    - Generate higher level structures first, then fill it with lower level fragments.
    - Top-Down-Search((T,N,P,S), E): Breadth-first search
      - worklist algorithm: compilers, PL techniques, etc. Initially, worklist to be Start symbol. (df)
      - While loop (while worklist is not empty):
        - AST := worklist.remove()
        - Check if the Ast is complete and satisfies Examples, and if so, return AST
        - If not, expand the AST into more AST's and add it into worklist.addAll. 
          - When you expand, you have to respect the grammar.
  - Bottomup: First leaves, then parents, then grandparents, etc.
