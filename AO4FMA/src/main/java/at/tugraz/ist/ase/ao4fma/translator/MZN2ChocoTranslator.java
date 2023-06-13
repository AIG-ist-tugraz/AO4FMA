/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2021-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.translator;

import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.translator.core.MZN2ChocoBaseListener;
import at.tugraz.ist.ase.ao4fma.translator.core.MZN2ChocoLexer;
import at.tugraz.ist.ase.ao4fma.translator.core.MZN2ChocoParser;
import at.tugraz.ist.ase.hiconfit.common.ConstraintUtils;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public class MZN2ChocoTranslator extends MZN2ChocoBaseListener {

//    @Getter
//    private DRModel model;
    private ProductAwareConfigurationModel model;

    private final Stack<String> ruleParts = new Stack<>();

    private Map<String, List<String>> enumDomains;
    private Map<String, String> vars;
    private Map<String, IntVar> intVars;
//    private List<at.tugraz.ist.ase.hiconfit.kb.core.Constraint> constraintList;

//    public MZN2ChocoTranslator() {
//        this.model = null;
//    }

    public void translate(String modelName, InputStream inputFile, ProductAwareConfigurationModel model) throws IOException {
        log.debug("{}Translating the MZN file to Choco model >>>", LoggerUtils.tab());
        LoggerUtils.indent();

        if (inputFile == null) {
            throw new MZN2ChocoTranslatorException("File should not be null");
        }

        // create a new DRModel
//        model = new DRModel(modelName);
        this.model = model;

        enumDomains = new LinkedHashMap<>();
        vars = new LinkedHashMap<>();
        intVars = new LinkedHashMap<>();
//        constraintList = new LinkedList<>();

        CharStream input = CharStreams.fromStream(inputFile);
        MZN2ChocoLexer lexer = new MZN2ChocoLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MZN2ChocoParser parser = new MZN2ChocoParser(tokens);
        ParseTree tree = parser.configuration();

        // create a standard ANTLR parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();
        // feed to walker
        walker.walk(this, tree);        // walk parse tree

        LoggerUtils.outdent();
        log.debug("{}<<< The MZN file translated to Choco model", LoggerUtils.tab());

        // check enums
//        for (Enumeration<String> keys = enums.keys(); keys.hasMoreElements();) {
//            String key = keys.nextElement();
//            System.out.print(key + " = {");
//            List<String> values = enums.get(key);
//            for (String value: values) {
//                System.out.print(value + ",");
//            }
//            System.out.println("}");
//        }

//        for (Variable v: model.getVars()) {
//            System.out.println(v);
//        }

//        System.out.println(model.getNbCstrs());

//        for (Constraint c: model.getCstrs()) {
//            System.out.println(c);
//        }
//        System.out.println(model);
    }

    // Parse domains
    public void exitEnum_stat(MZN2ChocoParser.Enum_statContext ctx) {

        String key = ctx.enum_decl().IDENTIFIER().getText();
        log.debug("{}Parsing the domain {} >>>", LoggerUtils.tab(), key.toUpperCase());
        LoggerUtils.indent();

        List<TerminalNode> enum_value_nodes = ctx.enum_decl().enum_values().IDENTIFIER();

        List<String> enum_values = new LinkedList<>();
        for (TerminalNode value : enum_value_nodes) {
            enum_values.add(value.getText());
            log.debug("{}{}", LoggerUtils.tab(), value.getText());
        }

//        model.addDomain(key, enum_values); // TODO
        addDomain(key, enum_values);

        LoggerUtils.outdent();
        log.debug("{}<<< The domain {} parsed", LoggerUtils.tab(), key.toUpperCase());
    }

    private void addDomain(String domainName, List<String> domainValues) {
        log.debug("{}Adds the domain {} to Model", LoggerUtils.tab(), domainName.toUpperCase());
        enumDomains.put(domainName, domainValues);
        model.addDomain(domainName, domainValues);
    }

    // Parse variables
    public void exitVariable(MZN2ChocoParser.VariableContext ctx) {
        log.debug("{}Parsing \"var {}\" >>>", LoggerUtils.tab(), ctx.var_decl().getText());
        LoggerUtils.indent();

        String type = ctx.var_decl().IDENTIFIER(0).getText();
        String var = ctx.var_decl().IDENTIFIER(1).getText();

//        model.addVariable(var, type); // TODO
        addVariable(var, type);

        LoggerUtils.outdent();
        log.debug("{}<<< The variable \"{}\" of the domain {} parsed", LoggerUtils.tab(), var, type.toUpperCase());
    }

    private void addVariable(String varName, String domainName) {
        log.debug("{}Adding the variable {} of the domain {} to Model >>>", LoggerUtils.tab(), varName.toUpperCase(), domainName.toUpperCase());
        // check the existence of domainName
        List<String> values = enumDomains.get(domainName); // take enum_values from enums

        if (values == null) {
            throw new MZN2ChocoTranslatorException("Domain " + domainName + " hasn't declared yet");
        }

        vars.put(varName, domainName);
        log.debug("{}<<< The variable {} of the domain {} added to Model", LoggerUtils.tab(), varName.toUpperCase(), domainName.toUpperCase());

        log.debug("{}Creating an IntVar for the variable {} >>>", LoggerUtils.tab(), varName.toUpperCase());
        int[] intVar_values = IntStream.range(0, values.size()).toArray();

        // create a variable in the Choco Model
        IntVar intVar = model.getModel().intVar(varName, intVar_values);

        intVars.put(varName, intVar);
        model.addVariable(varName, domainName, intVar);
        log.debug("{}<<< IntVar for the variable {} created", LoggerUtils.tab(), varName.toUpperCase());
    }

    // Parse constraints
    public void exitConstraint(MZN2ChocoParser.ConstraintContext ctx) {
        Constraint ifConstraint;
        Constraint thenConstraint;

        log.debug("{}Parsing {} >>>", LoggerUtils.tab(), ctx.getText().toUpperCase());
        LoggerUtils.indent();

        int oldNumCstrs = model.getModel().getNbCstrs();
        int newNumCstrs = oldNumCstrs;

        if (ctx.expr() instanceof MZN2ChocoParser.ImplicationContext) {
            ifConstraint = exprContextTranslate((ParserRuleContext) ctx.expr().getChild(0));
            thenConstraint = exprContextTranslate((ParserRuleContext) ctx.expr().getChild(2));

//            model.addIfThenConstraint(ifConstraint, thenConstraint); // TODO
            addIfThenConstraint(ifConstraint, thenConstraint);

            newNumCstrs = model.getModel().getNbCstrs();
        }

//        String constraintId = ctx.constraint_id().getChild(1).getText();
        String rightPart = ruleParts.pop();
        String leftPart = ruleParts.pop();
//        String strConstraint = constraintId + ": " + leftPart + " -> " + rightPart;
        String strConstraint = leftPart + " -> " + rightPart;

        log.debug("{}Sets choco constraints to DRConstraint", LoggerUtils.tab());
//        DRConstraint drConstraint = new DRConstraint(strConstraint); // TODO
        at.tugraz.ist.ase.hiconfit.kb.core.Constraint constraint = new at.tugraz.ist.ase.hiconfit.kb.core.Constraint(strConstraint);
//        setConstraintsToDRConstraint(oldNumCstrs, newNumCstrs, drConstraint); // TODO
        ConstraintUtils.addChocoConstraintsToConstraint(false, constraint, model.getModel(), oldNumCstrs, newNumCstrs - 1);
//        model.setConstraint(drConstraint); // TODO
        model.addConstraint(constraint);

        LoggerUtils.outdent();
        log.debug("{}{} parsed >>>", LoggerUtils.tab(), ctx.getText().toUpperCase());
    }

    private void addIfThenConstraint(Constraint ifConstraint, Constraint thenConstraint) {
        model.getModel().ifThen(ifConstraint, thenConstraint);
        log.debug("{}Adds the constraint: IF {}, THEN {} to the Choco model >>>", LoggerUtils.tab(), ifConstraint, thenConstraint);
    }

    // TODO
//    private void setConstraintsToDRConstraint(int oldNumCstrs, int newNumCstrs, DRConstraint rule) {
//        Constraint[] constraints = model.getModel().getCstrs();
//
//        int i = oldNumCstrs;
//        while (i < newNumCstrs) {
//            Constraint cstr = constraints[i];
//            if (cstr.getName().equals("REIFICATIONCONSTRAINT")) {
//                model.setCorrectConstraint(cstr);
//            } else {
//                rule.setConstraint(constraints[i].toString());
//            }
//            i++;
//        }
//    }

    private Constraint exprContextTranslate(ParserRuleContext context) {
        if (context instanceof MZN2ChocoParser.EqualContext // equal
            || context instanceof MZN2ChocoParser.NotEqualContext) { // not equal
            return equalContextTranslate(context);
        } else if (context instanceof MZN2ChocoParser.AndContext) {
            return andContextTranslate(context);
        } else {
            throw new MZN2ChocoTranslatorException(context.toString() + "hasn't supported yet");
        }
    }

    private Constraint andContextTranslate(ParserRuleContext context) {
        log.debug("{}Parsing {} >>>", LoggerUtils.tab(), context.getText());
        MZN2ChocoParser.AndContext ctx = (MZN2ChocoParser.AndContext) context;

        List<MZN2ChocoParser.ExprContext> cxs = ctx.expr();

        List<Constraint> cstrList = new LinkedList<>();

        for (MZN2ChocoParser.ExprContext cx: cxs) {
            if (cx instanceof MZN2ChocoParser.EqualContext
                || cx instanceof MZN2ChocoParser.NotEqualContext) {
                Constraint c = equalContextTranslate(cx);
                cstrList.add(c);
            }
        }

        Constraint[] cstrs = new Constraint[cstrList.size()];

        for (int i = 0; i < cstrList.size(); i++) {
            cstrs[i] = cstrList.get(i);
        }

        List<String> ruleparts = new LinkedList<>();
        for (int i = 0; i < cstrList.size(); i++) {
            ruleparts.add(ruleParts.pop());
        }

        ruleParts.push(StringUtils.join(ruleparts, " /\\ "));
        log.debug("{}<<< {} parsed", LoggerUtils.tab(), context.getText());

//        return model.addAndConstraint(cstrs); // TODO
        return addAndConstraint(cstrs);
    }

    private Constraint addAndConstraint(Constraint[] cstrs) {
        Constraint cstr = model.getModel().and(cstrs);
        log.debug("{}Adds AND constraint to the Choco model >>>", LoggerUtils.tab());
        return cstr;
    }

    private Constraint equalContextTranslate(ParserRuleContext context) {
        Variable variable;
        int value_index;
        String op;
        String enum_name;
        String var;
        String value;

        if (context instanceof MZN2ChocoParser.EqualContext) { // equal
            log.debug("{}Parsing {} >>>", LoggerUtils.tab(), context.getText());
            MZN2ChocoParser.EqualContext cx = (MZN2ChocoParser.EqualContext)context;

            var = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(0));

            // check if the variable is an enum
            if (cx.expr(1) instanceof MZN2ChocoParser.Enum_valueContext enum_cx) {
                enum_name = enum_cx.IDENTIFIER().getText();
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

                variable = this.getIntVar(var);

                value_index = this.getValueIndex(value, enum_name);
            } else {
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(1));
                enum_name = null;

                variable = this.model.getVariable(var);

                value_index = value.equals("true") ? 1 : 0;
            }
//            MZN2ChocoParser.Enum_valueContext enum_cx = (MZN2ChocoParser.Enum_valueContext) cx.expr(1);
//            enum_name = enum_cx.IDENTIFIER().getText();
//            value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

//            variable = model.getIntVar(var); // TODO

//            value_index = model.getValueIndex(value, enum_name); // TODO

            op = "=";
        } else { // not equal
            log.debug("{}Parsing {} >>>", LoggerUtils.tab(), context.getText());
            MZN2ChocoParser.NotEqualContext cx = (MZN2ChocoParser.NotEqualContext)context;

            var = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(0));

            // check if the variable is an enum
            if (cx.expr(1) instanceof MZN2ChocoParser.Enum_valueContext enum_cx) {
                enum_name = enum_cx.IDENTIFIER().getText();
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

                variable = this.getIntVar(var);

                value_index = this.getValueIndex(value, enum_name);
            } else {
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(1));
                enum_name = null;

                variable = this.model.getVariable(var);

                value_index = value.equals("true") ? 1 : 0;
            }

//            MZN2ChocoParser.Enum_valueContext enum_cx = (MZN2ChocoParser.Enum_valueContext) cx.expr(1);
//            enum_name = enum_cx.IDENTIFIER().getText();
//            value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

//            variable = model.getIntVar(var); // TODO

//            value_index = model.getValueIndex(value, enum_name); // TODO

            op = "!=";
        }
        log.debug("{}<<< {} parsed", LoggerUtils.tab(), context.getText());

        ruleParts.push(var + " " + op + " " + enum_name + "[" + value + "]");
//        return model.addArithmConstraint(variable, op, value_index); // TODO
        return addArithmConstraint(variable, op, value_index); // TODO
    }

    private Constraint addArithmConstraint(Variable variable, String op, int value) {
        Constraint cstr;

        if (variable instanceof IntVar) {
            cstr =  model.getModel().arithm((IntVar) variable, op, value);
        } else if (variable instanceof BoolVar) {
            cstr =  model.getModel().arithm((BoolVar) variable, op, value);
        } else {
            throw new MZN2ChocoTranslatorException("Variable " + variable.getName() + " is not an IntVar or a BoolVar");
        }

        log.debug("{}Adds the constraint: {} to the Choco model", LoggerUtils.tab(), variable.getName() + op + value);
        return cstr;
    }

    private Variable getIntVar(String varName) {
        return intVars.get(varName);
    }

    public int getValueIndex(String value, String domainName) {
        List<String> enum_values = enumDomains.get(domainName);
        return enum_values.indexOf(value);
    }

    private String exprIDContextTranslate(MZN2ChocoParser.IdContext context) {
        String id = context.IDENTIFIER().getText();
        return id;
    }

//    public void exitConstraint(MZN2ChocoParser.ConstraintContext ctx) {
//        //System.out.println(ctx.expr().getText());
//
//        ParserRuleContext leftContext = (ParserRuleContext) ctx.expr().getChild(0);
//        TerminalNode opNode = (TerminalNode) ctx.expr().getChild(1);
//        ParserRuleContext rightContext = (ParserRuleContext) ctx.expr().getChild(2);
//
//        IntVar leftVar = leftContextTranslate(model, leftContext);
//
//        // if null, then error
//        if (leftVar == null) return;
//
//        String op = opNodeTranslate(opNode);
//
//        Pair<IntVar, Integer> rightVar = rightContextTranslate(model, rightContext);
//
//        if (rightVar == null) return;
//
//        if (rightVar.getValue0() != null) {
//            model.arithm(leftVar, op, rightVar.getValue0()).post();
//        } else {
//            model.arithm(leftVar, op, rightVar.getValue1()).post();
//        }
//    }
//
//    private String opNodeTranslate(TerminalNode node) {
//        String op = MZN2ChocoParser._LITERAL_NAMES[node.getSymbol().getType()];
//        op = op.substring(1, op.length() - 1);
//
//        if (op.equals("==")) op = "=";
//
//        //System.out.println(op);
//        return op;
//    }
//
//    private IntVar leftContextTranslate(Model model, ParserRuleContext leftContext) {
//        //System.out.println(leftContext.getRuleIndex());
//        if (leftContext instanceof MZN2ChocoParser.IdContext) {
//            MZN2ChocoParser.IdContext id = (MZN2ChocoParser.IdContext)leftContext;
//
//            return findVariable(model, id.getText());
//        } else {
//            System.out.println("Error: must a identifier in the left side.");
//        }
//        return null;
//    }
//
//    private Pair<IntVar, Integer> rightContextTranslate(Model model, ParserRuleContext rightContext) {
//        if (rightContext instanceof MZN2ChocoParser.IdContext) {
//            MZN2ChocoParser.IdContext id = (MZN2ChocoParser.IdContext) rightContext;
//
//            return Pair.with(findVariable(model, id.getText()), Integer.MIN_VALUE);
//        }
////        } if (rightContext instanceof MZN2ChocoParser.IntContext) {
////            MZN2ChocoParser.IntContext valueContext = (MZN2ChocoParser.IntContext)rightContext;
////
////            return Pair.with(null, Integer.parseInt(valueContext.getText()));
////        }
//        else {
//            System.out.println("Error: must a identifier or an integer in the right side");
//        }
//        return null;
//    }

//    private IntVar findVariable(Model model, String name) {
//        Variable var = model.getVar(0);
//        for (Variable v : model.getVars()) {
//            if (v.getName().equals(name)) {
//                var = v;
//                break;
//            }
//        }
//        return (IntVar)var;
//    }
}