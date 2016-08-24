package ru.sugarbaron_bicycles.library.exceptions;

public class FileCreatingException 
extends Exception{
  //serial version uid
  private static final long serialVersionUID = 1L;

  public FileCreatingException(String reasonDescription){
    super(reasonDescription);
  }
}