
package com.crispin.files.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

public class Finder {

  private static boolean found;
  private static final FileSystemView fsv = FileSystemView.getFileSystemView();
  private static final File[] roots = fsv.getRoots();//listing all the disk volumes in the computer
   
  //recursives searches through the system and looks for any file with the name provided by the user
public static void findFile(String path,int level,String filename){
      File[] dirFiles = new File(path).listFiles();
    if (dirFiles != null && dirFiles.length > 0){
      for (File file : dirFiles){
          if(filename.equals(getFileNameWithoutExtension(file))){
             writeFile(file);
             found = true;
          }else if (file.isDirectory()) {
            System.out.println("[" + file.getName() + "]");
            findFile(file.getPath(), level++,filename);
         }else{
            System.out.println("name of file: " + file.getName());
         }
      }
   }
}        
       //this method can be combined with the "findfile" method to reduce code
       //the method was separated to make the code clean and understandable
    public static void fileFinder(int level, String filename){
                for(File file : roots){
                String path = file.getAbsolutePath();
                findFile(path,0,filename);
                }
        }
   
   //creates a folder called "File Finder" on the desktop and writes a file if found
  public static void writeFile(File filename){
     String des = System.getProperty("user.home") + "/Desktop/File Finder/";
     File file = new File(des);
     if(!file.exists()){
     file.mkdirs();
     }
     File newFile = new File(file,filename.getName());
     try(FileOutputStream output = new FileOutputStream(newFile);
           FileInputStream input = new FileInputStream(filename)) {
       final byte[] bytes = new byte[1024];
            int length;
            while((length = input.read(bytes)) > 0) {
                output.write(bytes, 0, length);
            }
     } catch (IOException e) {
     }
   }
  
  //gets the name of a file without its extension
 public static String getFileNameWithoutExtension(File file) {
      String nameWithoutExtension = null;
    int index = file.getName().lastIndexOf('.');
      if (index>0&& index <= file.getName().length() - 2 ) {
      nameWithoutExtension = file.getName().substring(0, index);
      }  
     return nameWithoutExtension;
    }
 
   //getting whether the file was found in the system or not
   public static boolean getFound(){
       return found;
    }
   //resetting the file found so that the user can search for another file
  static void setFound(boolean b){
     found = b;
   }
}
