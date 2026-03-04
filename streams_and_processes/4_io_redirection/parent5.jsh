var p5 = new ProcessBuilder("java", "-cp, "filters.jar", "Find", "Redirection").
              redirectInput(new File("Readme.txt")).
              redirectOutput(new File("temp.txt")).
              redirectErrorStream(true).
              start()
