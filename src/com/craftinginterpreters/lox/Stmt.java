//> Appendix II stmt
package com.craftinginterpreters.lox;

import java.util.List;

abstract class Stmt {
  interface Visitor<R> {
    R visitBlockStmt(Block stmt);
    R visitExpressionStmt(Expression stmt);
    R visitPrintStmt(Print stmt);
    R visitVarStmt(Var stmt);
    R visitCastFloatStmt(CastFloat stmt);
    R visitCastStringStmt(CastString stmt);
    R visitIfStatement(If stmt);
  }

  static class If extends Stmt {
    If(Expr condition,Stmt thenBranch,Stmt elseBranch) {
      this.condition = condition;
      this.thenBranch = thenBranch;
      this.elseBranch = elseBranch;
    }
    @Override
    <R> R accept(Visitor<R> visitor) {
        
        return visitor.visitIfStatement(this);
    }
    final Expr condition;
    final Stmt thenBranch;
    final Stmt elseBranch;
    
  }

  // Nested Stmt classes here...
//> stmt-block
  static class Block extends Stmt {
    Block(List<Stmt> statements) {
      this.statements = statements;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBlockStmt(this);
    }

    final List<Stmt> statements;
  }
//< stmt-block

static class CastString  extends Stmt{
  CastString(Expr expression) {
    this.expression = expression;
  }

  @Override
  <R> R accept(Visitor<R> visitor) {
    return visitor.visitCastStringStmt(this);
  }

  Expr expression;
  
}

static class CastFloat  extends Stmt{
  CastFloat(Expr expression) {
    this.expression = expression;
  }

  @Override
  <R> R accept(Visitor<R> visitor) {
    return visitor.visitCastFloatStmt(this);
  }

  Expr expression;
  
}

//> stmt-expression
  static class Expression extends Stmt {
    Expression(Expr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitExpressionStmt(this);
    }

    final Expr expression;
  }
//< stmt-expression
//> stmt-print
  static class Print extends Stmt {
    Print(Expr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitPrintStmt(this);
    }

    final Expr expression;
  }
//< stmt-print
//> stmt-var
  static class Var extends Stmt {
    Var(Token name, Expr initializer) {
      this.name = name;
      this.initializer = initializer;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitVarStmt(this);
    }

    final Token name;
    final Expr initializer;
  }
//< stmt-var

  abstract <R> R accept(Visitor<R> visitor);
}
//< Appendix II stmt
