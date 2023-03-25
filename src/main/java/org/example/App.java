package org.example;

public class App {
    private boolean isWorking;

    public App() {
        this.isWorking = true;

    }

    public void switchOff() {
        this.isWorking = false;
    }

    public Boolean getStatus() {
        return isWorking;
    }

    public void readLine(String line) {
//        TODO
    }
}
