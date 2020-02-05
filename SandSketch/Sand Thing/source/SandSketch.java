import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SandSketch extends PApplet {

int Nothing = 0xff808080; //<>// //<>//
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
public void setup(){
    
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
        else if(coin >3 && coin < 14 && (i / width) > height /4 && (i / width) < height /1.5f && i % 0.9f >0.2f) //this just creates a nice noise
        {
            pixels[i] = Obstacle;
        }
        else
        {
            pixels[i] = Nothing;
        }
    }

    updatePixels();
}

public void draw()
{
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

  public void settings() {  size(512,512,P2D ); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "SandSketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
