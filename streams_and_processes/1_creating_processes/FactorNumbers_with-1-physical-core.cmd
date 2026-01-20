@rem
@rem  Run a Java program with 1 physical (hardware) core.
@rem
start /affinity 0x02  cmd /k  java  FactorNumbers
