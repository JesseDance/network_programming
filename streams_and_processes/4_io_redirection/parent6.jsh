var p6 = new ProcessBuilder("java", "Find", "child").
              redirectInput(new File("Readme.txt")).
              redirectOutput(ProcessBuilder.Redirect.appendTo(new File("temp.txt"))).
              redirectErrorStream(true).
              start()
