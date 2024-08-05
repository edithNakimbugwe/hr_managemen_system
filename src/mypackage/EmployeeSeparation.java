/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DEETH
 */
public class EmployeeSeparation {
    
    public static final String RESULT = "EmployeeSeparation.pdf";
    Connection con;
    PreparedStatement statement;
    Statement st;
    String cs;
    
    String user;
    String password;
    
    String query;
    ResultSet rs;
    String records;
public EmployeeSeparation(){
   
        con=null;
        st=null;
        statement=null;
        cs = "jdbc:mysql://localhost:3306/hr_mgt_system";
        user="root";
        password="";
        //initComponents();
        try{
            Class.forName("com.mysql.jdbc.Driver");//register 
            con=DriverManager.getConnection(cs,user, password) ;
            st =con.createStatement();
            
        }
        catch(SQLException | ClassNotFoundException ex){
        }
        

}
public void Main() throws FileNotFoundException, DocumentException, SQLException{
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            
            Document document = new Document(PageSize.A4.rotate());
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            // step 3
            document.open(); 
            Paragraph co = new Paragraph("Extra.UG", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            co.setAlignment(Element.ALIGN_CENTER);

            Paragraph heading = new Paragraph("Involuntary Employee Separation Report", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            heading.setAlignment(Element.ALIGN_CENTER);
            
            document.add(co);
            //document.add(new Paragraph(" "));
            
            document.add(heading);
            document.add(new Paragraph(" "));
            
            Paragraph par = new Paragraph("This report shows details of  former employees that left involuntarily.", new Font(Font.FontFamily.TIMES_ROMAN, 14));
            par.setAlignment(Element.ALIGN_LEFT);
            
            Paragraph para = new Paragraph("Director's Signature.", new Font(Font.FontFamily.TIMES_ROMAN, 14));
            para.setAlignment(Element.ALIGN_LEFT);
            
            Paragraph parag = new Paragraph("-----------------.", new Font(Font.FontFamily.TIMES_ROMAN, 14));
            parag.setAlignment(Element.ALIGN_LEFT);
            
            PdfContentByte cb = writer.getDirectContent();
            
            // Load the image from file
            Image image = Image.getInstance("C:\\MOVIES\\PICS\\12.png");
            
            // Set the position and size of the image on the page
            image.setAbsolutePosition(document.getPageSize().getWidth()/2 - image.getScaledWidth()/2 , document.getPageSize().getHeight() - image.getScaledHeight());
            image.scaleAbsolute(40, 40);
            
            cb.addImage(image);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
             
             
            document.add(par);
            document.add(new Paragraph(" "));
            
            // step 4
            PdfPTable table = new PdfPTable(5);
            
            table.addCell("Employee ID");
            table.addCell("Employee Name");
            table.addCell("Separation Date");
            table.addCell("Reason");
            table.addCell("Process");
            
            try{
                String s = "Involuntary";
                query =  "SELECT e.ID, e.Name,"
                        + "s.LastDayatWork, s.Reason, s.Process FROM employeeinfo e INNER JOIN employeeseparation s "
                        + "ON e.ID = s.ID WHERE s.Process = '" + s + "'"
                        + "ORDER BY e.Name ASC";
                
                rs=st.executeQuery(query);
                while(rs.next()){
                    table.addCell(rs.getString("ID"));
                    table.addCell(rs.getString("Name"));
                    table.addCell(rs.getString("LastDayatWork"));
                    table.addCell(rs.getString("Reason"));
                    table.addCell(rs.getString("Process"));
                    
                }
                document.add(table);
                document.add(new Paragraph(" "));
                
                document.add(para);
                document.add(new Paragraph(" "));
                
                document.add(parag);
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Current Date is: "+ dateFormat.format(date)));
            }
            catch(SQLException ex){
            }
            
            if(Desktop.isDesktopSupported()){
                try{
                    File myfile = new File("EmployeeSeparation.pdf");
                    Desktop.getDesktop().open(myfile);
                }catch(IOException ex){}
            }
            /////////////////
            
            // step 5
            document.close();
        }
        catch(BadElementException | IOException ex){
                Logger.getLogger(EmployeeSeparation.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
        }


    
    



    



/**
 *
 * @author DEETH
 */


