import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

public class Main extends JFrame {

	
	private File audioFile;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Waveform Generator");
		WaveformPanelContainer waveformPanelContainer;
		audioFile = new File("");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		waveformPanelContainer = new WaveformPanelContainer();
		contentPane.add(waveformPanelContainer, BorderLayout.CENTER);
		
		JMenuItem mntmEscolherArquivo = new JMenuItem("Escolher arquivo");
		mntmEscolherArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filePicker = new JFileChooser();
				FileNameExtensionFilter wavFilter = new FileNameExtensionFilter("Waveform Audio File Format (*.wav)", "wav");
				filePicker.setFileFilter(wavFilter);
				filePicker.setAcceptAllFileFilterUsed(false);
				filePicker.showOpenDialog(null);
				audioFile = filePicker.getSelectedFile();
				AudioInputStream audioInputStream;
				try {
					audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (audioFile)));
					waveformPanelContainer.removeAll();
					waveformPanelContainer.setAudioToDisplay(audioInputStream);
					waveformPanelContainer.updateUI();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		mnArquivo.add(mntmEscolherArquivo);
		
		JMenuItem mntmSalvarImagem = new JMenuItem("Salvar imagem");
		mntmSalvarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setSelectedFile(new File(audioFile.getParentFile(), FilenameUtils.getBaseName(audioFile.getName())+".png")); //nome do arquivo de audio com a extensão png
				int confirm = fileChooser.showSaveDialog(null);
				if(confirm == JFileChooser.APPROVE_OPTION){
					File image = fileChooser.getSelectedFile();
					if (!FilenameUtils.getExtension(image.getName()).equalsIgnoreCase("png")) //se a extensão não for png
						image = new File(image.getParentFile(), FilenameUtils.getBaseName(image.getName())+".png"); // remove a extensão (se tiver) e adiciona .png
					BufferedImage finalImg = getScreenshot(waveformPanelContainer);
					try {
						ImageIO.write(finalImg, "PNG", image);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mnArquivo.add(mntmSalvarImagem);
		
		JMenuItem mntmLimpar = new JMenuItem("Limpar");
		mntmLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				waveformPanelContainer.removeAll();
				waveformPanelContainer.updateUI();
			}
		});
		mnArquivo.add(mntmLimpar);
	}

	public BufferedImage getScreenshot(
		    JPanel component) {

		    BufferedImage image = new BufferedImage(
		      component.getWidth(),
		      component.getHeight(),
		      BufferedImage.TYPE_INT_RGB
		      );
		    // call the Component's paint method, using
		    // the Graphics object of the image.
		    component.paint( image.getGraphics() ); // alternately use .printAll(..)
		    return image;
	}
	
}
