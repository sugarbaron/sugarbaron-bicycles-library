package ru.sugarbaron_bicycles.library;

//[standard libraries]
import java.awt.*;
//import java.awt.image.*;
import javax.swing.*;
//[my bicycles]
import ru.sugarbaron_bicycles.library.uncompleted_shit.*;




class z_ExternalCodeEmulator{
  public static void main(String[] unusableShit) throws Exception{
    //01.creating main frame
    JFrame frame = new JFrame("library_testing");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(1500, 1500);
    JPanel contentPane = new JPanel(null);
    //02.creating button with source picture
    Image pic = contentPane.getToolkit().getImage("./pictures/rotate.jpg");
    //ImageIcon srcIcon = new ImageIcon(pic);
    /*
    JButton srcBtn = new JButton(srcIcon);
    srcBtn.setSize(700, 700);
    srcBtn.setLocation(20, 20);
    contentPane.add(srcBtn);
    */
    //03.creating button with resized picture
    ImageIcon dstIcon = Pic.createSizedIcon(pic, 50, 50);
    JButton dstButton = new JButton(dstIcon);
    dstButton.setSize(70, 70);
    dstButton.setLocation(900, 20);
    contentPane.add(dstButton);
    //04.installing content pane and making visible all components
    frame.setContentPane(contentPane);
    frame.setVisible(true);
  }
}
