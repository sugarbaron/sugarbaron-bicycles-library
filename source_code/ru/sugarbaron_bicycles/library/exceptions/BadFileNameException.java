package ru.sugarbaron_bicycles.library.exceptions;

public class BadFileNameException 
extends Exception{
  //serial version uid
  private static final long serialVersionUID = 1L;

  public BadFileNameException(String reasonDescription){
    super(reasonDescription);
  }
}