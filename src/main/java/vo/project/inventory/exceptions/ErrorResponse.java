package vo.project.inventory.exceptions;

public record ErrorResponse(
        int status,
        String message,
        String details
) { }