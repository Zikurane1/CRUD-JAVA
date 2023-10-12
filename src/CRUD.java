import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CRUD {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtCel;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtId;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD");
        frame.setContentPane(new CRUD().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/CRUD","root","");
            System.out.println("Success");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    void table_load(){
        try{
            pst = con.prepareStatement("Select * from crud");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }




    public CRUD() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,age,cel;

                name = txtName.getText();
                age = txtAge.getText();
                cel = txtCel.getText();

                try {
                    pst = con.prepareStatement("insert into CRUD(name,age,cel)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, cel);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedd");
                    table_load();
                    txtName.setText("");
                    txtAge.setText("");
                    txtCel.setText("");
                    txtName.requestFocus();
                }
                catch(SQLException e1){
                    e1.printStackTrace();

                }



            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText();
                    pst = con.prepareStatement("Select name, age, cel from crud where id =?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()==true){
                        String name = rs.getString(1);
                        String age = rs.getString(2);
                        String cel = rs.getString(3);
                        txtName.setText(name);
                        txtAge.setText(age);
                        txtCel.setText(cel);
                    }
                    else {
                        txtName.setText("");
                        txtAge.setText("");
                        txtCel.setText("");
                        JOptionPane.showMessageDialog(null,"Nombre invalido");
                    }
                }
                catch (SQLException ex){

                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id,name,age,cel;

                name = txtName.getText();
                age = txtAge.getText();
                cel = txtCel.getText();
                id = txtId.getText();

                try {
                    pst = con.prepareStatement ("update crud set name = ?,age = ?,cel = ? where id= ?");
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, cel);
                    pst.setString(4, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Actualizado exitosamente");
                    table_load();
                    txtName.setText("");
                    txtAge.setText("");
                    txtCel.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException el){
                    el.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtId.getText();
                try{
                    pst = con.prepareStatement("delete from crud where id = ?");
                    pst.setString(1,id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Elemento eliminado");
                    table_load();
                    txtName.setText("");
                    txtAge.setText("");
                    txtCel.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException e1){

                }
            }
        });
    }
}
