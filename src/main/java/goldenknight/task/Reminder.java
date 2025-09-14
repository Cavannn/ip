package goldenknight.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Provides reminder functionality to get the next upcoming task.
 */
public class Reminder {

    /**
     * Returns a string message showing the next upcoming task based on completion time.
     *
     * @param tasks the list of all tasks
     * @return the reminder message
     */
    public static String reminder(ArrayList<Task> tasks) {
        Task nextTask = null;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextTime = null;

        for (Task t : tasks) {
            LocalDateTime taskTime = null;

            if (t instanceof Deadline) {
                taskTime = ((Deadline) t).getByDateTime();
            } else if (t instanceof Event) {
                taskTime = ((Event) t).getToDateTime();
            }

            if (taskTime != null && taskTime.isAfter(now)) {
                if (nextTime == null || taskTime.isBefore(nextTime)) {
                    nextTime = taskTime;
                    nextTask = t;
                }
            }
        }

        if (nextTask == null) {
            return "You have no upcoming tasks!";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

        if (nextTask instanceof Deadline) {
            Deadline d = (Deadline) nextTask;
            return "Next deadline: " + d.getDescription()
                    + " by " + d.getByDateTime().format(formatter);
        } else if (nextTask instanceof Event) {
            Event e = (Event) nextTask;
            return "Next event: " + e.getDescription()
                    + " ends by " + e.getToDateTime().format(formatter);
        }

        return "";
    }
}
