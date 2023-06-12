/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2021-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

// Generated from /Users/manleviet/Development/GitHub/ChocoWebService/ChocoWS/src/main/java/at/tugraz/ist/ase/chocows/dr/translator/core/MZN2Choco.g4 by ANTLR 4.9.1
package at.tugraz.ist.ase.ao4fma.translator.core;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MZN2ChocoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, VAR=7, ENUM=8, CONSTRAINT=9, 
		CM=10, SC=11, CL=12, LP=13, RP=14, MUL=15, DIV=16, ADD=17, SUB=18, AND=19, 
		OR=20, EQU=21, NEQ=22, GRT=23, LES=24, GRE=25, LEE=26, IMP=27, IDENTIFIER=28, 
		COMMENT=29, WS=30, INT_CONST=31;
	public static final int
		RULE_model = 0, RULE_statement_list = 1, RULE_statement = 2, RULE_enum_stat = 3, 
		RULE_variable = 4, RULE_constraint = 5, RULE_constraint_id = 6, RULE_expr = 7, 
		RULE_enum_decl = 8, RULE_enum_values = 9, RULE_var_decl = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "statement_list", "statement", "enum_stat", "variable", "constraint", 
			"constraint_id", "expr", "enum_decl", "enum_values", "var_decl"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'::\"'", "'\"'", "'['", "']'", "'{'", "'}'", "'var'", "'enum'", 
			"'constraint'", "','", "';'", "':'", "'('", "')'", "'*'", "'/'", "'+'", 
			"'-'", "'/\\'", "'\\/'", "'='", "'!='", "'>'", "'<'", "'>='", "'<='", 
			"'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "VAR", "ENUM", "CONSTRAINT", 
			"CM", "SC", "CL", "LP", "RP", "MUL", "DIV", "ADD", "SUB", "AND", "OR", 
			"EQU", "NEQ", "GRT", "LES", "GRE", "LEE", "IMP", "IDENTIFIER", "COMMENT", 
			"WS", "INT_CONST"
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

	public static class ModelContext extends ParserRuleContext {
		public List<Statement_listContext> statement_list() {
			return getRuleContexts(Statement_listContext.class);
		}
		public Statement_listContext statement_list(int i) {
			return getRuleContext(Statement_listContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MZN2ChocoListener ) ((MZN2ChocoListener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << ENUM) | (1L << CONSTRAINT))) != 0)) {
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
	public static class EqualContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQU() { return getToken(MZN2ChocoParser.EQU, 0); }
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
				match(T__2);
				setState(56);
				expr(0);
				setState(57);
				match(T__3);
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
			setState(82);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(80);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new EqualContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(65);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(66);
						((EqualContext)_localctx).op = match(EQU);
						setState(67);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new NotEqualContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(68);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(69);
						((NotEqualContext)_localctx).op = match(NEQ);
						setState(70);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new AndContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(71);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(72);
						((AndContext)_localctx).op = match(AND);
						setState(73);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new OrContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(74);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(75);
						((OrContext)_localctx).op = match(OR);
						setState(76);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new ImplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(77);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(78);
						((ImplicationContext)_localctx).op = match(IMP);
						setState(79);
						expr(5);
						}
						break;
					}
					} 
				}
				setState(84);
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
			setState(85);
			match(IDENTIFIER);
			setState(86);
			match(EQU);
			setState(87);
			match(T__4);
			setState(88);
			enum_values();
			setState(89);
			match(T__5);
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
			setState(91);
			match(IDENTIFIER);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CM) {
				{
				{
				setState(92);
				match(CM);
				setState(93);
				match(IDENTIFIER);
				}
				}
				setState(98);
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
			setState(99);
			match(IDENTIFIER);
			setState(100);
			match(CL);
			setState(101);
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
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3!j\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\3\3\3\3\3\3\4\3\4\3\4\5\4%\n\4"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\5\7/\n\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tB\n\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tS\n\t\f\t\16\tV\13\t\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\7\13a\n\13\f\13\16\13d\13\13\3\f\3\f"+
		"\3\f\3\f\3\f\2\3\20\r\2\4\6\b\n\f\16\20\22\24\26\2\2\2j\2\33\3\2\2\2\4"+
		"\36\3\2\2\2\6$\3\2\2\2\b&\3\2\2\2\n)\3\2\2\2\f,\3\2\2\2\16\62\3\2\2\2"+
		"\20A\3\2\2\2\22W\3\2\2\2\24]\3\2\2\2\26e\3\2\2\2\30\32\5\4\3\2\31\30\3"+
		"\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\3\3\2\2\2\35\33\3"+
		"\2\2\2\36\37\5\6\4\2\37 \7\r\2\2 \5\3\2\2\2!%\5\b\5\2\"%\5\n\6\2#%\5\f"+
		"\7\2$!\3\2\2\2$\"\3\2\2\2$#\3\2\2\2%\7\3\2\2\2&\'\7\n\2\2\'(\5\22\n\2"+
		"(\t\3\2\2\2)*\7\t\2\2*+\5\26\f\2+\13\3\2\2\2,.\7\13\2\2-/\5\16\b\2.-\3"+
		"\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61\5\20\t\2\61\r\3\2\2\2\62\63\7\3\2\2"+
		"\63\64\7\36\2\2\64\65\7\4\2\2\65\17\3\2\2\2\66\67\b\t\1\2\67B\7\36\2\2"+
		"89\7\36\2\29:\7\5\2\2:;\5\20\t\2;<\7\6\2\2<B\3\2\2\2=>\7\17\2\2>?\5\20"+
		"\t\2?@\7\20\2\2@B\3\2\2\2A\66\3\2\2\2A8\3\2\2\2A=\3\2\2\2BT\3\2\2\2CD"+
		"\f\n\2\2DE\7\27\2\2ES\5\20\t\13FG\f\t\2\2GH\7\30\2\2HS\5\20\t\nIJ\f\b"+
		"\2\2JK\7\25\2\2KS\5\20\t\tLM\f\7\2\2MN\7\26\2\2NS\5\20\t\bOP\f\6\2\2P"+
		"Q\7\35\2\2QS\5\20\t\7RC\3\2\2\2RF\3\2\2\2RI\3\2\2\2RL\3\2\2\2RO\3\2\2"+
		"\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\21\3\2\2\2VT\3\2\2\2WX\7\36\2\2XY\7"+
		"\27\2\2YZ\7\7\2\2Z[\5\24\13\2[\\\7\b\2\2\\\23\3\2\2\2]b\7\36\2\2^_\7\f"+
		"\2\2_a\7\36\2\2`^\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\25\3\2\2\2db"+
		"\3\2\2\2ef\7\36\2\2fg\7\16\2\2gh\7\36\2\2h\27\3\2\2\2\t\33$.ARTb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}