package ewm.error;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private String timestamp;
    private List<String> errors;
}
