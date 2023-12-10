package com.craftinginterpreters.lox;

class Return1 extends RuntimeException {
  final Object value;

  Return1(Object value) {
    super(null, null, false, false);
    this.value = value;
  }
}
