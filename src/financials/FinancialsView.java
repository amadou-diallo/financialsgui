/*z                               
 * FinancialsView.java
 */

package financials;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import business.*;
import java.text.NumberFormat;
import static java.text.NumberFormat.getCurrencyInstance;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 * The application's main frame.
 */
public class FinancialsView extends FrameView {
    
     Annuity an; //declare global variable for Annuity
     Loan ln; // declare global variable for Loan

    public FinancialsView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = FinancialsApp.getApplication().getMainFrame();
            aboutBox = new FinancialsAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        FinancialsApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jradAnnuity = new javax.swing.JRadioButton();
        jradLoan = new javax.swing.JRadioButton();
        jlblAmount = new javax.swing.JLabel();
        jtxtAmount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtRate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtTerm = new javax.swing.JTextField();
        jbtnCalc = new javax.swing.JButton();
        jlblResult = new javax.swing.JLabel();
        jtxtResult = new javax.swing.JTextField();
        jbtnSched = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(financials.FinancialsApp.class).getContext().getResourceMap(FinancialsView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        buttonGroup1.add(jradAnnuity);
        jradAnnuity.setFont(resourceMap.getFont("jradAnnuity.font")); // NOI18N
        jradAnnuity.setText(resourceMap.getString("jradAnnuity.text")); // NOI18N
        jradAnnuity.setName("jradAnnuity"); // NOI18N
        jradAnnuity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradAnnuityItemStateChanged(evt);
            }
        });

        buttonGroup1.add(jradLoan);
        jradLoan.setFont(resourceMap.getFont("jradLoan.font")); // NOI18N
        jradLoan.setText(resourceMap.getString("jradLoan.text")); // NOI18N
        jradLoan.setName("jradLoan"); // NOI18N
        jradLoan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradLoanItemStateChanged(evt);
            }
        });

        jlblAmount.setFont(resourceMap.getFont("jlblAmount.font")); // NOI18N
        jlblAmount.setText(resourceMap.getString("jlblAmount.text")); // NOI18N
        jlblAmount.setName("jlblAmount"); // NOI18N

        jtxtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtAmount.setText(resourceMap.getString("jtxtAmount.text")); // NOI18N
        jtxtAmount.setName("jtxtAmount"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtxtRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtRate.setText(resourceMap.getString("jtxtRate.text")); // NOI18N
        jtxtRate.setName("jtxtRate"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtxtTerm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtTerm.setText(resourceMap.getString("jtxtTerm.text")); // NOI18N
        jtxtTerm.setName("jtxtTerm"); // NOI18N

        jbtnCalc.setFont(resourceMap.getFont("jbtnCalc.font")); // NOI18N
        jbtnCalc.setText(resourceMap.getString("jbtnCalc.text")); // NOI18N
        jbtnCalc.setName("jbtnCalc"); // NOI18N
        jbtnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCalcActionPerformed(evt);
            }
        });

        jlblResult.setFont(resourceMap.getFont("jlblResult.font")); // NOI18N
        jlblResult.setText(resourceMap.getString("jlblResult.text")); // NOI18N
        jlblResult.setName("jlblResult"); // NOI18N

        jtxtResult.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtResult.setText(resourceMap.getString("jtxtResult.text")); // NOI18N
        jtxtResult.setName("jtxtResult"); // NOI18N

        jbtnSched.setText(resourceMap.getString("jbtnSched.text")); // NOI18N
        jbtnSched.setEnabled(false);
        jbtnSched.setName("jbtnSched"); // NOI18N
        jbtnSched.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSchedActionPerformed(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblResult, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addComponent(jradAnnuity)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jradLoan))
                                .addComponent(jtxtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbtnSched, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jtxtResult, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jbtnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jradLoan)
                    .addComponent(jradAnnuity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblResult, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtResult))
                .addGap(18, 18, 18)
                .addComponent(jbtnSched)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(103, 103, 103))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(financials.FinancialsApp.class).getContext().getActionMap(FinancialsView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jradLoanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradLoanItemStateChanged
        if (jradLoan.isSelected()) {
            jlblAmount.setText(Loan.AMTDESC);
            jlblResult.setText(Loan.RESULTDESC);
        }
    }//GEN-LAST:event_jradLoanItemStateChanged

    private void jradAnnuityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradAnnuityItemStateChanged
        if (jradAnnuity.isSelected()) {
            jlblAmount.setText(Annuity.AMTDESC);
            jlblResult.setText(Annuity.RESULTDESC);
        }
    }//GEN-LAST:event_jradAnnuityItemStateChanged

    private void jbtnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCalcActionPerformed
        statusMessageLabel.setText("");
        double a, r;
        int t;
        try {
            a = Double.parseDouble(jtxtAmount.getText());
            
        } catch (NumberFormatException e) {
            statusMessageLabel.setText("illegal Amount.");
            jtxtAmount.requestFocusInWindow();
            return ;
        }
        
        try {
            r = Double.parseDouble(jtxtRate.getText());
        } catch (NumberFormatException e) {
            statusMessageLabel.setText("illegal Rate.");
            jtxtRate.requestFocusInWindow();
            return;
        }
        
        try {
            t = Integer.parseInt(jtxtTerm.getText());
            
        } catch (NumberFormatException e) {
            statusMessageLabel.setText("illegal Rate.");
            jtxtTerm.requestFocusInWindow();
            return;
        }
           
           //instantiate business object: Annuity or Loan..
           NumberFormat curr = NumberFormat.getCurrencyInstance();
         if (jradAnnuity.isSelected()) {
             an = new Annuity(a,r,t);
             if (!an.getErrorMsg().isEmpty()) {
                 // an error was detected
                 statusMessageLabel.setText(an.getErrorMsg());
                  //enableInputMethods(false);
             }else{
                 // success
                 statusMessageLabel.setText("Success.");
                 String fmtresult = curr.format(an.getResult());
                 jtxtResult.setText(fmtresult);
                 jbtnSched.setEnabled(true);
             }
             
         } else {
             ln = new Loan(a,r,t);
             if (!ln.getErrorMsg().isEmpty()) {
                 statusMessageLabel.setText(ln.getErrorMsg());
             } else {
                 jtxtResult.setText(curr.format(ln.getResult()));
                 jbtnSched.setEnabled(true);
             
             }
         }
           
    }//GEN-LAST:event_jbtnCalcActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // CLEAR button was clicked
        jtxtResult.setText("");
        jtxtRate.setText("");
        jtxtAmount.setText("");
        jtxtTerm.setText("");
        statusMessageLabel.setText("");
        jbtnSched.setEnabled(false);
        buttonGroup1.clearSelection();
        jlblAmount.setText("Amount:");
        jlblResult.setText("Result:");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtnSchedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSchedActionPerformed
        // SCHEDULE button was clicked
        statusMessageLabel.setText("");
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        NumberFormat pct = NumberFormat.getPercentInstance();
        pct.setMaximumFractionDigits(3);
        
        
        String[] cols; //column heading
        String [][] t; //table cells
        DefaultTableModel mod; //table format
        JTable sched = null;
        String title = "";
        
        if (jradAnnuity.isSelected()) {
         cols = new String[] {"Month", "Beg.Bal.", "Deposit",
                               "Rate", "Int.Earn.", "End.Bal."};
         t = new String[an.getTerm()][6];
         mod = new DefaultTableModel(t,cols);
         sched = new JTable(mod);
         title = "Annuity Schedule: " + curr.format(an.getDeposit())+
                " @ " + pct.format(an.getRate()) + " for " + an.getTerm()
                 + "months.";
         for ( int i = 1; i <= an.getTerm(); i++  ) {
         sched.setValueAt(i, (i-1), 0);
         sched.setValueAt(curr.format(an.getBegBal(i)), (i-1), 1);
         sched.setValueAt(curr.format(an.getDeposit()), (i-1), 2);
         sched.setValueAt(pct.format(an.getRate()), (i-1), 3);
         sched.setValueAt(curr.format(an.getIntEarn(i)),(i-1), 4);
         sched.setValueAt(curr.format(an.getEndBal(i)),(i-1), 5);
         }
         
         } else if (jradLoan.isSelected()) {
    
    cols = new String[] {"Month", "Bag.Bal.", "Mo.Pmt.", "Int.Chg.","End.Bal."};
         t = new String[ln.getTerm()][5]; 
         mod = new DefaultTableModel(t,cols);
         sched = new JTable(mod);
         title = "Loan Schedule: " +  curr.format(ln.getPrincipal())+
                " @ " + pct.format(ln.getRate()) + " for " + ln.getTerm()
                + "months.";
         for(int i=1; i <= ln.getTerm(); i++ ) {
           sched.setValueAt(curr.format(ln.getBegBal(i)), (i-1), 1);
           sched.setValueAt(curr.format(ln.getResult()), (i-1), 2);
           sched.setValueAt(curr.format(ln.getIntChg(i)), (i-1), 3);
           sched.setValueAt(curr.format(ln.getEndBal(i)), (i-1), 4);
         }
    
         } else {
            statusMessageLabel.setText("Unknown operation");
            return;
         }
    
        
        JDialog dg = new JDialog();
        JScrollPane sp = new JScrollPane(sched);
        dg.add(sp);
        dg.setTitle(title);
        dg.setBounds(100,200,600,400);
        dg.setVisible(true);
        
    

        
    }//GEN-LAST:event_jbtnSchedActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtnCalc;
    private javax.swing.JButton jbtnSched;
    private javax.swing.JLabel jlblAmount;
    private javax.swing.JLabel jlblResult;
    private javax.swing.JRadioButton jradAnnuity;
    private javax.swing.JRadioButton jradLoan;
    private javax.swing.JTextField jtxtAmount;
    private javax.swing.JTextField jtxtRate;
    private javax.swing.JTextField jtxtResult;
    private javax.swing.JTextField jtxtTerm;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}