package com.udla.coremvc.controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            model.addAttribute("status", statusCode);
            model.addAttribute("message", getErrorMessage(statusCode));

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }

        return "error/error";
    }

    private String getErrorMessage(Integer statusCode) {
        switch (statusCode) {
            case 403:
                return "No tienes permisos para acceder a este recurso";
            case 404:
                return "La página que buscas no existe";
            case 500:
                return "Error interno del servidor";
            default:
                return "Ha ocurrido un error inesperado";
        }
    }
}
