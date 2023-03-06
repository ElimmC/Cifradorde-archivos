import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VentanaCod extends JFrame implements ActionListener{

    private JLabel labelTitulo;
	//private JTextField textOri, textCodi, textCodi2, textDeco;
	private JLabel archivo, opciones;
	private JButton botonEmpezar;
	JScrollPane mibarra1, miBarra2;
	private JComboBox<String> combo1;
	private JFileChooser chooser;
	private FileNameExtensionFilter filter;
	private File archivos;
	private String accion="nada";
	private Path camino;
	KeyPairGenerator generator;
	PrivateKey privateKey;
	PublicKey publicKey;
	Cipher encryptCipher;
	Cipher decryptCipher;
	byte[] bytes;
	byte[] encryptedFileBytes;
	byte[] decryptedFileBytes;
    /**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
     * @return 
     * @throws NoSuchAlgorithmException
	 */
	
	public VentanaCod() throws NoSuchAlgorithmException {
		generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(4096);
		java.security.KeyPair pair = generator.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();
		

		chooser = new JFileChooser();
		
		
		botonEmpezar = new JButton();
		botonEmpezar.setBounds(100, 200, 120, 25);
		botonEmpezar.setText("Empezar");


		labelTitulo = new JLabel();
		labelTitulo.setText("Cifrador");
		labelTitulo.setBounds(250, 20, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		opciones = new JLabel();
		opciones.setText("Opciones");
		opciones.setBounds(10, 80, 80, 25);
		add(opciones);

		combo1 = new JComboBox<String>();
		combo1.setBounds(10, 110, 110, 25);
		combo1.addItem("Seleccionar");
		combo1.addItem("Cifrar");
		combo1.addItem("Decifrar");
		add(combo1);
		
		archivo = new JLabel();
		archivo.setText("Seleccionado:");
		archivo.setBounds(150, 100, 210, 25);
		add(archivo);

		botonEmpezar.addActionListener(this);
		combo1.addActionListener(this);
		// ////////////////////////////////////////////


		add(botonEmpezar);
		add(labelTitulo);
		limpiar();
		setSize(400, 300);
		setTitle("Cifrador RSA");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
	}

	/**
	 * Limpia el formulario de Registro
	 */
	private void limpiar() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonEmpezar) { 
			if(accion.equals("nada")){
				JOptionPane.showMessageDialog(null, "Seleccione archivo");
			}
			if(accion.equals("codi")){
			/////AQUI CREAMOS EL NUEVO ARCHIVO
			
			File nuevoArchivo = new File("nuevoCifrado.rsa");
			try {
				nuevoArchivo.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} // if file already exists will do nothing 

				/////AQUI HACEMOS EL CIFRADO
			bytes = new byte[(int)archivos.length()];
			FileInputStream fis = null;
			try {
				 fis = new FileInputStream(archivos);
				 //read file into bytes[]
				 fis.read(bytes);
				   } catch (IOException e1) {
			   e1.printStackTrace();
			   } finally {
				 if (fis != null) {
				 try {
				   fis.close();
			   } catch (IOException e1) {
				   e1.printStackTrace();
				   }
					 }
				 }				 
				try {
					encryptCipher = Cipher.getInstance("RSA");
				} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
					e1.printStackTrace();
				}
				try {
					encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				}
				try {
					encryptedFileBytes = encryptCipher.doFinal(bytes);
				} catch (IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}

				try (FileOutputStream stream = new FileOutputStream(nuevoArchivo, false)) {
					stream.write(encryptedFileBytes);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "Cifrado con éxito");
				accion="nada";
				 
			}
			
			 if(accion.equals("decodi")){
			/////AQUI CREAMOS EL NUEVO ARCHIVO
			
			File nuevoArchivo = new File("nuevoDecifrado.txt");
			try {
				nuevoArchivo.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} // if file already exists will do nothing 

				/////AQUI HACEMOS EL DECIFRADO
				bytes = new byte[(int)archivos.length()];

			FileInputStream fis = null;
     		    try {
          		 fis = new FileInputStream(archivos);
          		 fis.read(bytes);
		   		 } 
				catch (IOException e1) {
				 e1.printStackTrace();
				} 
				finally {
          		 if (fis != null) {
              		try {
					fis.close();
					} 
					catch (IOException e1) 
					{
					e1.printStackTrace();
					}
          		 }
      			}

				  try {
					encryptedFileBytes = Files.readAllBytes(camino);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				  try {
					decryptCipher = Cipher.getInstance("RSA");
				} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
					e1.printStackTrace();
				}
				  try {
					decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				}
				  try {
					decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
				} catch (IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}
				  try (FileOutputStream stream = new FileOutputStream(nuevoArchivo, false)) {
					  stream.write(decryptedFileBytes);
				  } catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Decifrado con éxito");
				accion="nada";
			}
			
		}
		if (e.getSource() == combo1) {
			if(combo1.getSelectedItem().equals("Cifrar")){
				filter = new FileNameExtensionFilter("solo texto","txt");
				chooser.setFileFilter(filter);
       			int returnVal = chooser.showOpenDialog(null);
        		if(returnVal == JFileChooser.APPROVE_OPTION) {
					archivos = chooser.getSelectedFile();
					archivo.setText("Seleccionado a cifrar: " + archivos.getName());
					accion = "codi";
					camino = Paths.get(archivos.getPath());
        		}
			}
			if(combo1.getSelectedItem().equals("Decifrar")){
				filter = new FileNameExtensionFilter("solo cifrado", "rsa");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
        		if(returnVal == JFileChooser.APPROVE_OPTION) {
					archivos = chooser.getSelectedFile();
					archivo.setText("Seleccionado a decifrar: " + archivos.getName());
					accion = "decodi";
					camino = Paths.get(archivos.getPath());
        		}
			}
			if(combo1.getSelectedItem().equals("Seleccionar")){
				
			}
		}
	}
    
    
}
