public class Timer extends Thread {
    private int millis;
    private boolean down;
    private Thread thread;
    private GUI gui;
    private boolean running;

    public Timer(int millis, boolean down, GUI gui) {
        this.down = down;
        this.millis = millis;
        this.gui = gui;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        if (down) {
            if (millis == 0)
                gui.countdownFinish();
            while (running && millis > 0) {
                millis -= System.currentTimeMillis() - now;
                millis = Math.max(millis, 0);
                now = System.currentTimeMillis();
                gui.countdown( String.format("%02d:%02d:%03d",millis/60000, millis%60000/1000, millis%1000));
                if (millis == 0)
                    gui.countdownFinish();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            while (running) {
                millis += System.currentTimeMillis() - now;
                now = System.currentTimeMillis();
                gui.timer( String.format("%02d:%02d:%03d",millis/60000, millis%60000/1000, millis%1000));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }
}
