import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2PasswordTest {
    public static void main(String[] args) {
        // Use the same parameters as stored in MySQL
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 10);

        // Example raw password (use the actual password entered during login)
        String rawPassword = "aleli123";  

        // Password from database (replace this with the actual hash from MySQL)
        String storedPassword =
"$argon2id$v=19$m=65536,t=10,p=1$3c6zltqcvyqZE+G5BppGVw$7M6zbeCLweCERgJY0YcEdzEgYE/d1V5CkzVD9eTe7mo";  

        // Check if passwords match
        boolean matches = encoder.matches(rawPassword, storedPassword);
        System.out.println("Password Match: " + matches);  // Expected output: true
    }
}

//aleli - aleli123
//admin - admin123
//jwick - pass123