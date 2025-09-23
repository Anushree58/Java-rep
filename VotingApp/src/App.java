import java.sql.*;

public class App {
    // Database URL, Username, Password
    static final String DB_URL = "jdbc:mysql://localhost:3306/voting_system";
    static final String USER = "root"; // change as per your MySQL username
    static final String PASS = "Ananthu@2006"; // change as per your MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("✅ Connected to MySQL database!");

            // Example 1: Insert a new User
            String insertUser = "INSERT INTO users (name, email, password, voter_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertUser)) {
                pstmt.setString(1, "John Doe");
                pstmt.setString(2, "john@example.com");
                pstmt.setString(3, "securePass");
                pstmt.setString(4, "VOT123");
                pstmt.executeUpdate();
                System.out.println("User added successfully!");
            }

            // Example 2: Insert a Candidate
            String insertCandidate = "INSERT INTO candidates (name, party, position, symbol) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCandidate)) {
                pstmt.setString(1, "Alice");
                pstmt.setString(2, "Green Party");
                pstmt.setString(3, "Mayor");
                pstmt.setString(4, "🌳");
                pstmt.executeUpdate();
                System.out.println("Candidate added successfully!");
            }

            // Example 3: Insert a Vote
            String insertVote = "INSERT INTO votes (user_id, candidate_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertVote)) {
                pstmt.setInt(1, 1); // user_id = 1
                pstmt.setInt(2, 1); // candidate_id = 1
                pstmt.executeUpdate();
                System.out.println("Vote recorded successfully!");
            }

            // Example 4: Fetch results (count votes per candidate)
            String query = "SELECT c.name, COUNT(v.vote_id) AS total_votes " +
                    "FROM candidates c LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                    "GROUP BY c.candidate_id";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                System.out.println("\n--- Voting Results ---");
                while (rs.next()) {
                    System.out.println(rs.getString("name") + " -> " + rs.getInt("total_votes") + " votes");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}