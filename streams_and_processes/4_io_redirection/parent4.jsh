var p4 = new ProcessBuilder("java", "-cp", "filters.jar", "Find", "Redirection").
              redirectInput(new File("Readme.txt")).
              redirectOutput(new File("temp.txt")).
              redirectError(new File("errors.txt")).
              start()
