package interface_adapter.add_task;

/**
 * The state for the enter task.
 */
public class EnterTaskState {

        private String taskName;
        private String taskDescription;
        private String taskStatus;
        private double taskTime;
        private String taskError;
        private double progress;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public void setTaskStatus(String status) {
            this.taskStatus = status;
        }

        public void setTaskTime(double time) {
            this.taskTime = time;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public double getTaskTime() {
            return taskTime;
        }

        public void setEnterTaskError(String error) {
            this.taskError = error;
        }

//        public void setProgress(double progress) {
//            this.progress = progress;
//        }
//
//        public void getProgress() {
//            return progress;
//        }
}