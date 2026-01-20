@rem
@rem  Run a Java program with 2 logical (shared) cores.
@rem
start /affinity 0xC  cmd /k  java  FactorNumbers
