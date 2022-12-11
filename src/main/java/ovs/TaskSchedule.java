package ovs;

import ovs.graph.Task;

import java.util.ArrayList;

public class TaskSchedule {

    static long currentTime = System.currentTimeMillis();

    private static ArrayList<TimedTask> tasks = new ArrayList<>();
    private static ArrayList<TimedTask> removedTasks = new ArrayList<>();

    protected static void update(){
        currentTime = System.currentTimeMillis();

        for (int i = 0; i < tasks.size(); i++) {
            TimedTask task = tasks.get(i);

            if(task.startTime + task.delay < currentTime){
                task.task.onFinished();
                removedTasks.add(task);
            }
        }

        for (int i = 0; i < removedTasks.size(); i++) {
            tasks.remove(removedTasks.get(i));
        }

        if(removedTasks.size() > 0){
            removedTasks.clear();
        }

    }

    public static void addTask(Task task, long delay){
        TimedTask timedTask = new TimedTask();
        timedTask.task = task;
        timedTask.startTime = System.currentTimeMillis();
        timedTask.delay = delay;
        tasks.add(timedTask);
    }

    static class TimedTask{
        Task task;
        long startTime;
        long delay;
    }
}
