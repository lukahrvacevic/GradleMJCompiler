package rs.ac.bg.etf.pp1;

import java_cup.runtime.*;
import rs.ac.bg.etf.pp1.logger.CompilerDiagnostics;
import rs.ac.bg.etf.pp1.sym;
import rs.ac.bg.etf.pp1.util.Context;

%%

%public
%class MJScanner

%unicode

%line
%column
%char

%cup

%{
    private CompilerDiagnostics diagnostics;

    public MJScanner(java.io.Reader in, Context context) {
        this(in);
        this.diagnostics = CompilerDiagnostics.getInstance(context);
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void report_error() {
        diagnostics.reportError("Lexical error at line " + (yyline + 1) + ":" + (yycolumn + 1) + ": " + yytext());
    }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t]

Comment = "//" {InputCharacter}* {LineTerminator}?

Identifier = [:jletter:][:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*
CharLiteral = \'[^\r\n\'\\]\'

%%

<YYINITIAL> {
    /* keywords */

    "abstract"          { return symbol(sym.ABSTRACT); }
    "break"             { return symbol(sym.BREAK); }
    "class"             { return symbol(sym.CLASS); }
    "const"             { return symbol(sym.CONST); }
    "continue"          { return symbol(sym.CONTINUE); }
    "else"              { return symbol(sym.ELSE); }
    "extends"           { return symbol(sym.EXTENDS); }
    "for"               { return symbol(sym.FOR); }
    "if"                { return symbol(sym.IF); }
    "length"            { return symbol(sym.LENGTH); }
    "new"               { return symbol(sym.NEW); }
    "program"           { return symbol(sym.PROGRAM); }
    "print"             { return symbol(sym.PRINT); }
    "read"              { return symbol(sym.READ); }
    "return"            { return symbol(sym.RETURN); }
    "this"              { return symbol(sym.THIS); }
    "void"              { return symbol(sym.VOID); }

    "true"              { return symbol(sym.BOOLEAN_LITERAL, 1); }
    "false"             { return symbol(sym.BOOLEAN_LITERAL, 0); }

    "null"              { return symbol(sym.NULL_LITERAL); }

    /* sugar */

    "findAny"           { return symbol(sym.FIND_ANY); }
    "map"               { return symbol(sym.MAP); }

    /* separators */

    "("                 { return symbol(sym.LPAREN); }
    ")"                 { return symbol(sym.RPAREN); }
    "{"                 { return symbol(sym.LBRACE); }
    "}"                 { return symbol(sym.RBRACE); }
    "["                 { return symbol(sym.LBRACK); }
    "]"                 { return symbol(sym.RBRACK); }
    ";"                 { return symbol(sym.SEMICOLON); }
    ","                 { return symbol(sym.COMMA); }
    "."                 { return symbol(sym.DOT); }
    "=>"                { return symbol(sym.ARROW); }

    /* operators */

    "="                 { return symbol(sym.EQ); }
    ">"                 { return symbol(sym.GT); }
    "<"                 { return symbol(sym.LT); }
    "?"                 { return symbol(sym.QUESTION); }
    ":"                 { return symbol(sym.COLON); }
    "=="                { return symbol(sym.EQEQ); }
    "<="                { return symbol(sym.LTEQ); }
    ">="                { return symbol(sym.GTEQ); }
    "!="                { return symbol(sym.EXCL_EQ); }
    "&&"                { return symbol(sym.CONJ); }
    "||"                { return symbol(sym.DISJ); }
    "++"                { return symbol(sym.INCR); }
    "--"                { return symbol(sym.DECR); }
    "+"                 { return symbol(sym.ADD); }
    "-"                 { return symbol(sym.SUB); }
    "*"                 { return symbol(sym.MULT); }
    "/"                 { return symbol(sym.DIV); }
    "%"                 { return symbol(sym.MOD); }

    /* numericl iterals */

    {IntegerLiteral}    { return symbol(sym.INTEGER_LITERAL, Integer.parseInt(yytext())); }
    {CharLiteral}       { return symbol(sym.CHAR_LITERAL, yycharat(1)); }

    /* whitespace */

    {WhiteSpace}        { /* ignore */ }

    /* comments */

    {Comment}           { /* ignore */ }

    /*identifiers */

    {Identifier}        { return symbol(sym.IDENTIFIER, yytext()); }
}

.                       { report_error(); }