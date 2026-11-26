package qa.api;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ApiFailureAttachment implements TestExecutionExceptionHandler {

    private static final Pattern STATUS_MISMATCH =
            Pattern.compile("Expected status code <([0-9]{3})> but was <([0-9]{3})>");

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable failure) throws Throwable {
        try {
            StringBuilder diagnostic = new StringBuilder("Failure: ")
                    .append(failure.getClass().getSimpleName()).append('\n');
            Matcher status = STATUS_MISMATCH.matcher(String.valueOf(failure.getMessage()));
            if (status.find()) {
                diagnostic.append("Expected HTTP ").append(status.group(1))
                        .append(", received ").append(status.group(2)).append('\n');
            }

            Allure.getLifecycle().updateTestCase(test -> test.getSteps().forEach(step -> {
                diagnostic.append('\n').append(step.getName());
                step.getParameters().stream()
                        .filter(parameter -> "HTTP status".equals(parameter.getName())
                                || "Request error".equals(parameter.getName()))
                        .forEach(parameter -> diagnostic.append(" | ").append(parameter.getName())
                                .append(": ").append(parameter.getValue()));
            }));
            Allure.addAttachment("API failure diagnostics", "text/plain", diagnostic.toString(), ".txt");
        } catch (RuntimeException reportFailure) {
            failure.addSuppressed(reportFailure);
        }
        throw failure;
    }
}
