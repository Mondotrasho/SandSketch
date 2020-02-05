color Nothing = #808080; //<>//
color Sand = #EDC9AF;
color Water = #0000FF;
color Obstacle = #36454F;
color DrawWithMe;
color Pix;
float ET = 0;
float DT = 0;
PixPos Pos = new PixPos();
ArrayList<PixPos> ToBeDrawn = new ArrayList<PixPos>();
int brushsize = 30;

Zoom zoom;
void settings(){
  size(1400,1000,P2D );
}

void keyPressed() {
  
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

void setup(){
    
    String[] args = {"--location=0,0", "Foo"};
    zoom = new Zoom(300, 300);
    PApplet.runSketch(args, zoom);

    loadPixels();
    DrawWithMe = Sand;
    for (int i = 0; i < width*height; i++) 
    {
        int coin = int(random(20));
        if(coin <=1)
        {
            pixels[i] = Sand;
        }
        else if(coin >1 && coin < 3)
        {
            pixels[i] = Water;
        }
        else if(coin >3 && coin < 14 && (i / width) > height /4 && (i / width) < height /1.7 && i % .9 >0.2) //this just creates a nice noise
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

void draw()
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
              int coin = int(random(2));
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
              int coin = int(random(2));
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
              int coin = int(random(2));
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
 
 void mouseClicked() {
 
} 
