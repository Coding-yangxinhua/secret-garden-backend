package com.pwc.sdc.sg.common.bean;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author Xinhua X Yang
 */
@Getter
@Setter
public class ResponseEntity<T> implements Serializable {
    HttpStatus status;

    String remark;

    T data;


    public ResponseEntity(HttpStatus status, String remark, T data) {
        this.status = status;
        this.remark = remark;
        this.data = data;
    }

    public ResponseEntity(T data) {
        this.status = HttpStatus.OK;
        this.data = data;
        this.remark = status.getReasonPhrase();
    }
    public ResponseEntity(HttpStatus status) {
        this.status = status;
        this.remark = status.getReasonPhrase();
    }

    public ResponseEntity(HttpStatus status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity<T>(data);
    }

    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<T>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> error(String remark) {
        return new ResponseEntity<T>(HttpStatus.BAD_REQUEST, remark);
    }

    public static <T> ResponseEntity<T> error(HttpStatus status, String remark) {
        return new ResponseEntity<T>(status, remark);
    }

}
