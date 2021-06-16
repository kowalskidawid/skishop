package pl.kowalskidawid.skishop;

import java.util.ArrayList;
import java.util.List;

public class Response {
    public Boolean success;
    public Object result;
    public List<String> errors;

    public Response(Boolean success, Object result, List<String> errors) {
        this.success = success;
        this.result = result;
        this.errors = errors;
    }

    public Response() {
        this.errors = new ArrayList<String>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String message) {
        this.errors.add(message);
    }
}
