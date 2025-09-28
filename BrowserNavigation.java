import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;

public class BrowserNavigation {

    private final BrowserStack<String> forwardToStack;
    private final BrowserStack<String> backToStack;
    private final BrowserQueue<String> historyToQueue;
    public String currentWebsite;

    // Constructor
    public BrowserNavigation() {
        forwardToStack = new BrowserStack<>();
        backToStack = new BrowserStack<>();
        historyToQueue = new BrowserQueue<>();
        currentWebsite = "homepage.com";
        historyToQueue.enqueue(currentWebsite);
    }

    // Visits a new website and update navigation history
    public String visitWebsite(String url) throws URISyntaxException, IOException {
        // Save current page to backToStack if it exists
        if (currentWebsite != null) {
            backToStack.push(currentWebsite);
        }
        currentWebsite = url;
        historyToQueue.enqueue(url); // Adds visited URL to browsing history
        forwardToStack.clear(); // Ensures the logic for the current page to go back or go forward

        // Bonus part
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            String fullUrl = url;
            // Prepend http:// if missing, required for valid URI structure
            if (!fullUrl.startsWith("http://") && !fullUrl.startsWith("https://")) {
                fullUrl = "http://" + fullUrl;
            }
            Desktop.getDesktop().browse(new URI(fullUrl));
        }

        return "Now at " + url;
    }

    // Goes back to previous browser
    public String goBack() {
        // Check if we can go back. If the backToStack is empty, we are at the start of history.
        if (backToStack.isEmpty()) {
            return "No previous page available.";
        }
        forwardToStack.push(currentWebsite); // push the current page into the list
        currentWebsite = backToStack.pop(); // pop the previous page out of the list
        return "Now at " + currentWebsite;
    }

    // Goes forward to the next browser
    public String goForward() {
        // Check if we can go forward. If the forwardToStack is empty, there is nothing to go to.
        if (forwardToStack.isEmpty()) {
            return "No forward page available.";
        }
        backToStack.push(currentWebsite); // push the current page into the list
        currentWebsite = forwardToStack.pop(); // pop the next page out of the list
        return "Now at " + currentWebsite;
    }

    // Shows history of websites visited
    public String showHistory() {
        if (historyToQueue.isEmpty()) {
            return "No browsing history available.";
        }
        StringBuilder history = new StringBuilder();
        for (String s : historyToQueue) {
            history.append(s).append("\n");
        }
        return history.toString();
    }

    // Clears the history and resets
    public String clearHistory() {
        backToStack.clear();
        forwardToStack.clear();
        historyToQueue.clear();
        currentWebsite = "homepage.com";
        historyToQueue.enqueue(currentWebsite);
        return "Browsing history cleared.";
    }

    // Closes the browser and saves the browser to "session_data_txt"
    public String closeBrowser() {
        // Use PrintWriter to write data to a simple text file line-by-line
        try (PrintWriter writer = new PrintWriter(new FileWriter("session_data.txt"))) {
            // Write back stack size and elements (using StackIterator)
            writer.println(backToStack.size());
            for (String url : backToStack) { // calls backToStack.iterator()
                writer.println(url);
            }
            // Write forward stack size and elements
            writer.println(forwardToStack.size());
            for (String url : forwardToStack) { // calls forwardToStack.iterator()
                writer.println(url);
            }
            // Write history queue size and elements
            writer.println(historyToQueue.size());
            for (String url : historyToQueue) {
                writer.println(url);
            }
            // Write current page
            writer.println(currentWebsite);
        } catch (IOException e) {
            return "Error saving session: " + e.getMessage();
        }
        return "Browser session saved.";
    }

    // Restores the session state from the "session_data.txt" file
    public String restoreLastSession() {
        // Use Scanner to read the text file line-by-line
        try (Scanner fileScanner = new Scanner(new File("session_data.txt"))) {
            // Restore back stack
            backToStack.clear();
            int backStackSize = Integer.parseInt(fileScanner.nextLine());
            for (int i = 0; i < backStackSize; i++) {
                backToStack.push(fileScanner.nextLine());
            }
            // Restore forward stack
            forwardToStack.clear();
            int forwardStackSize = Integer.parseInt(fileScanner.nextLine());
            for (int i = 0; i < forwardStackSize; i++) {
                forwardToStack.push(fileScanner.nextLine());
            }
            // Restore history queue
            historyToQueue.clear();
            int historyQueueSize = Integer.parseInt(fileScanner.nextLine());
            for (int i = 0; i < historyQueueSize; i++) {
                historyToQueue.enqueue(fileScanner.nextLine());
            }
            // Restore current page
            if (fileScanner.hasNextLine()) {
                currentWebsite = fileScanner.nextLine();
            }
            return "Previous session restored.";
        } catch (FileNotFoundException e) {
            return "No previous session found.";
        } catch (Exception e) {
            return "Error restoring session: " + e.getMessage();
        }
    }

    // Gets the total times of websites visited
    public int getTotal() {
        return historyToQueue.size();
    }
}