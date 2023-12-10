import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

// User class
class User {
    private String name;
    private double balance;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void editBalance(double amount) { balance += amount; }
}

// Expense class
class Expense {
    private String expenseName;
    private double amount;
    private Date date;
    private String description;

    public Expense(String expenseName, double amount, Date date, String description) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getExpenseName() { return expenseName; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public String getDescription() { return description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(Date date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
}

// ExpenseSplitter class
class ExpenseSplitter {
    private List<User> users;
    private List<Expense> expenses;

    public ExpenseSplitter() {
        users = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public List<Expense> getExpenses() {
    return expenses;
}
    // Add user to users list
     public void createUser(String name, double initialBalance) {
        User newUser = new User(name, initialBalance);
        users.add(newUser);
    }

    // Add expense to expenses list
    public void inputExpense(String expenseName, double amount, Date date, String description) {
        Expense newExpense = new Expense(expenseName, amount, date, description);
        expenses.add(newExpense);

        double amountPerUser = amount / users.size();
        for (User user : users) {
            user.editBalance(-amountPerUser);
        }
    }

    // Calculate balance for each user
    public void calculateBalance() {
        for (User user : users) {
            System.out.println(user.getName() + " has a balance of " + user.getBalance());
        }
    }

    // Edit expense
    public void editExpense(String expenseName, double amount, Date date, String description) {
        boolean expenseFound = false;
        for (Expense expense : expenses) {
            if (expense.getExpenseName().equals(expenseName)) {
                expenseFound = true;

                double oldAmount = expense.getAmount();
                double oldAmountPerUser = oldAmount / users.size();

                // Update old amounts
                for (User user : users) {
                    user.editBalance(oldAmountPerUser);
                }

                // Update expense details
                expense.setAmount(amount);
                expense.setDate(date);
                expense.setDescription(description);

                // Update new amounts
                double amountPerUser = amount / users.size();
                for (User user : users) {
                    user.editBalance(-amountPerUser);
                }
                break;
            }
        }

        // If expense not found
        if (!expenseFound) {
            System.out.println("Expense not found.");
        }
    }

    // final balance for each user
    public void viewFinalBalance() {
        for (User user : users) {
            System.out.println(user.getName() + " has a balance of " + user.getBalance());
        }
    }

    // Display detailed expense summaries
    public void displayExpenseSummaries() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for (Expense expense : expenses) {
            System.out.println("Expense Name: " + expense.getExpenseName());
            System.out.println("Amount: " + expense.getAmount());
            System.out.println("Date: " + dateFormat.format(expense.getDate()));
            System.out.println("Description: " + expense.getDescription());
            System.out.println("----------");
        }
    }
}
