// Foo.java tab //<>//
 
import processing.core.*;
 
public class Zoom extends PApplet {
 
  private final int w, h;
 
  public Zoom() {
    w = 200;
    h = 200;
  }
 
  public Zoom(int w, int h) {
    this.w = w;
    this.h = h;
  }
 
  public void settings() {
    size(w, h);
  }
 int[] bigPix = new int[640000]; 
 int Bh = 0;
 int Bw = 1;
 int mx = 0;
 int my = 0;
  public void draw() {
    background(255);
    loadPixels();
    int readCol = 0;
    int ReadRow = 0;
    int writepos = 0;
    int[] temprow = new int[w/2];
    Pixelwritepos = 0;
    //while (true){   

       


for (int mod = 0; mod < w*h;mod += w){
       for (int i = 0; readCol * w + writepos < temprow.length +mod;){
       if(writepos < temprow.length ){
         int Y = (i / w) ;
       int X = (i - (Y * w));   
       clampcenteredmidoffsets();
       
          if(bigPix.length > ((readCol + centeredmy)*Bw+(ReadRow+centeredmx))){
                        temprow[writepos] = bigPix[((readCol + centeredmy)*Bw+(ReadRow+centeredmx))];
          }
        writepos++;
        ReadRow++;
        if(writepos > (w/2) - 1){
            ReadRow = 0;
            readCol++;
            writerowtopix(temprow);
            temprow = new int[w/2];            
        }
        }
       }
       writepos = 0;
}
    //<>//
        
        
        
            
            //if(bigPix.length > ((Y + centeredmy)*Bw+(X+centeredmx))){
            //   pixels[i] = bigPix[((Y + centeredmy)*Bw+(X+centeredmx))];
            //}
   
    
      

updatePixels();
  }
      public void pass(int[] p,int h,int w,int mousex,int mousey) {
        Bh = h;
        Bw = w;
        bigPix = p;   
        mx = mousex;
        my = mousey;
  }
int centeredmy =0;
int centeredmx = 0;
  public void clampcenteredmidoffsets(){

       centeredmx = (mx - w/4);
            if (centeredmx < 0){
              centeredmx = 0;
            }
            if (centeredmx > Bw){
              centeredmx = Bw;
            }
           centeredmy = (my - h/4);
            if (centeredmy < 0){
              centeredmy = 0;
            }
            if (centeredmy > Bh){
              centeredmy = Bh;
            }
    }
     int Pixelwritepos = 0;
public void writerowtopix(int[] row){
        
        
          for (int i = 0; i < row.length; i++){
        //once
        if(Pixelwritepos < pixels.length){
        pixels[Pixelwritepos] = row[i];
             Pixelwritepos++; 
        }
        else{
          return;
        }
        //twice
           if(Pixelwritepos < pixels.length){
        pixels[Pixelwritepos] = row[i];
             Pixelwritepos++; 
        }
        else{
          return;
        }
          }
        
           for (int i = 0; i < row.length; i++){
        //thrice
        if( Pixelwritepos < pixels.length){
        pixels[Pixelwritepos] = row[i];
             Pixelwritepos++; 
        }
        else{
          return;
        }
        //fource
             if(Pixelwritepos < pixels.length){
        pixels[Pixelwritepos] = row[i];
             Pixelwritepos++; 
        }
        else{
          return;
        }
        }
        
     }
  }
