import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.core.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SandSketch extends PApplet {

int Nothing = 0xff808080;
int Sand = 0xffEDC9AF;
int Water = 0xff0000FF;
int Obstacle = 0xff36454F;
int DrawWithMe;
int Pix;
float ET = 0;
float DT = 0;
PixPos Pos = new PixPos();
ArrayList<PixPos> ToBeDrawn = new ArrayList<PixPos>();
int brushsize = 30;

Zoom zoom;
public void settings(){
  size(1400,1000,P2D );
}

public void keyPressed() {
  
    if(keyCode == TAB){
  if (DrawWithMe == Sand) 
        {
            DrawWithMe = Water;
        } 
        else if (DrawWithMe == Water) 
        {
            DrawWithMe = Obstacle;
        }
        else  
        {
            DrawWithMe = Sand;
        }}
        
        if(keyCode == UP){
          brushsize++;
        }
        if(keyCode == DOWN){
          brushsize--;
        }
}

public void setup(){
    
    String[] args = {"--location=0,0", "Foo"};
    zoom = new Zoom(300, 300);
    PApplet.runSketch(args, zoom);

    loadPixels();
    DrawWithMe = Sand;
    for (int i = 0; i < width*height; i++) 
    {
        int coin = PApplet.parseInt(random(20));
        if(coin <=1)
        {
            pixels[i] = Sand;
        }
        else if(coin >1 && coin < 3)
        {
            pixels[i] = Water;
        }
        else if(coin >3 && coin < 14 && (i / width) > height /4 && (i / width) < height /1.7f && i % .9f >0.2f) //this just creates a nice noise
        {
            pixels[i] = Obstacle;
        }
        else
        {
            pixels[i] = Nothing;
        }
    }

    //zoom.pass(pixels,height,width);
    updatePixels();  
    
   
}

public void draw()
{

      textSize(20);
      fill(0);
      text("Press up or down to change BrushSize ^", width - 390, 40);  
      text("Press Tab to Change Colour -->", width - 350, 20);
      
    DT = (millis() - ET) / 1000;
    ET = millis();
    // background(255);
     
    loadPixels();
    
    
    
  if(mousePressed && mouseButton == RIGHT)
    {
        if (DrawWithMe == Sand) 
        {
            DrawWithMe = Water;
        } 
        else if (DrawWithMe == Water) 
        {
            DrawWithMe = Obstacle;
        }
        else  
        {
            DrawWithMe = Sand;
        }
    }
   if(mousePressed && mouseButton == LEFT)
    {
    
    
    
     //get pos
        PixPos mPos = new PixPos(mouseX,mouseY);
        //Mark X Pixels around Pos to be filled in next draw
        ToBeDrawn.add(mPos);
  
        int x = mPos.X;
        int y = mPos.Y;
        int r = brushsize;

            for (int j = x - r; j <= x + r; j++)
                for (int k = y - r; k <= y + r; k++)
                    if (PVector.dist(new PVector(j, k), new PVector(x, y)) <= r)
                        ToBeDrawn.add(new PixPos(j, k));
    }

  
    //draw in drawn pixels here
    if(ToBeDrawn.size() > 0)
    {
        for (int i = ToBeDrawn.size() - 1; i >= 0; i--) 
        {
            PixPos addme = ToBeDrawn.get(i);
            if(InBounds(addme) && Get(addme) == Nothing)
            {
                Set(addme,DrawWithMe);
            }
            ToBeDrawn.remove(i);  
        }
    }

    //calc behavior for pixels here
    for (int i = width*height - 1; i >0 ; i--) 
    {
      
      
      
      
        Pos.Y = (i / width);
        Pos.X = i - (Pos.Y * width);
        
        Pix = GetPixel(Pos.X,Pos.Y);
        
        
        //sand
        if(Pix == Sand && InBounds(PosBelow(Pos)))
        {
            if(GetBelow(Pos) == Nothing || GetBelow(Pos) == Water)
            {
              
                Set(Pos,GetBelow(Pos));
                
                SetBelow(Pos,Sand);
            }
            else{
              int coin = PApplet.parseInt(random(2));
              if(coin == 0 && InBounds(PosLeftBelow(Pos))){
                if(GetLeftBelow(Pos) == Nothing || GetLeftBelow(Pos) == Water)
                {             
                Set(Pos,GetLeftBelow(Pos));                
                SetLeftBelow(Pos,Sand);
                }
              }
              else if(InBounds(PosRightBelow(Pos))){
                if(GetRightBelow(Pos) == Nothing || GetRightBelow(Pos) == Water)
                {             
                Set(Pos,GetRightBelow(Pos));                
                SetRightBelow(Pos,Sand);
                }
              }
            } 
        }
        
        //water
        if(Pix == Water && InBounds(PosBelow(Pos)))
        {
          boolean done = false;
            if(GetBelow(Pos) == Nothing)
            {
              
                Set(Pos,GetBelow(Pos));                
                SetBelow(Pos,Water);
                done = true;
            }
            else{
              int coin = PApplet.parseInt(random(2));
              if(coin == 0 && InBounds(PosLeftBelow(Pos))){
                if(GetLeftBelow(Pos) == Nothing)
                {             
                Set(Pos,GetLeftBelow(Pos));                
                SetLeftBelow(Pos,Water);
                done = true;
                }
              }
              else if(InBounds(PosRightBelow(Pos))){
                if(GetRightBelow(Pos) == Nothing)
                {             
                Set(Pos,GetRightBelow(Pos));                
                SetRightBelow(Pos,Water);
                done = true;
                }
              }
            }              
            if(done == false){
              int coin = PApplet.parseInt(random(2));
              if(coin == 0 && InBounds(PosLeft(Pos))){
                if(GetLeft(Pos) == Nothing)
                {             
                Set(Pos,GetLeft(Pos));                
                SetLeft(Pos,Water);
                }
              }
              else if(InBounds(PosRight(Pos))){
                if(GetRight(Pos) == Nothing)
                {             
                Set(Pos,GetRight(Pos));                
                SetRight(Pos,Water);
                }
              }
            }
            
        }
       
      
    }
    
    
    zoom.pass(pixels,height,width,mouseX,mouseY);
      
    updatePixels();  
    zoom.redraw();
    
    loadPixels();
    int index = width;
     for (int j = 1; j < 20;j++){    
    for (int i = 0; i < 30;i++){   
      index = j *width;
      
    if(index - i < pixels.length && index-i > 0)
    pixels[index-i] = DrawWithMe -1;
    }
    }
    text(brushsize, width - 25, 15);
    updatePixels();
}
 
 public void mouseClicked() {
 
} 
class PixPos{
  int X ;
  int Y ;
  PixPos(){
    X = 0;
    Y = 0;
}

  PixPos(int newx,int newy){
    X = newx;
    Y = newy;
}
}

//Generic Compare
 public boolean ComparePix(PixPos p,int c)
 { 
   return GetPixel(p.X,p.Y) == c;
 }

//Generic Set
public void Set(PixPos p,int c){
  SetPixel(p.X,p.Y,c);
}
//Generic get (For pixel Color)
public int Get(PixPos p){
  return GetPixel(p.X,p.Y);
}


//BELOW
public void SetBelow(PixPos p,int c){
     Set(PosBelow(p),c);
 }
 public int GetBelow(PixPos p){
     return Get(PosBelow(p));
 }
 public PixPos PosBelow(PixPos p){
   return new PixPos(p.X,p.Y+1);
 }
 
 //ABOVE
public void SetAbove(PixPos p,int c){
     Set(PosAbove(p),c);
 }
 public int GetAbove(PixPos p){
     return Get(PosAbove(p));
 }
  public PixPos PosAbove(PixPos p){
   return new PixPos(p.X,p.Y-1);
 }
 
 //LEFT
public void SetLeft(PixPos p,int c){
     Set(PosLeft(p),c);
 }
 public int GetLeft(PixPos p){
     return Get(PosLeft(p));
 }
   public PixPos PosLeft(PixPos p){
   return new PixPos(p.X+1,p.Y);
 }
 
 //RIGHT
public void SetRight(PixPos p,int c){
     Set(PosRight(p),c);
 }
 public int GetRight(PixPos p){
     return Get(PosRight(p));
 }
    public PixPos PosRight(PixPos p){
   return new PixPos(p.X-1,p.Y);
 }
 
 //Right Above
 
 public void SetRightAbove(PixPos p,int c){
     Set(PosRightAbove(p),c);
 }
 public int GetRightAbove(PixPos p){   
     return Get(PosRightAbove(p));
 }
 public PixPos PosRightAbove(PixPos p){
   return PosAbove(PosRight(p));
 }
  //Right Below
 
 public void SetRightBelow(PixPos p,int c){
     Set(PosRightBelow(p),c);
 }
 public int GetRightBelow(PixPos p){   
     return Get(PosRightBelow(p));
 }
 public PixPos PosRightBelow(PixPos p){
   return PosBelow(PosRight(p));
 }
 
  //Left Above
 
 public void SetLeftAbove(PixPos p,int c){
     Set(PosLeftAbove(p),c);
 }
 public int GetLeftAbove(PixPos p){   
     return Get(PosLeftAbove(p));
 }
 public PixPos PosLeftAbove(PixPos p){
   return PosAbove(PosLeft(p));
 }
  //Right Below
 
 public void SetLeftBelow(PixPos p,int c){
     Set(PosLeftBelow(p),c);
 }
 public int GetLeftBelow(PixPos p){   
     return Get(PosLeftBelow(p));
 }
 public PixPos PosLeftBelow(PixPos p){
   return PosBelow(PosLeft(p));
 }
 
 
 public boolean InBounds(PixPos p){
   boolean in = false;
   
   if((p.X < width && p.X >= 0 ) && (p.Y < height && p.Y >= 0)){in = true;}
    
   return in;
 }
 //Set and Get from X Y
 public void SetPixel(int row, int col, int value)
 {
    pixels[col*width+row] = value;  
 }
 
public int GetPixel(int row, int col)
 {   
    return pixels[col*width+row];  
 }
// Foo.java tab //<>//
 

 
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SandSketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
