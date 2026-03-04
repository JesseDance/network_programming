@rem
@rem  Run a Java program with 2 logical (shared) cores.
@rem
start /affinity 0xC  cmd /k  "echo Using 2 logical cores & java FactorNumbers"
