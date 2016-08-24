package ru.sugarbaron_bicycles.library.uncompleted_shit;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;



/**
 * pictures processing
 * 
 * @author sugarbaron (sugarbaron1@mail.ru)
 */
 public final class Pic{
   //primary_section___________________________________________________________
   ////////////////////////////////////////////////////////////////////////////
   /**
    * create an icon with specified picture and dimensions
    * 
    * @param srcPic  icon picture
    * @param width   destination width
    * @param height  destination height
    * 
    * @return        a new #BufferedImage instance. it has specified dimensions
    */
   static public ImageIcon createSizedIcon(Image srcPic, int width, int height){
     //01.creating an instace of resized picture
     BufferedImage resizedPic;
     resizedPic = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
     //02.creating raphic context of resized picture instance
     Graphics2D graphics = resizedPic.createGraphics();
     //03.drawing result picture on graphics context of resized picture instance
     graphics.drawImage(srcPic, 0, 0, width, height, null);
     //04.doing something :)
     graphics.dispose();
     //05.ready!
     return new ImageIcon(resizedPic);
   } 
 } 