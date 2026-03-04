

/**
   This program shows what happens when a parent and child
   share System.in and both try to use it at the same time.

   Run this program with the following command-lines
      > java TestSharedInput
   and
      > java TestSharedInput_read < TestSharedInput_data.txt
*/

class Child11
{
   public static void main(String[] args) throws Exception
   {
      // One line of data from the file TestSharedInput_data.txt.
      final byte[] bytes = new byte[8];

      // Make the child pause.
      //try {Thread.sleep(100);}catch(Exception e){}

      // Echo lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         System.in.read(bytes);
         System.out.print("Child : " + new String(bytes));
      }

      // Close the input stream (how does this affect the parent?).
      System.in.close();
      System.out.println("Child process done.");
   }
}


public class TestSharedInput_read
{
   public static void main(String[] args) throws Exception
   {
      System.out.println("Starting Child11 process ...");
      final Process p = new ProcessBuilder("java", "Child11")
                             .inheritIO()
                             .start();

      // Do NOT wait for the child process to terminate.
      //p.waitFor();

      // One line of data from the file TestSharedInput_data.txt.
      final byte[] bytes = new byte[8];

      // Make the parent pause.
      //try {Thread.sleep(100);}catch(Exception e){}

      // Echo lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         System.in.read(bytes);
         System.out.print("Parent : " + new String(bytes));
      }

      // Close the input stream (how does this affect the child?).
      System.in.close();
      System.out.println("Parent process done.");

      // Wait for the child process to terminate.
      p.waitFor(); // Try commenting this out. Then the child can compete with the shell.
   }
}

/*
   Running this program with this command-line.

      > java TestSharedInput

                                  shell
                           +-----------------+
                           |                 |
        keyboard >---+---->> stdin    stdout >>----+---> console window
                     |     |                 |     |
                     |     |          stderr >>----+
                     |     |                 |     |
                     |     +-----------------+     |
                     |                             |
                     |     TestSharedInput_read    |
                     |     +-----------------+     |
                     |     |                 |     |
                     +---->> stdin    stdout >>----+
                     |     |                 |     |
                     |     |          stderr >>----+
                     |     |                 |     |
                     |     +-----------------+     |
                     |                             |
                     |           Child             |
                     |     +-----------------+     |
                     |     |                 |     |
                     +---->> stdin    stdout >>----+
                           |                 |     |
                           |          stderr >>----+
                           |                 |
                           +-----------------+


   Running this program with this command-line.

      > java TestSharedInput < TestSharedInput_data.txt

                                  shell
                           +-----------------+
                           |                 |
        keyboard >-------->> stdin    stdout >>----+---> console window
                           |                 |     |
                           |          stderr >>----+
                           |                 |     |
                           +-----------------+     |
                                                   |
                           TestSharedInput_read    |
                           +-----------------+     |
                           |                 |     |
         data.txt >--+---->> stdin    stdout >>----+
                     |     |                 |     |
                     |     |          stderr >>----+
                     |     |                 |     |
                     |     +-----------------+     |
                     |                             |
                     |           Child             |
                     |     +-----------------+     |
                     |     |                 |     |
                     +---->> stdin    stdout >>----+
                           |                 |     |
                           |          stderr >>----+
                           |                 |
                           +-----------------+
*/
