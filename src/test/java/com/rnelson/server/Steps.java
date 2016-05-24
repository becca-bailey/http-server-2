package com.rnelson.server;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class Steps {
    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new ServerRunner(port));
        server.start();
    }

    @And("^the client connects on port (\\d+)$")
    public void theClientConnectsOnPort(int port) throws Throwable {
        Thread client = new Thread(new ClientRunner("localhost", port));
        client.start();
    }


    @When("^the user inputs \"([^\"]*)\"$")
    public void theUserInputs(String userInput) throws Throwable {
        userInput = (userInput + "\n");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }

    private ByteArrayOutputStream outputFromServer = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outputFromServer));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Then("^the response is \"([^\"]*)\"$")
    public void theResponseIs(String userInput) throws Throwable {
        assertEquals(("Echo: " + userInput + "\n"), outputFromServer.toString());
    }
}
