public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private boolean hasVoted;

    // Constructor
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.hasVoted = false;
    }

    // Login method
    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println("Login successful for user: " + this.name);
            return true;
        } else {
            System.out.println("Invalid email or password.");
            return false;
        }
    }

    // Vote method
    public void vote(String candidateName) {
        if (hasVoted) {
            System.out.println("You have already voted. Multiple votes are not allowed.");
        } else {
            System.out.println(this.name + " voted for " + candidateName);
            hasVoted = true;
        }
    }

    // MAIN method added so we can run this file directly
    public static void main(String[] args) {
        User user1 = new User(1, "User One", "user1@example.com", "password123");

        if (user1.login("user1@example.com", "password123")) {
            user1.vote("Candidate A");
            user1.vote("Candidate B"); // second vote blocked
        }
    }
}
