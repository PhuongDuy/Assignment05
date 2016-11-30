package vn.vanlanguni.ponggame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
/**
 * 
 */

/**
 * @author Binh Tran - T151266
 *	
 * 
 */
public class BallColorWindow extends JDialog{
	JRadioButton optColorBall = new JRadioButton("ColorBall");
	JRadioButton optTennisBall = new JRadioButton("Tennis");
	JRadioButton optBasketball = new JRadioButton("Basketball");
	JRadioButton optPokemonball = new JRadioButton("PokemonBall");
	JLabel lblColorBall = new JLabel();
	JLabel lblBasket = new JLabel();
	JLabel lblTennis = new JLabel();
	JLabel lblPokemon = new JLabel();
	ImageIcon imColorBall = new ImageIcon("./BallImage/ColorBall.png");
	ImageIcon imTennis = new ImageIcon("./BallImage/Tenisball.png");
	ImageIcon imBasketball = new ImageIcon("./BallImage/Basketball.png");
	ImageIcon imPokemonball = new ImageIcon("./BallImage/Pokemonball.png");
	ButtonGroup btnGroup = new ButtonGroup();
	JButton btnChoose = new JButton("Choose");
	public BallColorWindow(){
		setSize(500, 380);
		setTitle("BallTyle");
		setLayout(null);
		setLocationRelativeTo(null);
		setModal(true);
		
		add(optColorBall);
		add(optTennisBall);
		add(optBasketball);
		add(optPokemonball);
		add(lblColorBall);
		add(lblPokemon);
		add(lblBasket);
		add(lblTennis);
		add(btnChoose);
		
		lblColorBall.setIcon(imColorBall);
		lblTennis.setIcon(imTennis);
		lblBasket.setIcon(imBasketball);
		lblPokemon.setIcon(imPokemonball);
		btnGroup.add(optColorBall);
		btnGroup.add(optTennisBall);
		btnGroup.add(optBasketball);
		btnGroup.add(optPokemonball);
		
		lblColorBall.setBounds(10, 10, 50, 50);
		optColorBall.setBounds(60, 20, 100, 30);
		
		
		lblTennis.setBounds(10, 70, 50, 50);
		optTennisBall.setBounds(60, 80, 100, 30);
		
		lblBasket.setBounds(10, 130, 50, 50);
		optBasketball.setBounds(60, 140, 100, 30);

		lblPokemon.setBounds(10, 190, 50, 50);
		optPokemonball.setBounds(60, 200, 100, 30);
		
		btnChoose.setBounds(350, 250, 100, 30);
		
		//default option
		optColorBall.setSelected(true);
		
		btnChoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(optColorBall.isSelected() == true)
				{
					PongPanel.NumTypeBall = 0;
				}else if(optTennisBall.isSelected() == true){
					PongPanel.NumTypeBall = 1;
				}else if(optBasketball.isSelected() == true){
					PongPanel.NumTypeBall = 2;
				}else if(optPokemonball.isSelected() == true){
					PongPanel.NumTypeBall = 3;
				} 
				
			}
		});
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BallColorWindow mainWidow = new BallColorWindow();
		mainWidow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWidow.setVisible(true);
	}

}
