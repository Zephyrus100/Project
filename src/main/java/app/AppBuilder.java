package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.*;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_task.EnterTaskController;
import interface_adapter.add_task.EnterTaskPresenter;
import interface_adapter.add_task.EnterTaskViewModel;
import interface_adapter.add_task.TaskEnteredViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.get_quote.QuoteController;
import interface_adapter.get_quote.QuotePresenter;
import interface_adapter.get_quote.QuoteViewModel;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.enter_task.EnterTaskInputBoundary;
import use_case.enter_task.EnterTaskInteractor;
import use_case.enter_task.EnterTaskOutputBoundary;
//import use_case.enter_task.InMemoryTaskData;
import use_case.get_quote.QuoteInputBoundary;
import use_case.get_quote.QuoteInteractor;
import use_case.get_quote.QuoteOutputBoundary;
import use_case.home.HomeInputBoundary;
import use_case.home.HomeInteractor;
import use_case.home.HomeOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;
import interface_adapter.local_timer.LocalTimerController;
import interface_adapter.local_timer.LocalTimerPresenter;
import interface_adapter.local_timer.LocalTimerViewModel;
import use_case.local_timer.LocalTimerInteractor;
import entity.LocalTimerFactory;
import use_case.local_timer.LocalTimerDataAccessInterface;
import entity.CommonReportFactory;
import interface_adapter.report.ReportController;
import interface_adapter.report.ReportPresenter;
import interface_adapter.report.ReportViewModel;
import use_case.report.ReportInputBoundary;
import use_case.report.ReportInteractor;
import use_case.report.ReportOutputBoundary;
import use_case.report.ReportDataAccessInterface;
/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private InMemoryTaskData inMemoryTaskData = new InMemoryTaskData();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private HomeViewModel homeViewModel;
    private HomeView homeView;
    private EnterTaskView enterTaskView;
    private EnterTaskViewModel enterTaskViewModel;
    private TaskEnteredViewModel taskEnteredViewModel;
    private TaskEnteredView taskEnteredView;
    private ReportViewModel reportViewModel;
    private ReportView reportView;
    private ReportController reportController;
    private final InMemoryLocalTimerDataAccess timerData = new InMemoryLocalTimerDataAccess();
    private final InMemoryReportDataAccess reportDataAccess;
    private QuoteViewModel quoteViewModel;
    private ZenQuotesDataAccessObject quoteDataAccessObject;
    private QuoteView quoteView;


    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        reportDataAccess = new InMemoryReportDataAccess(inMemoryTaskData, timerData);
    }

    /**
     * Adds the Home View to the application.
     * @return this builder
     */
    public AppBuilder addHomeView() {
        homeViewModel = new HomeViewModel();
        homeView = new HomeView(homeViewModel, viewManagerModel);
        cardPanel.add(homeView, homeView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addEnterTaskView() {
        enterTaskViewModel = new EnterTaskViewModel();
        enterTaskView = new EnterTaskView(enterTaskViewModel, viewManagerModel);
        cardPanel.add(enterTaskView, enterTaskView.getViewName());
        return this;
    }

    public AppBuilder addTaskEnteredView() {
        taskEnteredViewModel = new TaskEnteredViewModel();
        taskEnteredView = new TaskEnteredView(taskEnteredViewModel, viewManagerModel);
        cardPanel.add(taskEnteredView, taskEnteredViewModel.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(viewManagerModel,
                homeViewModel, enterTaskViewModel);
        final HomeInputBoundary userHomeInteractor = new HomeInteractor(homeOutputBoundary);

        final HomeController homeController = new HomeController(userHomeInteractor);
        homeView.setHomeController(homeController);
        return this;

    }
    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addEnterTaskUseCase() {
        final EnterTaskOutputBoundary enterTaskOutputBoundary = new EnterTaskPresenter(enterTaskViewModel,
                taskEnteredViewModel, viewManagerModel);

        final EnterTaskInputBoundary enterTaskInteractor = new EnterTaskInteractor(inMemoryTaskData, enterTaskOutputBoundary);

        final EnterTaskController enterTaskController = new EnterTaskController(enterTaskInteractor);
        enterTaskView.setEnterTaskController(enterTaskController);
        return this;
    }


    /**
     * Adds the Timer View to the application.
     * @return this builder
     */
    public AppBuilder addTimerView() {
        final LocalTimerViewModel timerViewModel = new LocalTimerViewModel();
        final LocalTimerPresenter timerPresenter = new LocalTimerPresenter(timerViewModel);

        final LocalTimerInteractor timerInteractor = new LocalTimerInteractor(
                timerPresenter,
                new LocalTimerFactory(),
                timerData
        );

        final LocalTimerController timerController = new LocalTimerController(timerInteractor);
        final LocalTimerView timerView = new LocalTimerView(timerViewModel, timerController, viewManagerModel);
        cardPanel.add(timerView, timerView.getViewName());

        return this;
    }


    public AppBuilder addQuoteView() {
        quoteViewModel = new QuoteViewModel("quote");
        quoteDataAccessObject = new ZenQuotesDataAccessObject();

        QuoteOutputBoundary quotePresenter = new QuotePresenter(quoteViewModel, viewManagerModel);
        QuoteInputBoundary quoteInteractor = new QuoteInteractor(quoteDataAccessObject, quotePresenter);
        QuoteController quoteController = new QuoteController(quoteInteractor);

        quoteView = new QuoteView(quoteViewModel, quoteController, viewManagerModel);
        cardPanel.add(quoteView, quoteView.getViewName());

        return this;
    }

    public AppBuilder addQuoteUseCase() {
        if (quoteDataAccessObject == null) {
            quoteDataAccessObject = new ZenQuotesDataAccessObject();
        }

        QuoteOutputBoundary quoteOutputBoundary = new QuotePresenter(quoteViewModel, viewManagerModel);
        QuoteInputBoundary quoteInteractor = new QuoteInteractor(quoteDataAccessObject, quoteOutputBoundary);
        QuoteController quoteController = new QuoteController(quoteInteractor);
        quoteView.setQuoteController(quoteController);

        return this;
    }

    /**
     * Adds the Report View to the application.
     * @return this builder.
     */
    public AppBuilder addReportView() {
        reportViewModel = new ReportViewModel();
        reportView = new ReportView(reportViewModel, viewManagerModel);
        cardPanel.add(reportView, reportView.getViewName());
        return this;
    }

    /**
     * Adds the Report Use Case to the application.
     * @return this builder.
     */
    public AppBuilder addReportUseCase() {
        ReportOutputBoundary reportOutputBoundary = new ReportPresenter(viewManagerModel, reportViewModel);
        ReportInputBoundary reportInteractor = new ReportInteractor(
                reportOutputBoundary,
                reportDataAccess,
                new CommonReportFactory()
        );
        reportController = new ReportController(reportInteractor, reportDataAccess);
        reportView.setReportController(reportController);
        return this;
    }

    /**
     * Adds the Quote Use case to the application.
     * @return this builder.
     */


    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Task Manager");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Ensure the correct initial view is set here:
        cardLayout.show(cardPanel, homeView.getViewName());  // Ensure this points to the Enter Task View
        // cardLayout.show(cardPanel, "report");

        return application;
    }

}