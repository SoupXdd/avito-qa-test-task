package qa.api;

import java.util.UUID;

record TestCredentials(String username, String password) {

    static final String DEFAULT_PASSWORD = "strong_password";

    static TestCredentials newUser() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        return new TestCredentials("student_" + suffix, DEFAULT_PASSWORD);
    }

    TestCredentials withPassword(String password) {
        return new TestCredentials(username, password);
    }
}
