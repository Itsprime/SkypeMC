package net.itsprime;

import com.google.gson.JsonObject;
import com.skype.SkypeException;
import in.kyle.ezskypeezlife.EzSkype;
import in.kyle.ezskypeezlife.api.SkypeCredentials;
import in.kyle.ezskypeezlife.api.SkypeStatus;
import in.kyle.ezskypeezlife.api.captcha.SkypeCaptcha;
import in.kyle.ezskypeezlife.api.captcha.SkypeErrorHandler;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Jham on 12/28/2015.
 */
public class SkypeLogin implements SkypeErrorHandler {

    private EzSkype ezSkype;

    protected void skypeLogin() throws Exception, SkypeException {
        System.out.println("---Logging in---");

        // Load credentials from a file 'login.json'
        JsonObject login = EzSkype.GSON.fromJson(new FileReader(new File("login.json")), JsonObject.class);

        // Enter the Skype login info here
        ezSkype = new EzSkype(new SkypeCredentials(login.get("user").getAsString(), login.get("pass").getAsString()));
        ezSkype.setDebug(true);

        // A error handler is a class that will be called to solve issues with the bot
        ezSkype.setErrorHandler(this);
        ezSkype.login();
       // ezSkype.getLocalUser().setStatus(SkypeStatus.ONLINE);


        // Register all the events in this class
        // Events are denoted as methods that have 1 parameter that implements SkypeEvent
        ezSkype.getEventManager().registerEvents(this);

        System.out.println("---Logged in---");
    }

    public String setNewPassword() {
        System.out.println("Set new password!");
        return null;
    }

    public String solve(SkypeCaptcha skypeCaptcha) {
        System.out.println("Enter the solution to " + skypeCaptcha.getUrl() + " then click enter");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
