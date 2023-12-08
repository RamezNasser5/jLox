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
    R visitTernaryStatement(Ternary stmt);
    R visitWhileStmt(While stmt);
    R visitDoStatement(Do stmt);
    R visitBreakStmt(Break stmt);
  }

  static class Do extends Stmt {
    Do(Expr condition, Stmt body) {
      this.condition = condition;
      this.body = body;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitDoStatement(this);
    }

    final Expr condition;
    final Stmt body;
    
  }

  static class While extends Stmt {
    While(Expr condition, Stmt body) {
      this.condition = condition;
      this.body = body;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitWhileStmt(this);
    }

    final Expr condition;
    final Stmt body;
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

  static class Ternary extends Stmt {
    Ternary(Expr condition,Stmt thenBranch,Stmt elseBranch) {
      this.condition = condition;
      this.thenBranch = thenBranch;
      this.elseBranch = elseBranch;
    }
    @Override
    <R> R accept(Visitor<R> visitor) {
        
        return visitor.visitTernaryStatement(this);
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

static class Break extends Stmt {
  @Override
  <R> R accept(Visitor<R> visitor) {
      return visitor.visitBreakStmt(this);
  }
}

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
