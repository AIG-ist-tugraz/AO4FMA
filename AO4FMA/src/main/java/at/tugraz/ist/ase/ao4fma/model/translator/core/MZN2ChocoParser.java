/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

// Generated from /Users/manleviet/Development/AIG/AO4FMA/AO4FMA/src/main/java/at/tugraz/ist/ase/ao4fma/model/translator/core/MZN2Choco.g4 by ANTLR 4.12.0
package at.tugraz.ist.ase.ao4fma.model.translator.core;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MZN2ChocoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, VAR=9, 
		ENUM=10, CONSTRAINT=11, CM=12, SC=13, CL=14, LP=15, RP=16, MUL=17, DIV=18, 
		ADD=19, SUB=20, AND=21, OR=22, EQU=23, NEQ=24, GRT=25, LES=26, GRE=27, 
		LEE=28, IMP=29, IDENTIFIER=30, COMMENT=31, WS=32, INT_CONST=33;
	public static final int
		RULE_configuration = 0, RULE_statement_list = 1, RULE_statement = 2, RULE_enum_stat = 3, 
		RULE_variable = 4, RULE_constraint = 5, RULE_constraint_id = 6, RULE_expr = 7, 
		RULE_enum_decl = 8, RULE_enum_values = 9, RULE_var_decl = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"configuration", "statement_list", "statement", "enum_stat", "variable", 
			"constraint", "constraint_id", "expr", "enum_decl", "enum_values", "var_decl"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'::\"'", "'\"'", "'=='", "'<->'", "'['", "']'", "'{'", "'}'", 
			"'var'", "'enum'", "'constraint'", "','", "';'", "':'", "'('", "')'", 
			"'*'", "'/'", "'+'", "'-'", "'/\\'", "'\\/'", "'='", "'!='", "'>'", "'<'", 
			"'>='", "'<='", "'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "VAR", "ENUM", 
			"CONSTRAINT", "CM", "SC", "CL", "LP", "RP", "MUL", "DIV", "ADD", "SUB", 
			"AND", "OR", "EQU", "NEQ", "GRT", "LES", "GRE", "LEE", "IMP", "IDENTIFIER", 
			"COMMENT", "WS", "INT_CONST"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MZN2Choco.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MZN2ChocoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConfigurationContext extends ParserRuleContext {
		public List<Statement_listContext> statement_list() {
			return getRuleContexts(Statement_listContext.class);
		}
		public Statement_listContext statement_list(int i) {
			return getRuleContext(Statement_listContext.class,i);
		}
		public ConfigurationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configuration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterConfiguration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitConfiguration(this);
		}
	}

	public final ConfigurationContext configuration() throws RecognitionException {
		ConfigurationContext _localctx = new ConfigurationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_configuration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3584L) != 0)) {
				{
				{
				setState(22);
				statement_list();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Statement_listContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode SC() { return getToken(MZN2ChocoParser.SC, 0); }
		public Statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterStatement_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitStatement_list(this);
		}
	}

	public final Statement_listContext statement_list() throws RecognitionException {
		Statement_listContext _localctx = new Statement_listContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(28);
			statement();
			setState(29);
			match(SC);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public Enum_statContext enum_stat() {
			return getRuleContext(Enum_statContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(34);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ENUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				enum_stat();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(32);
				variable();
				}
				break;
			case CONSTRAINT:
				enterOuterAlt(_localctx, 3);
				{
				setState(33);
				constraint();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Enum_statContext extends ParserRuleContext {
		public TerminalNode ENUM() { return getToken(MZN2ChocoParser.ENUM, 0); }
		public Enum_declContext enum_decl() {
			return getRuleContext(Enum_declContext.class,0);
		}
		public Enum_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterEnum_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitEnum_stat(this);
		}
	}

	public final Enum_statContext enum_stat() throws RecognitionException {
		Enum_statContext _localctx = new Enum_statContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_enum_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(ENUM);
			setState(37);
			enum_decl();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(MZN2ChocoParser.VAR, 0); }
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(VAR);
			setState(40);
			var_decl();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintContext extends ParserRuleContext {
		public TerminalNode CONSTRAINT() { return getToken(MZN2ChocoParser.CONSTRAINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Constraint_idContext constraint_id() {
			return getRuleContext(Constraint_idContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitConstraint(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_constraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(CONSTRAINT);
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(43);
				constraint_id();
				}
			}

			setState(46);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Constraint_idContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MZN2ChocoParser.IDENTIFIER, 0); }
		public Constraint_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterConstraint_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitConstraint_id(this);
		}
	}

	public final Constraint_idContext constraint_id() throws RecognitionException {
		Constraint_idContext _localctx = new Constraint_idContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constraint_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(T__0);
			setState(49);
			match(IDENTIFIER);
			setState(50);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParensContext extends ExprContext {
		public TerminalNode LP() { return getToken(MZN2ChocoParser.LP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RP() { return getToken(MZN2ChocoParser.RP, 0); }
		public ParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitParens(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEqualContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode NEQ() { return getToken(MZN2ChocoParser.NEQ, 0); }
		public NotEqualContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterNotEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitNotEqual(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(MZN2ChocoParser.OR, 0); }
		public OrContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitOr(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public EqualContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitEqual(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(MZN2ChocoParser.AND, 0); }
		public AndContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitAnd(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Enum_valueContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(MZN2ChocoParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Enum_valueContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterEnum_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitEnum_value(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(MZN2ChocoParser.IDENTIFIER, 0); }
		public IdContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitId(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImplicationContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IMP() { return getToken(MZN2ChocoParser.IMP, 0); }
		public ImplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterImplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitImplication(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Bi_ImplicationContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Bi_ImplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterBi_Implication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitBi_Implication(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(53);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				_localctx = new Enum_valueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54);
				match(IDENTIFIER);
				setState(55);
				match(T__4);
				setState(56);
				expr(0);
				setState(57);
				match(T__5);
				}
				break;
			case 3:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59);
				match(LP);
				setState(60);
				expr(0);
				setState(61);
				match(RP);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(85);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(83);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new EqualContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(65);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(66);
						((EqualContext)_localctx).op = match(T__2);
						setState(67);
						expr(10);
						}
						break;
					case 2:
						{
						_localctx = new NotEqualContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(68);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(69);
						((NotEqualContext)_localctx).op = match(NEQ);
						setState(70);
						expr(9);
						}
						break;
					case 3:
						{
						_localctx = new AndContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(71);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(72);
						((AndContext)_localctx).op = match(AND);
						setState(73);
						expr(8);
						}
						break;
					case 4:
						{
						_localctx = new OrContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(74);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(75);
						((OrContext)_localctx).op = match(OR);
						setState(76);
						expr(7);
						}
						break;
					case 5:
						{
						_localctx = new ImplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(77);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(78);
						((ImplicationContext)_localctx).op = match(IMP);
						setState(79);
						expr(6);
						}
						break;
					case 6:
						{
						_localctx = new Bi_ImplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(80);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(81);
						((Bi_ImplicationContext)_localctx).op = match(T__3);
						setState(82);
						expr(5);
						}
						break;
					}
					} 
				}
				setState(87);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Enum_declContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MZN2ChocoParser.IDENTIFIER, 0); }
		public TerminalNode EQU() { return getToken(MZN2ChocoParser.EQU, 0); }
		public Enum_valuesContext enum_values() {
			return getRuleContext(Enum_valuesContext.class,0);
		}
		public Enum_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterEnum_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitEnum_decl(this);
		}
	}

	public final Enum_declContext enum_decl() throws RecognitionException {
		Enum_declContext _localctx = new Enum_declContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_enum_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(IDENTIFIER);
			setState(89);
			match(EQU);
			setState(90);
			match(T__6);
			setState(91);
			enum_values();
			setState(92);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Enum_valuesContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(MZN2ChocoParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(MZN2ChocoParser.IDENTIFIER, i);
		}
		public List<TerminalNode> CM() { return getTokens(MZN2ChocoParser.CM); }
		public TerminalNode CM(int i) {
			return getToken(MZN2ChocoParser.CM, i);
		}
		public Enum_valuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_values; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterEnum_values(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitEnum_values(this);
		}
	}

	public final Enum_valuesContext enum_values() throws RecognitionException {
		Enum_valuesContext _localctx = new Enum_valuesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_enum_values);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(IDENTIFIER);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CM) {
				{
				{
				setState(95);
				match(CM);
				setState(96);
				match(IDENTIFIER);
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(MZN2ChocoParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(MZN2ChocoParser.IDENTIFIER, i);
		}
		public TerminalNode CL() { return getToken(MZN2ChocoParser.CL, 0); }
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterVar_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitVar_decl(this);
		}
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_var_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(IDENTIFIER);
			setState(103);
			match(CL);
			setState(104);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 8);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001!k\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005"+
		"\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007"+
		"\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0005\u0000\u0018\b\u0000"+
		"\n\u0000\f\u0000\u001b\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002#\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0003\u0005-\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0003\u0007@\b\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007T\b\u0007\n\u0007"+
		"\f\u0007W\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0005\tb\b\t\n\t\f\te\t\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0000\u0001\u000e\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0000\u0000l\u0000\u0019\u0001\u0000\u0000\u0000\u0002"+
		"\u001c\u0001\u0000\u0000\u0000\u0004\"\u0001\u0000\u0000\u0000\u0006$"+
		"\u0001\u0000\u0000\u0000\b\'\u0001\u0000\u0000\u0000\n*\u0001\u0000\u0000"+
		"\u0000\f0\u0001\u0000\u0000\u0000\u000e?\u0001\u0000\u0000\u0000\u0010"+
		"X\u0001\u0000\u0000\u0000\u0012^\u0001\u0000\u0000\u0000\u0014f\u0001"+
		"\u0000\u0000\u0000\u0016\u0018\u0003\u0002\u0001\u0000\u0017\u0016\u0001"+
		"\u0000\u0000\u0000\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u0001\u0001"+
		"\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u001d\u0003"+
		"\u0004\u0002\u0000\u001d\u001e\u0005\r\u0000\u0000\u001e\u0003\u0001\u0000"+
		"\u0000\u0000\u001f#\u0003\u0006\u0003\u0000 #\u0003\b\u0004\u0000!#\u0003"+
		"\n\u0005\u0000\"\u001f\u0001\u0000\u0000\u0000\" \u0001\u0000\u0000\u0000"+
		"\"!\u0001\u0000\u0000\u0000#\u0005\u0001\u0000\u0000\u0000$%\u0005\n\u0000"+
		"\u0000%&\u0003\u0010\b\u0000&\u0007\u0001\u0000\u0000\u0000\'(\u0005\t"+
		"\u0000\u0000()\u0003\u0014\n\u0000)\t\u0001\u0000\u0000\u0000*,\u0005"+
		"\u000b\u0000\u0000+-\u0003\f\u0006\u0000,+\u0001\u0000\u0000\u0000,-\u0001"+
		"\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000./\u0003\u000e\u0007\u0000"+
		"/\u000b\u0001\u0000\u0000\u000001\u0005\u0001\u0000\u000012\u0005\u001e"+
		"\u0000\u000023\u0005\u0002\u0000\u00003\r\u0001\u0000\u0000\u000045\u0006"+
		"\u0007\uffff\uffff\u00005@\u0005\u001e\u0000\u000067\u0005\u001e\u0000"+
		"\u000078\u0005\u0005\u0000\u000089\u0003\u000e\u0007\u00009:\u0005\u0006"+
		"\u0000\u0000:@\u0001\u0000\u0000\u0000;<\u0005\u000f\u0000\u0000<=\u0003"+
		"\u000e\u0007\u0000=>\u0005\u0010\u0000\u0000>@\u0001\u0000\u0000\u0000"+
		"?4\u0001\u0000\u0000\u0000?6\u0001\u0000\u0000\u0000?;\u0001\u0000\u0000"+
		"\u0000@U\u0001\u0000\u0000\u0000AB\n\t\u0000\u0000BC\u0005\u0003\u0000"+
		"\u0000CT\u0003\u000e\u0007\nDE\n\b\u0000\u0000EF\u0005\u0018\u0000\u0000"+
		"FT\u0003\u000e\u0007\tGH\n\u0007\u0000\u0000HI\u0005\u0015\u0000\u0000"+
		"IT\u0003\u000e\u0007\bJK\n\u0006\u0000\u0000KL\u0005\u0016\u0000\u0000"+
		"LT\u0003\u000e\u0007\u0007MN\n\u0005\u0000\u0000NO\u0005\u001d\u0000\u0000"+
		"OT\u0003\u000e\u0007\u0006PQ\n\u0004\u0000\u0000QR\u0005\u0004\u0000\u0000"+
		"RT\u0003\u000e\u0007\u0005SA\u0001\u0000\u0000\u0000SD\u0001\u0000\u0000"+
		"\u0000SG\u0001\u0000\u0000\u0000SJ\u0001\u0000\u0000\u0000SM\u0001\u0000"+
		"\u0000\u0000SP\u0001\u0000\u0000\u0000TW\u0001\u0000\u0000\u0000US\u0001"+
		"\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000V\u000f\u0001\u0000\u0000"+
		"\u0000WU\u0001\u0000\u0000\u0000XY\u0005\u001e\u0000\u0000YZ\u0005\u0017"+
		"\u0000\u0000Z[\u0005\u0007\u0000\u0000[\\\u0003\u0012\t\u0000\\]\u0005"+
		"\b\u0000\u0000]\u0011\u0001\u0000\u0000\u0000^c\u0005\u001e\u0000\u0000"+
		"_`\u0005\f\u0000\u0000`b\u0005\u001e\u0000\u0000a_\u0001\u0000\u0000\u0000"+
		"be\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000"+
		"\u0000d\u0013\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000fg\u0005"+
		"\u001e\u0000\u0000gh\u0005\u000e\u0000\u0000hi\u0005\u001e\u0000\u0000"+
		"i\u0015\u0001\u0000\u0000\u0000\u0007\u0019\",?SUc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}