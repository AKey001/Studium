package de.antonkiessling.studium.plan.commons;

public class InternetThread {

    public void run(InternetThreadAction action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    action.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
