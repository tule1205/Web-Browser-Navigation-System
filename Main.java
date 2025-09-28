import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BrowserNavigation browser = new BrowserNavigation(); // Connects BrowserNavigation
        System.out.println("Current Page: " + browser.currentWebsite);

        // Gives the user an option menu to choose
        boolean running = true;
        while (running) {
            System.out.println("\n1. What website do you want to visit?");
            System.out.println("2. Go BACK to previous website");
            System.out.println("3. Go FORWARD to previous website");
            System.out.println("4. Show history of websites");
            System.out.println("5. Clear history");
            System.out.println("6. Close browser");
            System.out.println("7. Restore Sessions");
            System.out.println("Please pick a menu number.");

            // Executes all methods
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: // Visits website
                        System.out.println("Please enter an url");
                        String url = scanner.nextLine();
                        System.out.println(browser.visitWebsite(url));
                        break;
                    case 2: // Goes back to previous website
                        System.out.println(browser.goBack());
                        break;
                    case 3: // Goes forward to next website
                        System.out.println(browser.goForward());
                        break;
                    case 4: // Shows history of websites
                        System.out.println(browser.showHistory());
                        break;
                    case 5: // Clears history
                        System.out.println("Do you want clear history? Type Y/N. (Y = yes, N = no)"); // Asks again
                        String choice1 = scanner.next();
                        try {
                            if (choice1.equals("Y") || choice1.equals("y")) {
                                System.out.println(browser.clearHistory());
                            } else if (choice1.equals("N") || choice1.equals("n")) {
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter Y or N.");
                            }
                        } catch(InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a letter.");
                            scanner.nextLine();
                        }
                        break;
                    case 6: // Closes browser
                        System.out.println(browser.closeBrowser());
                        running = false;
                        break;
                    case 7: // Restores session state
                        System.out.println(browser.restoreLastSession());
                        break;
                    default:
                        System.out.println("Invalid choice. Try again!");
                        break;
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("Total times visited: ");
        System.out.println(browser.getTotal());
    }
}