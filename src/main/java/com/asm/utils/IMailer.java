package com.asm.utils;

public interface IMailer {
    boolean send(String to, String subject, String body);
}
