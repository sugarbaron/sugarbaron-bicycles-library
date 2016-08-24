package ru.sugarbaron_bicycles.library.exceptions;

public class WhatAFuckException
extends Exception{
  //serial version uid
  private static final long serialVersionUID = 1L;

  public WhatAFuckException(String reasonDescription){
    super(reasonDescription);
  }
  
  public WhatAFuckException(Exception exc){
    super(exc);
  }
}