import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class expenseSplitterApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseSplitter expenseSplitter = new ExpenseSplitter();

        // Get users
        System.out.print("Enter the number of users: ");
        int numUsers = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numUsers; i++) {
            System.out.print("Enter user " + (i + 1) + " name: ");
            String userName = scanner.nextLine();
            // Create user with initial balance of 0
            expenseSplitter.createUser(userName, 0);
        }

        // Get expenses
        inputExpenses(scanner, expenseSplitter);
        expenseSplitter.calculateBalance();
        // Display options
        displayOptions(scanner, expenseSplitter);
    }

    public static void inputExpenses(Scanner scanner, ExpenseSplitter expenseSplitter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Enter expenses (Enter 'done' to finish): ");
        // Get expenses
        while (true) {
            try {
                System.out.print("Expense name: ");
                String expenseName = scanner.nextLine();
                if (expenseName.equals("done")) {
                    break;
                }

                System.out.print("Amount: ");
                String amountStr = scanner.nextLine();
                if (amountStr.isEmpty()) {
                    // Throw exception to catch later
                    // no null or empty strings allowed
                    throw new NumberFormatException("Amount cannot be empty.");
                }
                double amount = Double.parseDouble(amountStr);
                if (amount < 0) {
                    // Throw exception to catch later
                    // no negative amounts allowed
                    throw new NumberFormatException("Amount cannot be negative.");
                }

                System.out.print("Date (MM/DD/YYYY): ");
                String dateString = scanner.nextLine();
                if (dateString.equals("done")) {
                    break;
                }
                Date date = dateFormat.parse(dateString);

                System.out.print("Description: ");
                String description = scanner.nextLine();

                expenseSplitter.inputExpense(expenseName, amount, date, description);
            } catch (NumberFormatException | ParseException e) {
                // Catch exceptions thrown above
                // Print error message and continue
                System.out.println("Invalid input: " + e.getMessage() + " Please try again.");
            }
        }
    }

    public static void displayOptions(Scanner scanner, ExpenseSplitter expenseSplitter) {
        while (true) {
            try {
                System.out.println("\nSelect an option:");
                System.out.println("1. View Expense Summaries");
                System.out.println("2. Edit Expenses");
                System.out.println("3. View Final Balances");
                System.out.println("4. Exit");

                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        expenseSplitter.displayExpenseSummaries();
                        break;
                    case 2:
                        System.out.println("Current User Expenses:");
                        expenseSplitter.displayExpenseSummaries();
                        System.out.print("Enter the expense name to edit (or 'back' to go back): ");
                        String expenseToEdit = scanner.nextLine();
                        // Check if user wants to go back
                        if (!expenseToEdit.equalsIgnoreCase("back")) {
                            // Get new expense details
                            inputExpenses(scanner, expenseSplitter, expenseToEdit);
                        }
                        break;
                        // Calculate balance
                    case 3:
                        expenseSplitter.calculateBalance();
                        break;
                    case 4:
                        return;
                        // exit
                        // invalid option
                    default:
                        System.out.println("Invalid option. Please select again.");
                }
                // Catch invalid input
                // e.g. non-integer input
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid menu option.");
            }
        }
    }

    public static void inputExpenses(Scanner scanner, ExpenseSplitter expenseSplitter, String expenseToEdit) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (Expense expense : expenseSplitter.getExpenses()) {
            // Find expense to edit
            if (expense.getExpenseName().equalsIgnoreCase(expenseToEdit)) {
                try {
                    System.out.println("Editing expense: " + expense.getExpenseName());
                    System.out.print("Enter new amount: ");
                    String amountStr = scanner.nextLine();
                    double amount = Double.parseDouble(amountStr);

                    System.out.print("Enter new date (MM/DD/YYYY): ");
                    String dateString = scanner.nextLine();
                    Date date = dateFormat.parse(dateString);

                    System.out.print("Enter new description: ");
                    String description = scanner.nextLine();

                    expenseSplitter.editExpense(expense.getExpenseName(), amount, date, description);
                    // Catch invalid input
                    // e.g. non-integer input
                } catch (NumberFormatException | ParseException e) {
                    System.out.println("Invalid input: " + e.getMessage() + " Please try again.");
                }
                break;
            }
        }
    }
}
