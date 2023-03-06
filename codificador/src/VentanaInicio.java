import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class VentanaInicio extends JFrame implements ActionListener{

    private JLabel labelTitulo;
	private JTextField textUsr;
	private JPasswordField textCon;
	private JLabel Usuario, contraseña;
	private JButton botonEntrar;
	JScrollPane mibarra1, miBarra2;
	VentanaCod miVentanaCod;

    /**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
     * @return 
	 */
	public VentanaInicio() {
		botonEntrar = new JButton();
		botonEntrar.setBounds(150, 200, 120, 25);
		botonEntrar.setText("Entrar");


		labelTitulo = new JLabel();
		labelTitulo.setText("Iniciar sesión");
		labelTitulo.setBounds(150, 20, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		Usuario = new JLabel();
		Usuario.setText("Usuario");
		Usuario.setBounds(50, 80, 80, 25);
		add(Usuario);

		contraseña = new JLabel();
		contraseña.setText("contraseña");
		contraseña.setBounds(50, 120, 80, 25);
		add(contraseña);

		textUsr = new JTextField();
		textUsr.setBounds(150, 80, 200, 25);
		add(textUsr);

		textCon = new JPasswordField();
		textCon.setBounds(150, 120, 200, 25);
		add(textCon);

		botonEntrar.addActionListener(this);
		// ////////////////////////////////////////////


		add(botonEntrar);
		add(labelTitulo);
		limpiar();
		setSize(450, 300);
		setTitle("Cifrador RSA");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
	}

	/**
	 * Limpia el formulario de Registro
	 */
	private void limpiar() {
		textUsr.setText("");
		textCon.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonEntrar) { 
			if(textUsr.getText().equals("equipo1") && String.valueOf(textCon.getPassword()).equals("Noice")){
				
				setVisible(false);
				
				
				try {
					miVentanaCod= new VentanaCod();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				miVentanaCod.setVisible(true);
			}
			else{
				limpiar();
				JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
			}
			
		}
	}
    
    
}
