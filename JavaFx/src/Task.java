import GameLogic.BlindsHelper;
import GameLogic.GameEngine;

public class Task extends javafx.concurrent.Task<Task.ActionResult> {

    private GameEngine refGameEngine;
    private String file;

    public class ActionResult {
        public boolean result;
        public String msg;

        public ActionResult(boolean result,String msg){
            this.result = result;
            this.msg = msg;
        }
    }


    public Task(GameEngine ser , String file) {
        SetServer(ser);
        this.file = file;
    }

    public void SetServer(GameEngine ser)
    {
        refGameEngine = ser;
    }

    @Override
    protected Task.ActionResult call() throws Exception {
        this.updateMessage("Loading");
        Thread.sleep(1000);
        this.updateProgress(20, 100);
        try {
            if(this.file != null) {
                Thread.sleep(700);
                this.updateProgress(50, 100);
                refGameEngine.loadFile(this.file);
                Thread.sleep(2300);
                this.updateProgress(100, 100);
                Thread.sleep(1000);
            }
            this.updateProgress(0, 100);
            return new ActionResult(true, "File loaded successfully");
        } catch ( Exception e ) {
            this.updateProgress(0, 100);
            return new ActionResult(false, e.getMessage());

        }
    }
}
