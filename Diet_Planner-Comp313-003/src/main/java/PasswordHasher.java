import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 10);

        String rawPassword = "pass123"; // Change this password if needed
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}

//admin123 for admin user
//aleli test123
