package be.kdg.programming3.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SessionExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(SessionExceptionHandler.class);

        @ExceptionHandler(SessionExpiredException.class)
        public String handleSessionExpiredException(SessionExpiredException ex, Model model) {
            logger.error("Session expired: " + ex.getMessage());
            model.addAttribute("error", "Session has expired. Please log in again.");
            return "error/sessionerror";
        }

    }
