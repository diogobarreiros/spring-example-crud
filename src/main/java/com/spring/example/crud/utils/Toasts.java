package com.spring.example.crud.utils;

import com.spring.example.crud.domain.models.shared.Toast;
import com.spring.example.crud.domain.models.shared.Type;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.spring.example.crud.domain.models.shared.Type.*;

public class Toasts {
    
    private Toasts() {}
    
    private static final String TOAST_FIELD = "toasts";
    
    public static void add(Model model, String message, Type type) {
        model.addAttribute(TOAST_FIELD, List.of(Toast.of(message, type)));
    }
    
    public static void add(Model model, String message) {
        model.addAttribute(TOAST_FIELD, List.of(Toast.of(message, LIGHT)));
    }

    public static void add(Model model, List<Toast> toasts) {
        model.addAttribute(TOAST_FIELD, toasts);
    }

    public static void add(RedirectAttributes redirect, String message, Type type) {
        redirect.addFlashAttribute(TOAST_FIELD, List.of(Toast.of(message, type)));
    }

    public static void add(RedirectAttributes redirect, String message) {
        redirect.addFlashAttribute(TOAST_FIELD, List.of(Toast.of(message, LIGHT)));
    }

    public static void add(RedirectAttributes redirect, List<Toast> toasts) {
        redirect.addFlashAttribute(TOAST_FIELD, toasts);
    }

    public static void add(RedirectAttributes redirect, BindingResult result) {
        var errors = result.getAllErrors();
        redirect.addFlashAttribute(TOAST_FIELD, toasts(errors));
    }

    public static void add(Model model, BindingResult result) {
        var errors = result.getAllErrors();        
        model.addAttribute(TOAST_FIELD, toasts(errors));
    }
    
    private static List<Toast> toasts(List<ObjectError> errors) {
        return errors.stream()
                .map(error -> Toast.of(error.getDefaultMessage(), DANGER))
                .collect(Collectors.toList());
    }
}
