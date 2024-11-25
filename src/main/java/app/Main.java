package app;

import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();

        // Add necessary views
        appBuilder
                .addSignupView()
                .addLoginView()
                .addHomeView()
//                .addTaskEnteredView()
                .addLoggedInView()
                .addTimerView()
                .addEnterTaskView()
                .addTaskEnteredView();

        // Add necessary use cases
        appBuilder
                .addSignupUseCase()
                .addHomeUseCase()
                .addLoginUseCase()
                .addEnterTaskUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .addEnterTaskUseCase();

        // Build and display the application
        JFrame appFrame = appBuilder.build();
        appFrame.setSize(500, 500); // Adjust size as needed
        appFrame.setVisible(true);
    }
}
