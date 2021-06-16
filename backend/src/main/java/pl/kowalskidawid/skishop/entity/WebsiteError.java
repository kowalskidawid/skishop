package pl.kowalskidawid.skishop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class WebsiteError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String controller;
    public String method;
    public String message;
    public String clientIp;
    public String date;

    public WebsiteError() {
    }

    public WebsiteError(String controller, String method, String message, String clientIp) {
        this.controller = controller;
        this.method = method;
        this.message = message;
        this.clientIp = clientIp;
        this.date = LocalDateTime.now().toString();
    }

    public Integer getId() {
        return id;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
