/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2021-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.model.translator;

import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.core.MZN2ChocoBaseListener;
import at.tugraz.ist.ase.ao4fma.model.translator.core.MZN2ChocoLexer;
import at.tugraz.ist.ase.ao4fma.model.translator.core.MZN2ChocoParser;
import at.tugraz.ist.ase.hiconfit.common.ConstraintUtils;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Translates the MZN file to a Choco model using ANTLR4.
 * This is a simple translator with limited functionality.
 * The order of the specification of variables, domains, and constraints has to be:
 * - First, using enum syntax to specify domains
 * - Second, using var syntax to specify variables
 * - Finally, using constraint syntax to specify constraints
 * Supporting operators: ==, !=, /\, \/, ->, <->
 * Expression forms: <exp> <op> <exp>
 *     <variable> <op> <variable>
 * Constraints: constraint [::id] <ep>
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class MZN2ChocoTranslator extends MZN2ChocoBaseListener {

    private ProductAwareConfigurationModel model;

    private final Stack<String> ruleParts = new Stack<>();

    public void translate(InputStream inputFile, ProductAwareConfigurationModel model) throws IOException {
        log.debug("{}Translating the MZN file to Choco model >>>", LoggerUtils.tab());
        LoggerUtils.indent();

        if (inputFile == null) {
            throw new MZN2ChocoTranslatorException("File should not be null");
        }

        this.model = model;

        val input = CharStreams.fromStream(inputFile);
        val lexer = new MZN2ChocoLexer(input);
        val tokens = new CommonTokenStream(lexer);
        val parser = new MZN2ChocoParser(tokens);
        ParseTree tree = parser.configuration();

        // create a standard ANTLR parse tree walker
        val walker = new ParseTreeWalker();
        // feed to walker
        walker.walk(this, tree);        // walk parse tree

        LoggerUtils.outdent();
        log.debug("{}<<< The MZN file translated to Choco model", LoggerUtils.tab());
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

        addDomain(key, enum_values);

        LoggerUtils.outdent();
        log.debug("{}<<< The domain {} parsed", LoggerUtils.tab(), key.toUpperCase());
    }

    private void addDomain(String domainName, List<String> domainValues) {
        log.debug("{}Adds the domain {} to Model", LoggerUtils.tab(), domainName.toUpperCase());
        model.addDomain(domainName, domainValues);
    }

    // Parse variables
    public void exitVariable(MZN2ChocoParser.VariableContext ctx) {
        log.debug("{}Parsing \"var {}\" >>>", LoggerUtils.tab(), ctx.var_decl().getText());
        LoggerUtils.indent();

        String type = ctx.var_decl().IDENTIFIER(0).getText();
        String var = ctx.var_decl().IDENTIFIER(1).getText();

        addVariable(var, type);

        LoggerUtils.outdent();
        log.debug("{}<<< The variable \"{}\" of the domain {} parsed", LoggerUtils.tab(), var, type.toUpperCase());
    }

    private void addVariable(String varName, String domainName) {
        log.debug("{}Adding the variable {} of the domain {} to Model >>>", LoggerUtils.tab(), varName.toUpperCase(), domainName.toUpperCase());
        // check the existence of domainName
        val domain = model.getDomain(domainName);

        if (domain == null) {
            throw new MZN2ChocoTranslatorException("Domain " + domainName + " hasn't declared yet");
        }

        log.debug("{}Creating an IntVar for the variable {} >>>", LoggerUtils.tab(), varName.toUpperCase());
        // create a variable in the Choco Model
        val intVar = model.getModel().intVar(varName, domain.getIntValues());

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

            addIfThenConstraint(ifConstraint, thenConstraint);

            newNumCstrs = model.getModel().getNbCstrs();
        } else if (ctx.expr() instanceof MZN2ChocoParser.Bi_ImplicationContext) {
            ifConstraint = exprContextTranslate((ParserRuleContext) ctx.expr().getChild(0));
            thenConstraint = exprContextTranslate((ParserRuleContext) ctx.expr().getChild(2));

            addIfOnlyIfConstraint(ifConstraint, thenConstraint);

            newNumCstrs = model.getModel().getNbCstrs();
        }

        String rightPart = ruleParts.pop();
        String leftPart = ruleParts.pop();
        String strConstraint = leftPart + " -> " + rightPart;

        log.debug("{}Sets choco constraints to DRConstraint", LoggerUtils.tab());
        val constraint = new at.tugraz.ist.ase.hiconfit.kb.core.Constraint(strConstraint);
        ConstraintUtils.addChocoConstraintsToConstraint(false, constraint, model.getModel(), oldNumCstrs, newNumCstrs - 1);
        model.addConstraint(constraint);

        LoggerUtils.outdent();
        log.debug("{}{} parsed >>>", LoggerUtils.tab(), ctx.getText().toUpperCase());
    }

    private void addIfThenConstraint(Constraint ifConstraint, Constraint thenConstraint) {
        model.getModel().ifThen(ifConstraint, thenConstraint);
        log.debug("{}Adds the constraint: IF {}, THEN {} to the Choco model >>>", LoggerUtils.tab(), ifConstraint, thenConstraint);
    }

    private void addIfOnlyIfConstraint(Constraint ifConstraint, Constraint thenConstraint) {
        model.getModel().ifOnlyIf(ifConstraint, thenConstraint);
        log.debug("{}Adds the constraint: {} IfOnlyIf {} to the Choco model >>>", LoggerUtils.tab(), ifConstraint, thenConstraint);
    }

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
        val ctx = (MZN2ChocoParser.AndContext) context;

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

        return addAndConstraint(cstrs);
    }

    private Constraint addAndConstraint(Constraint[] cstrs) {
        val cstr = model.getModel().and(cstrs);
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
            val cx = (MZN2ChocoParser.EqualContext) context;

            var = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(0));

            // check if the variable is an enum
            if (cx.expr(1) instanceof MZN2ChocoParser.Enum_valueContext enum_cx) {
                enum_name = enum_cx.IDENTIFIER().getText();
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

                variable = model.getVariable(var);

                value_index = this.getValueIndex(value, enum_name);
            } else {
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(1));
                enum_name = null;

                variable = this.model.getVariable(var);

                value_index = value.equals("true") ? 1 : 0;
            }

            op = "=";
        } else { // not equal
            log.debug("{}Parsing {} >>>", LoggerUtils.tab(), context.getText());
            val cx = (MZN2ChocoParser.NotEqualContext)context;

            var = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(0));

            // check if the variable is an enum
            if (cx.expr(1) instanceof MZN2ChocoParser.Enum_valueContext enum_cx) {
                enum_name = enum_cx.IDENTIFIER().getText();
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) enum_cx.expr());

                variable = model.getVariable(var);

                value_index = this.getValueIndex(value, enum_name);
            } else {
                value = exprIDContextTranslate((MZN2ChocoParser.IdContext) cx.expr(1));
                enum_name = null;

                variable = this.model.getVariable(var);

                value_index = value.equals("true") ? 1 : 0;
            }

            op = "!=";
        }
        log.debug("{}<<< {} parsed", LoggerUtils.tab(), context.getText());

        ruleParts.push(var + " " + op + " " + enum_name + "[" + value + "]");
        return addArithmConstraint(variable, op, value_index);
    }

    private Constraint addArithmConstraint(Variable variable, String op, int value) {
        Constraint cstr;

        if (variable instanceof IntVar var) {
            cstr =  model.getModel().arithm(var, op, value);
        } else {
            throw new MZN2ChocoTranslatorException("Variable " + variable.getName() + " is not an IntVar or a BoolVar");
        }

        log.debug("{}Adds the constraint: {} to the Choco model", LoggerUtils.tab(), variable.getName() + op + value);
        return cstr;
    }

    public int getValueIndex(String value, String domainName) {
        val domain = model.getDomain(domainName);

        if (domain == null) {
            throw new MZN2ChocoTranslatorException("Domain " + domainName + " not found");
        }

        return domain.indexOf(value);
    }

    private String exprIDContextTranslate(MZN2ChocoParser.IdContext context) {
        return context.IDENTIFIER().getText();
    }
}