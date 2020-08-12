package SimonSaysGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Titulo extends JPanel implements Runnable{

	private JLabel s,
				   i,
				   m,
				   o,
				   n,
				   d,
				   i2,
				   c,
				   e;
	
	public Titulo(){
		super();
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(700,100));
		
		this.s=new JLabel();
		this.i=new JLabel();
		this.m=new JLabel();
		this.o=new JLabel();
		this.n=new JLabel();
		this.d=new JLabel();
		this.i2=new JLabel();
		this.c=new JLabel();
		this.e=new JLabel();
	
		this.s.setText("S");
		this.i.setText("I");
		this.m.setText("M");
		this.o.setText("O");
		this.n.setText("N     ");
		this.d.setText("D");
		this.i2.setText("I");
		this.c.setText("C");
		this.e.setText("E");
		
		this.s.setFont(new Font("Tahoma", 0,50));
		this.i.setFont(new Font("Tahoma", 0,50));
		this.m.setFont(new Font("Tahoma", 0,50));
		this.o.setFont(new Font("Tahoma", 0,50));
		this.n.setFont(new Font("Tahoma", 0,50));
		this.d.setFont(new Font("Tahoma", 0,50));
		this.i2.setFont(new Font("Tahoma", 0,50));
		this.c.setFont(new Font("Tahoma", 0,50));
		this.e.setFont(new Font("Tahoma", 0,50));
		
		
		this.add(this.s);
		this.add(this.i);
		this.add(this.m);
		this.add(this.o);
		this.add(this.n);
		this.add(this.d);
		this.add(this.i2);
		this.add(this.c);
		this.add(this.e);
		
		this.cambiarColores();
		
	}		
	
	public int aleatorio(){
		return (int)(Math.random()*256+0);
	}
	
	public void cambiarColores(){
		Thread hilo=new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		int x=1;
			while (x==1){
				this.s.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.i.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.m.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.o.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.n.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.d.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.i2.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.c.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				this.e.setForeground(new Color(this.aleatorio(),this.aleatorio(),this.aleatorio()));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
	}
	
}
