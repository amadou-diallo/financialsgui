 
package business;

/**
 *
 * @author amadou
 */
public class Annuity {
    
 public static final String AMTDESC = "Monthly Desposit:";
 public static final String RESULTDESC = "Final Value of Annuity:";
 private double amount, rate;
 private int term;
 private String errmsg = "";
 private double[] begbal, intearn, endbal;
 private boolean built;


public Annuity(double amt, double rat, int trm) {
    this.amount = amt;
    this.rate = rat;
    this.term = trm;
    this.built = false;
    
    if(isValid()) {
    buildAnnuity();
}
   
    
}//end of constructor

private boolean isValid() {
    this.errmsg = "";
    if (this.amount <= 0) {
        this.errmsg = "Amount not positive.";
    }
    
    if (this.rate <=0 || this.rate > 1.0){
        this.errmsg += "Rate appears to be incorrect.";
    }
    
    if (this.term <= 0) {
        this.errmsg += "Term is illegal";
    }
    
    if (this.errmsg.isEmpty()) {
        return true;
    }
        return false;
}//end of isValid

private void buildAnnuity() {
    try {
       
       this.begbal = new double[this.term];
       this.intearn = new double[this.term];
       this.endbal = new double[this.term];
       
       this.begbal[0] = 0.0;
       for (int i=0; i< this.term; i++) {
           if (i > 0) {
               this.begbal [i] = this.endbal[i-1];
           }
      this.intearn[i] = (this.begbal[i] + this.amount) * (this.rate / 12.0);
      this.endbal[i] = this.begbal[i] + this.amount + this.intearn[i];
       }
     
       this.built = true;
    } catch (Exception e) {
        this.errmsg = "Build Error: " + e.getMessage();
        this.built = false;
        
    }
}// end of buildAnnuity
        
public String getErrorMsg() {
   return this.errmsg;     
}

public double getResult() {
   if (!this.built) {
        buildAnnuity();
        if(!built) {
            return -1;
        }
   }
     return this.endbal[this.term-1];
}// end of getResult

public double getDeposit() {
    return this.amount;
}

public double getRate() {
    return this.rate;
}

public int getTerm() {
    return this.term;
}
    
public double getBegBal(int mo) {
    
     if (!this.built) {
        buildAnnuity();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
     return this.begbal[mo-1];
     

     }
public double getIntEarn(int mo) {
     if (!this.built) {
        buildAnnuity();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
    
    return this.intearn[mo-1];
}

public double getEndBal(int mo) {
     if (!this.built) {
        buildAnnuity();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
    
    
    
    return this.endbal[mo-1];
}



}// END OF CLASS



        
