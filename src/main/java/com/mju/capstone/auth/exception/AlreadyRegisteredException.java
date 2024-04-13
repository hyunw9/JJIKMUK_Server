package com.mju.capstone.auth.exception;

public class AlreadyRegisteredException extends RuntimeException{

  public AlreadyRegisteredException() {super("이미 가입된 유저입니다.");
  }
}
