package com.craftinginterpreters.lox;

import java.util.List;

import com.craftinginterpreters.lox.Stmt.CastString;
import com.craftinginterpreters.lox.Stmt.If;
import com.craftinginterpreters.lox.Stmt.Ternary;

class Interpreter implements Expr.Visitor<Object>,
        Stmt.Visitor<Void> {

    private Environment environment = new Environment();

    void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitIfStatement(If stmt) {
        if (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitTernaryStatement(Ternary stmt) {
        if (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitCastStringStmt(CastString stmt) {
        Object value = evaluate(stmt.expression);
        Token name = ((Expr.Variable) (stmt.expression)).name;
        String stringObject = "";
        if (value.toString().endsWith(".0")) {
            stringObject = value.toString().substring(0, value.toString().length() - 2);
        } else {
            stringObject = value.toString();
        }

        environment.assign(name, stringObject);
        return null;
    }

    @Override
    public Void visitCastFloatStmt(Stmt.CastFloat stmt) {
        Object value = evaluate(stmt.expression);
        Token name = ((Expr.Variable) (stmt.expression)).name;
        double doublObject = Double.parseDouble(value.toString());
        environment.assign(name, doublObject);
        return null;
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right); // [left]

        switch (expr.operator.type) {
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case GREATER:
                return left.toString().compareTo(right.toString()) > 0;

            case GREATER_EQUAL:
                return left.toString().compareTo(right.toString()) >= 0;

            case LESS:
                return left.toString().compareTo(right.toString()) < 0;
            case LESS_EQUAL:
                return left.toString().compareTo(right.toString()) <= 0;
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                } // [plus]

                if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                if (left.toString().endsWith(".0")) {
                    return left.toString().substring(0, left.toString().length() - 2) + right.toString();
                }
                if (right.toString().endsWith(".0")) {
                    return left.toString() + right.toString().substring(0, right.toString().length() - 2);
                }

            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                if (checkNumberOperands(expr.operator, left, right)) {
                    return (double) left * (double) right;
                } else if (left instanceof String && right instanceof Double) {
                    String text_loop = "";
                    Double int_right = (Double) right;
                    for (int i = 0; i < int_right; i++) {
                        text_loop += left.toString();
                    }
                    return text_loop;
                } else {
                    String text_loop = "";
                    Double int_left = (Double) left;
                    for (int i = 0; i < int_left; i++) {
                        text_loop += right.toString();
                    }
                    return text_loop;
                }
            case AND:
                break;
            case BANG:
                break;
            case CLASS:
                break;
            case COMMA:
                break;
            case DOT:
                break;
            case ELSE:
                break;
            case EOF:
                break;
            case EQUAL:
                break;
            case FALSE:
                break;
            case FOR:
                break;
            case FUN:
                break;
            case IDENTIFIER:
                break;
            case IF:
                break;
            case LEFT_BRACE:
                break;
            case LEFT_PAREN:
                break;
            case NIL:
                break;
            case NUMBER:
                break;
            case OR:
                break;
            case PRINT:
                break;
            case RETURN:
                break;
            case RIGHT_BRACE:
                break;
            case RIGHT_PAREN:
                break;
            case SEMICOLON:
                break;
            case STRING:
                break;
            case SUPER:
                break;
            case THIS:
                break;
            case TRUE:
                break;
            case VAR:
                break;
            case WHILE:
                break;
            default:
                break;

        }

        // Unreachable.
        return null;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double) right;
            case AND:
                break;
            case BANG_EQUAL:
                break;
            case CLASS:
                break;
            case COMMA:
                break;
            case DOT:
                break;
            case ELSE:
                break;
            case EOF:
                break;
            case EQUAL:
                break;
            case EQUAL_EQUAL:
                break;
            case FALSE:
                break;
            case FOR:
                break;
            case FUN:
                break;
            case GREATER:
                break;
            case GREATER_EQUAL:
                break;
            case IDENTIFIER:
                break;
            case IF:
                break;
            case LEFT_BRACE:
                break;
            case LEFT_PAREN:
                break;
            case LESS:
                break;
            case LESS_EQUAL:
                break;
            case NIL:
                break;
            case NUMBER:
                break;
            case OR:
                break;
            case PLUS:
                break;
            case PRINT:
                break;
            case RETURN:
                break;
            case RIGHT_BRACE:
                break;
            case RIGHT_PAREN:
                break;
            case SEMICOLON:
                break;
            case SLASH:
                break;
            case STAR:
                break;
            case STRING:
                break;
            case SUPER:
                break;
            case THIS:
                break;
            case TRUE:
                break;
            case VAR:
                break;
            case WHILE:
                break;
            default:
                break;
        }

        // Unreachable.
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }
        environment.define(stmt.name.lexeme, value);
        return null;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return environment.get(expr.name);
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
        if (expr.name instanceof Expr.Variable) {
            environment.assign(((Expr.Variable) (expr.name)).name, value);
        }
        if (expr.name instanceof Expr.Binary) {
            boolean b = isTruthy(evaluate((Expr.Binary) expr.name));
            if (b) {
                Token t = ((Expr.Variable) ((Expr.Binary) (expr.name)).left).name;
                environment.assign(t, value);
            }
        } else
            throw new RuntimeError(((Expr.Binary) (expr.name)).operator,
                    "Operator must be < or >.");
        return value;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeStmt(stmt.statements);
        return null;
    }

    void executeStmt(List<Stmt> statements) {
        for (Stmt statement : statements) {
            execute(statement);
        }
    }

    void executeBlock(List<Stmt> statements,
            Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;
            for (Stmt statement : statements) {
                execute(statement);
            }
        } finally {
            this.environment = previous;
        }
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) {
            return;
        }
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private boolean checkNumberOperands(Token operator,
            Object left, Object right) {
        if (left instanceof Double && right instanceof Double) {
            return true;
        }
        return false;
        // [operand]
        // throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private boolean isTruthy(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (boolean) object;
        }
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }

        return a.equals(b);
    }

    private String stringify(Object object) {
        if (object == null) {
            return "variable not assigned";

        }

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }

}
