@rem
@rem  Run a Java program with 2 physical (hardware) cores.
@rem
start /affinity 0x50  cmd /k  java  FactorNumbers
