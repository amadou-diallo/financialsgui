
package business;
/**
 *
 * @author amadou
 */
public class Loan {
    
public static final String AMTDESC = "Loan Amount:";
public static final String RESULTDESC = "Monthly payment:";
 
private double amount, rate, mopmt;
private int term;
private String errmsg;
private double[] begbal, intchg, endbal;
private boolean built;

 
public Loan(double a, double r, int t) {
    this.amount = a;
    this.rate = r ;
    this.term = t;
    
    if(isValid()) {
       buildLoan();
    }
 }// end of constructor
    
  private boolean isValid() {
    this.errmsg = "";
    if (this.amount <= 0) {
        this.errmsg = "Amount not positive.";
    }
    
    if (this.rate <=0 || this.rate > 1.0){
        this.errmsg += " Rate appears to be incorrect.";
    }
    
    if (this.term <= 0) {
        this.errmsg += " Term is illegal.";
    }
    
    if (this.errmsg.isEmpty()) {
        return true;
    }else{
        return false;
    }
}//end of isValid

private void buildLoan() {
    //calculate monthly payment..
    double morate = this.rate / 12.0;
    double denom = Math.pow( (1+ morate), this.term) - 1;
    
    this.mopmt = (morate + morate / denom) * this.amount;
    
      try {
       this.begbal = new double[this.term];
       this.intchg = new double[this.term];
       this.endbal = new double[this.term];
       
       this.begbal[0] = this.amount;
       for (int i=0; i< this.term; i++) {
           if (i > 0) {
               this.begbal [i] = this.endbal[i-1];
           }
           
           this.intchg[i] = this.begbal[i] * (this.rate / 12.0);
           this.endbal[i] = this.begbal[i] + this.intchg[i] - this.mopmt;
       }
     
       this.built = true;
    } catch (Exception e) {
        this.errmsg = "Build Error: " + e.getMessage();
        this.built = false;
        
    }
    
}// end of buildLoan

public double getPrincipal() {
    return this.amount;
}
public double getRate() {
    return this.rate;
}

public int getTerm() {
    return this.term;
}
        
public String getErrorMsg() {
   return this.errmsg;     
}

public double getResult() {
   return this.mopmt; 
}
public double getBegBal(int mo) {
    
     if (!this.built) {
        buildLoan();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
     return this.begbal[mo-1];
     
   }
public double getIntChg(int mo) {
    
     if (!this.built) {
        buildLoan();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
     return this.intchg[mo-1];
}
 public double getEndBal(int mo) {
    
     if (!this.built) {
        buildLoan();
        if(!built) {
            return -1;
     }
        
     }
     
     if (mo < 1 || mo > this.term) {
         return -1;
     }
     return this.endbal[mo-1];
     



 }


}// end of class

 
