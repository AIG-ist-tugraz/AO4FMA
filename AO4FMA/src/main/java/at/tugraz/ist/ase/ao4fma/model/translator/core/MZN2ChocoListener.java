/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

// Generated from /Users/manleviet/Development/AIG/AO4FMA/AO4FMA/src/main/java/at/tugraz/ist/ase/ao4fma/translator/core/MZN2Choco.g4 by ANTLR 4.12.0
package at.tugraz.ist.ase.ao4fma.model.translator.core;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MZN2ChocoParser}.
 */
public interface MZN2ChocoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#configuration}.
	 * @param ctx the parse tree
	 */
	void enterConfiguration(MZN2ChocoParser.ConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#configuration}.
	 * @param ctx the parse tree
	 */
	void exitConfiguration(MZN2ChocoParser.ConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_list(MZN2ChocoParser.Statement_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_list(MZN2ChocoParser.Statement_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MZN2ChocoParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MZN2ChocoParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#enum_stat}.
	 * @param ctx the parse tree
	 */
	void enterEnum_stat(MZN2ChocoParser.Enum_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#enum_stat}.
	 * @param ctx the parse tree
	 */
	void exitEnum_stat(MZN2ChocoParser.Enum_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(MZN2ChocoParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(MZN2ChocoParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(MZN2ChocoParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(MZN2ChocoParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#constraint_id}.
	 * @param ctx the parse tree
	 */
	void enterConstraint_id(MZN2ChocoParser.Constraint_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#constraint_id}.
	 * @param ctx the parse tree
	 */
	void exitConstraint_id(MZN2ChocoParser.Constraint_idContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(MZN2ChocoParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(MZN2ChocoParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotEqual}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotEqual(MZN2ChocoParser.NotEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqual}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotEqual(MZN2ChocoParser.NotEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOr(MZN2ChocoParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOr(MZN2ChocoParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqual(MZN2ChocoParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqual(MZN2ChocoParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd(MZN2ChocoParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd(MZN2ChocoParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code enum_value}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEnum_value(MZN2ChocoParser.Enum_valueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code enum_value}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEnum_value(MZN2ChocoParser.Enum_valueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(MZN2ChocoParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(MZN2ChocoParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Implication}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterImplication(MZN2ChocoParser.ImplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Implication}
	 * labeled alternative in {@link MZN2ChocoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitImplication(MZN2ChocoParser.ImplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#enum_decl}.
	 * @param ctx the parse tree
	 */
	void enterEnum_decl(MZN2ChocoParser.Enum_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#enum_decl}.
	 * @param ctx the parse tree
	 */
	void exitEnum_decl(MZN2ChocoParser.Enum_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#enum_values}.
	 * @param ctx the parse tree
	 */
	void enterEnum_values(MZN2ChocoParser.Enum_valuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#enum_values}.
	 * @param ctx the parse tree
	 */
	void exitEnum_values(MZN2ChocoParser.Enum_valuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MZN2ChocoParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(MZN2ChocoParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MZN2ChocoParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(MZN2ChocoParser.Var_declContext ctx);
}