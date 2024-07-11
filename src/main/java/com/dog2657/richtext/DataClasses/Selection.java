package com.dog2657.richtext.DataClasses;

public class Selection {
    private int start;
    private int end;

   public Selection(int start){
       this.start = start;
   }

   public void setEnd(int end){
       this.end = end;
   }

   public void moveEnd(int moves){
       this.end += moves;
   }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /**
     * Gets the start or end depending on which is smaller aka closer to the start
     * @return int
     */
    public int getBeginning(){
      if(this.start <= this.end)
          return this.start;
      else
          return this.end;
    }

    /**
     * Gets the start or end depending on which is larger aka closer to the end
     * @return int
     */
    public int getEnding(){
        if(this.start <= this.end)
            return this.end;
        else
            return this.start;
    }
}
