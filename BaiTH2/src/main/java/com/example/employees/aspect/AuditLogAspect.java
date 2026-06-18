package com.example.employees.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
public class AuditLogAspect {
    @After("execution(* com.example.employees.controller.EmployeeController.getEmployees(..))")
    public void logEmployeeAccess(JoinPoint joinPoint) {
        // 1. Lấy tên phương thức
        String methodName = joinPoint.getSignature().getName();
        // 2. Lấy tên người dùng đang đăng nhập từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "Anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        // 3. Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        // 4. In ra Console log theo đúng định dạng yêu cầu
        System.out.printf("[AUDIT LOG] Tài khoản '%s' đã gọi hàm '%s' vào lúc %s%n",
                username, methodName, now);
    }
}