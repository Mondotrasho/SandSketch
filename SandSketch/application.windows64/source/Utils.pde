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
 boolean ComparePix(PixPos p,color c)
 { 
   return GetPixel(p.X,p.Y) == c;
 }

//Generic Set
void Set(PixPos p,color c){
  SetPixel(p.X,p.Y,c);
}
//Generic get (For pixel Color)
int Get(PixPos p){
  return GetPixel(p.X,p.Y);
}


//BELOW
void SetBelow(PixPos p,color c){
     Set(PosBelow(p),c);
 }
 int GetBelow(PixPos p){
     return Get(PosBelow(p));
 }
 PixPos PosBelow(PixPos p){
   return new PixPos(p.X,p.Y+1);
 }
 
 //ABOVE
void SetAbove(PixPos p,color c){
     Set(PosAbove(p),c);
 }
 int GetAbove(PixPos p){
     return Get(PosAbove(p));
 }
  PixPos PosAbove(PixPos p){
   return new PixPos(p.X,p.Y-1);
 }
 
 //LEFT
void SetLeft(PixPos p,color c){
     Set(PosLeft(p),c);
 }
 int GetLeft(PixPos p){
     return Get(PosLeft(p));
 }
   PixPos PosLeft(PixPos p){
   return new PixPos(p.X+1,p.Y);
 }
 
 //RIGHT
void SetRight(PixPos p,color c){
     Set(PosRight(p),c);
 }
 int GetRight(PixPos p){
     return Get(PosRight(p));
 }
    PixPos PosRight(PixPos p){
   return new PixPos(p.X-1,p.Y);
 }
 
 //Right Above
 
 void SetRightAbove(PixPos p,color c){
     Set(PosRightAbove(p),c);
 }
 int GetRightAbove(PixPos p){   
     return Get(PosRightAbove(p));
 }
 PixPos PosRightAbove(PixPos p){
   return PosAbove(PosRight(p));
 }
  //Right Below
 
 void SetRightBelow(PixPos p,color c){
     Set(PosRightBelow(p),c);
 }
 int GetRightBelow(PixPos p){   
     return Get(PosRightBelow(p));
 }
 PixPos PosRightBelow(PixPos p){
   return PosBelow(PosRight(p));
 }
 
  //Left Above
 
 void SetLeftAbove(PixPos p,color c){
     Set(PosLeftAbove(p),c);
 }
 int GetLeftAbove(PixPos p){   
     return Get(PosLeftAbove(p));
 }
 PixPos PosLeftAbove(PixPos p){
   return PosAbove(PosLeft(p));
 }
  //Right Below
 
 void SetLeftBelow(PixPos p,color c){
     Set(PosLeftBelow(p),c);
 }
 int GetLeftBelow(PixPos p){   
     return Get(PosLeftBelow(p));
 }
 PixPos PosLeftBelow(PixPos p){
   return PosBelow(PosLeft(p));
 }
 
 
 boolean InBounds(PixPos p){
   boolean in = false;
   
   if((p.X < width && p.X >= 0 ) && (p.Y < height && p.Y >= 0)){in = true;}
    
   return in;
 }
 //Set and Get from X Y
 void SetPixel(int row, int col, color value)
 {
    pixels[col*width+row] = value;  
 }
 
color GetPixel(int row, int col)
 {   
    return pixels[col*width+row];  
 }
