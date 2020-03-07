package taskmanager;


import taskmanager.client.Client;
import taskmanager.server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.*;
import taskmanager.server.NotificationThread;

public class TimeNotification {
    private Log<TaskNode> temp;

    public TimeNotification(Log tl) throws IOException, ClassNotFoundException {
        temp = tl;
    }


    public void onTimeNotification() throws ParseException {
        Timer timer = new Timer();
        for (int i = 0; i < temp.size(); i++) {

            GregorianCalendar tempCal = new GregorianCalendar();
            tempCal = temp.get(i).getTaskDate();
            tempCal.add(Calendar.MONTH, -1);
            Date tempDate = new Date();
            if (tempCal.getTimeInMillis() > System.currentTimeMillis() && !temp.get(i).getChanged()) {
                temp.get(i).setChanged(true);
                timer.schedule(new timeTask(temp.get(i)), tempCal.getTime());
            }
        }
    }


    private class timeTask extends TimerTask {
        TaskNode notificationTask;

        timeTask(TaskNode tn) {
            this.notificationTask = tn;
        }

        @Override
        public void run() {


            System.out.println("Произошла нотификация");
            Date date = notificationTask.getTaskDate().getTime();
            System.out.println(notificationTask.getTaskName() + " " + notificationTask.getTaskDescription());
            try {
                ObjectOutputStream output = new ObjectOutputStream(NotificationThread.out);
                output.writeObject(new Message(notificationTask, "NOTIFICATION"));
                output.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}