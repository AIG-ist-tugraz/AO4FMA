/*
 * ChocoWS - A RESTful Web Service for Choco Solver
 *
 * Copyright (c) 2021 TUGraz
 *
 * Applied Artificial Intelligence & Software Engineering Research Group
 * Institute for Software Technology
 * Graz University of Technology, Austria
 * Website: http://ase.ist.tugraz.at/ASE/
 */
grammar MZN2Choco;
import CommonLexer;

//options{
//    language = Java;
//    //output=AST;
//    tokenVocab=CSP2ChocoLexer;
//}

//@header {
//import org.chocosolver.parser.flatzinc.ast.*;
//import org.chocosolver.parser.flatzinc.ast.declaration.*;
//import org.chocosolver.parser.flatzinc.ast.expression.*;
//import org.chocosolver.solver.ResolutionPolicy;
//import org.chocosolver.solver.Model;
//
//import java.util.ArrayList;
//import java.util.List;
//}
//
//@members{
//
//public Datas datas;
//
//// the configuration
//public Model mModel;
//
//
//public boolean allSolutions, freeSearch;
//}

//@members{
//public Model configuration;
//
//void defineArithmeticConstraint() { }
//}

configuration : statement_list* ;

statement_list : (statement SC) ;

statement : enum_stat
          | variable
          | constraint
          ;

enum_stat : ENUM enum_decl;

variable: VAR var_decl;

constraint : CONSTRAINT (constraint_id)? expr;

constraint_id : '::"' IDENTIFIER '"';

expr:   expr op='=' expr                         # Equal
    |   expr op='!=' expr                         # NotEqual
    |   expr op='/\\' expr                               # And
    |   expr op='\\/' expr                               # Or
    |   expr op='->' expr                               # Implication
    |   IDENTIFIER                                      # id
    |   IDENTIFIER '[' expr ']'                         # enum_value
//    |   INT_CONST                                       # int
    |   LP expr RP                                    # parens
    ;

//expr:   '-' expr                                        # Minus
//    |   expr op=('*'|'/') expr                          # MulDiv
//    |   expr op=('+'|'-') expr                          # AddSub
//    |   expr op=('>'|'>='|'<'|'<='|'='|'!=') expr      # Comparation
//    |   '!' expr                                        # Not
//    |   expr op='&&' expr                               # And
//    |   expr op='||' expr                               # Or
//    |   expr op='->' expr                               # Implication
//    |   IDENTIFIER                                      # id
//    |   IDENTIFIER '[' expr ']'                         # enum_value
////    |   INT_CONST                                       # int
//    |   '(' expr ')'                                    # parens
//    ;

enum_decl: IDENTIFIER EQU '{' enum_values '}' ;

enum_values: IDENTIFIER (CM IDENTIFIER)* ;

var_decl: IDENTIFIER CL IDENTIFIER ;