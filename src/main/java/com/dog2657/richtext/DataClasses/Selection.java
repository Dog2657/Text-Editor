package com.dog2657.richtext.DataClasses;

import com.dog2657.richtext.Model;

import java.util.concurrent.atomic.AtomicReference;

public class Selection {
    private int start;
    private int end = -1;

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


    public String getContent(){
       if(this.end == -1)
           return "";

        AtomicReference<String> output = new AtomicReference<>("");

        Model.getInstance().process_each_line_output((int line, String content, int previousLinesTotal) -> {
            if((content.length() + previousLinesTotal) <= this.getBeginning())
                return;

            if(this.getEnding() <= previousLinesTotal)
                return;

            int start = 0;
            if(output.get() == "")
                start = this.getBeginning() - previousLinesTotal;

            int end = this.getEnding() - previousLinesTotal;
            if(content.length() < end)
                end = content.length();

            if(output.get() != "")
                output.set(output.get() + "\n");

            output.set(output.get() + content.substring(start, end));
        });

        return output.get();
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
