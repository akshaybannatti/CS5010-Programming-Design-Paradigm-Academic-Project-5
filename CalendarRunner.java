import calendar.controller.CalendarController;
import calendar.model.CalendarModel;
import calendar.model.MultiCalendarModelImpl;
import calendar.view.CliView;
import calendar.view.EventView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Entry point of the Calendar application.
 *
 * <p>This class configures and launches the calendar program in either
 * <b>interactive</b> or <b>headless</b> mode depending on user arguments.
 * It wires the Model–View–Controller (MVC) components together and ensures
 * that file paths and command-line arguments are handled in a platform-independent way.
 * </p>
 *
 * <p>Usage examples:</p>
 * <ul>
 *   <li><code>java -jar calendar.jar --mode interactive</code> — launches an
 *       interactive session.</li>
 *   <li><code>java -jar calendar.jar --mode headless commands.txt</code> — runs commands
 *       from a file.</li>
 * </ul>
 *
 * <p>All file system paths used by the program must be platform independent
 * as required by the assignment specification.</p>
 *
 * <p><b>Design:</b> This class is the main launcher and does not contain
 * business logic; the logic resides in the model, controller, and view packages.</p>
 */
public final class CalendarRunner {

  /**
   * Launches the Calendar application.
   *
   * <p>Accepts two or three command-line arguments:
   * </p>
   * <ul>
   *   <li><b>--mode interactive</b> — runs the program in interactive mode.</li>
   *   <li><b>--mode headless &lt;commands.txt&gt;</b> — executes commands from
   *       the provided file.</li>
   * </ul>
   *
   * <p>Invalid or missing arguments will result in an error message and graceful termination.</p>
   *
   * @param args command-line arguments specifying mode and (optionally) the input file
   * @throws IOException if reading from the commands file fails in headless mode
   */
  public static void main(final String[] args) {
    try {
      final CalendarModel model = new MultiCalendarModelImpl();
      final EventView view;

      boolean headless = false;
      Path scriptPath = null;

      if (args != null && args.length >= 2
          && "--mode".equalsIgnoreCase(args[0])
          && "headless".equalsIgnoreCase(args[1])) {
        headless = true;
        if (args.length >= 3) {
          scriptPath = Path.of(args[2]);
        } else {
          throw new IllegalArgumentException(
              "Headless mode requires a commands file path");
        }
      }

      if (headless) {
        final List<String> lines = Files.readAllLines(scriptPath, StandardCharsets.UTF_8);
        view = new CliView(System.out,
            new InputStreamReader(System.in, StandardCharsets.UTF_8));
        final CalendarController controller = new CalendarController(model, view);
        controller.runHeadless(lines);
      } else {
        view = new CliView(System.out,
            new InputStreamReader(System.in, StandardCharsets.UTF_8));
        final CalendarController controller = new CalendarController(model, view);
        controller.runInteractive();
      }
    } catch (IOException ioe) {
      System.err.println("Fatal error: " + ioe.getMessage());
    } catch (RuntimeException re) {
      System.err.println("Fatal error: " + re.getMessage());
    }
  }
}
